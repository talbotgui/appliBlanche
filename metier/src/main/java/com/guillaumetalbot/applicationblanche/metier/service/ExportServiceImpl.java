package com.guillaumetalbot.applicationblanche.metier.service;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.guillaumetalbot.applicationblanche.exception.BusinessException;
import com.guillaumetalbot.applicationblanche.metier.dto.LigneDeFacturePdfDto;
import com.guillaumetalbot.applicationblanche.metier.dto.LignePaiementDto;
import com.guillaumetalbot.applicationblanche.metier.entite.reservation.Consommation;
import com.guillaumetalbot.applicationblanche.metier.entite.reservation.Formule;
import com.guillaumetalbot.applicationblanche.metier.entite.reservation.Option;
import com.guillaumetalbot.applicationblanche.metier.entite.reservation.Paiement;
import com.guillaumetalbot.applicationblanche.metier.entite.reservation.Reservation;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

@Service
public class ExportServiceImpl implements ExportService {

	private static final DateTimeFormatter DATE_COURTE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM");
	private static final DateTimeFormatter DATE_FORMATTER = LigneDeFacturePdfDto.DATE_FORMATTER;
	private static final String FACTURATION_CHEMIN_RAPPORT_XML = "edition-facture/facture.xml";
	public static final NumberFormat NUMBER_FORMATTER = LigneDeFacturePdfDto.NUMBER_FORMATTER;

	private final Map<String, JasperReport> cacheDeTemplatesCompiles = new HashMap<>();

	@Value("${edition.facture.chemin-entete:edition-facture/enteteParDefaut.jpg}")
	private String cheminImageEntete;
	@Value("${edition.facture.chemin-pieddepage:edition-facture/piedDePageParDefaut.jpg}")
	private String cheminImagePiedDePage;

	/**
	 * Recherche du template XML.
	 *
	 * @param cheminDuTemplate
	 * @return
	 * @throws JRException
	 */
	private JasperReport chargementDuTemplateDeRapportCompile(final String cheminDuTemplate) throws JRException {

		// Si le template est dans le cache, on le renvoie
		if (this.cacheDeTemplatesCompiles.containsKey(cheminDuTemplate)) {
			return this.cacheDeTemplatesCompiles.get(cheminDuTemplate);
		}

		// Recherche du template
		final InputStream is = this.getClass().getClassLoader().getResourceAsStream(cheminDuTemplate);
		if (is == null) {
			throw new BusinessException(BusinessException.ERREUR_CREATION_DOCUMENT);
		}

		// Compilation du template de document
		final JasperReport template = JasperCompileManager.compileReport(is);
		this.cacheDeTemplatesCompiles.put(cheminDuTemplate, template);

		// renvoi du template
		return template;

	}

	private Collection<LigneDeFacturePdfDto> creerListeLignesFacturees(final Reservation reservation, final long nbNuits, final long nbPersonnes) {
		// Création de la liste des lignes facturées
		final List<LigneDeFacturePdfDto> lignes = new ArrayList<>();

		// Ajout de la ligne de la formule
		if (reservation.getFormule() != null) {
			final Formule formule = reservation.getFormule();
			lignes.add(
					new LigneDeFacturePdfDto("Formule", formule.getNom(), formule.getPrixParNuit(), nbNuits, formule.calculerMontantTotal(nbNuits)));
		}

		// Ajout des options
		if (reservation.getOptions() != null) {
			for (final Option o : reservation.getOptions()) {
				lignes.add(new LigneDeFacturePdfDto("Option", o.getNom(), o.getPrix(), o.calculerMultiplicateurAuPrixUnitaire(nbNuits, nbPersonnes),
						o.calculerMontantTotal(nbNuits, nbPersonnes)));
			}
		}

		// Ajout des consommation
		if (reservation.getConsommations() != null) {

			final List<Consommation> consommationsTriees = new ArrayList<>(reservation.getConsommations());
			Collections.sort(consommationsTriees, (o1, o2) -> {
				// tri par date ASc puis par nom
				// pas de gestion du cas NULL de dateCreation ou produit ou produit.nom
				// ces données sont obligatoires
				final int hc1 = new HashCodeBuilder().append(o1.getDateCreation()).append(o1.getProduit().getNom()).toHashCode();
				final int hc2 = new HashCodeBuilder().append(o2.getDateCreation()).append(o2.getProduit().getNom()).toHashCode();
				return hc1 - hc2;
			});

			for (final Consommation c : consommationsTriees) {
				final String libelle = c.getProduit().getNom() + " (" + c.getDateCreation().format(DATE_COURTE_FORMATTER) + ")";
				lignes.add(new LigneDeFacturePdfDto("Consommation", libelle, c.getPrixPaye(), (long) c.getQuantite(), c.calculerMontantTotal()));
			}
		}

		return lignes;
	}

	/**
	 * Transformation d'une liste de Paiement en liste de LignePaiementDto (dont la structure est plus simple).
	 *
	 * @param paiements
	 * @return
	 */
	private Collection<LignePaiementDto> creerListeLignesPaiement(final Set<Paiement> paiements) {
		final List<LignePaiementDto> lignesPaiements = new ArrayList<>();
		if (paiements != null) {
			for (final Paiement p : paiements) {
				lignesPaiements.add(new LignePaiementDto(p.getDateCreation(), p.getMontant(), p.getMoyenDePaiement().getNom()));
			}
		}
		return lignesPaiements;
	}

	/**
	 * Export en PDF et transformation en base64.
	 *
	 * @param rapport
	 * @return
	 * @throws JRException
	 */
	private byte[] exporterRapport(final JasperPrint rapport) throws JRException {
		final ByteArrayOutputStream os = new ByteArrayOutputStream();
		JasperExportManager.exportReportToPdfStream(rapport, os);
		return os.toByteArray();
	}

	@Override
	public byte[] genererPdfFactureReservation(final Reservation reservation, final Long numeroDeFacture) {
		try {

			// Recherche du template XML
			final JasperReport templateCompile = this.chargementDuTemplateDeRapportCompile(FACTURATION_CHEMIN_RAPPORT_XML);

			// Remplissage du document
			final JasperPrint rapportAvecDonnees = this.injecterDonneesDansTemplate(templateCompile, reservation, numeroDeFacture);

			// Export en PDF et transformation en base64
			return this.exporterRapport(rapportAvecDonnees);
		} catch (final JRException e) {
			throw new BusinessException(BusinessException.ERREUR_CREATION_DOCUMENT, e);
		}
	}

	/**
	 * Remplissage du document.
	 *
	 * @param rapportXml
	 * @param reservation
	 * @param numeroDeFacture
	 * @return
	 * @throws JRException
	 */
	private JasperPrint injecterDonneesDansTemplate(final JasperReport rapportXml, final Reservation reservation, final Long numeroDeFacture)
			throws JRException {

		// Calcul des informations nécessaires à la suite
		final long nbNuits = reservation.calculerNombreNuits();
		final long nbPersonnes = reservation.getNombrePersonnes();

		// Paramètres sous forme de clef/valeur
		final Map<String, Object> parametres = new HashMap<>();
		parametres.put("numeroDeFacture", numeroDeFacture.toString());
		parametres.put("dateDeFacture", LocalDate.now().format(DATE_FORMATTER));
		parametres.put("client", reservation.getClient());
		parametres.put("dates", "du " + reservation.getDateDebut().format(DATE_FORMATTER) + " au " + reservation.getDateFin().format(DATE_FORMATTER)
				+ " soit " + Long.toString(nbNuits) + " nuit(s)");
		parametres.put("lignesFacturees", this.creerListeLignesFacturees(reservation, nbNuits, nbPersonnes));
		parametres.put("lignesPaiements", this.creerListeLignesPaiement(reservation.getPaiements()));
		parametres.put("montantPaye", NUMBER_FORMATTER.format(reservation.calculerMontantPaye()));
		parametres.put("montantRestantDu", NUMBER_FORMATTER.format(reservation.calculerMontantRestantDu()));
		parametres.put("montantTotal", NUMBER_FORMATTER.format(reservation.calculerMontantTotal()));
		parametres.put("cheminImageEntete", this.cheminImageEntete);
		parametres.put("cheminImagePiedDePage", this.cheminImagePiedDePage);

		// Alimentation du template
		return JasperFillManager.fillReport(rapportXml, parametres, new JREmptyDataSource(1));
	}

}
