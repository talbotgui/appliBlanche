package com.guillaumetalbot.applicationblanche.metier.service;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.guillaumetalbot.applicationblanche.exception.BusinessException;
import com.guillaumetalbot.applicationblanche.metier.entite.reservation.Reservation;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class ExportServiceImpl implements ExportService {

	private final Map<String, JasperReport> cacheDeTemplatesCompiles = new HashMap<>();

	private final String facturationCheminRapportXml = "facture.xml";

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
	public byte[] genererPdfFactureReservation(final Reservation reservation, final Double montantTotal) {
		try {

			// Recherche du template XML
			final JasperReport templateCompile = this.chargementDuTemplateDeRapportCompile(this.facturationCheminRapportXml);

			// Remplissage du document
			final JasperPrint rapportAvecDonnees = this.injecterDonneesDansTemplate(templateCompile, reservation, montantTotal);

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
	 * @param montantTotal
	 * @return
	 * @throws JRException
	 */
	private JasperPrint injecterDonneesDansTemplate(final JasperReport rapportXml, final Reservation reservation, final Double montantTotal)
			throws JRException {

		// Paramètres sous forme de clef/valeur
		final Map<String, Object> parametres = new HashMap<>();
		parametres.put("param1", "valval1");

		// Liste des données
		final JRBeanCollectionDataSource listeDesOptions = new JRBeanCollectionDataSource(reservation.getOptions());

		// Alimentation du template
		return JasperFillManager.fillReport(rapportXml, parametres, listeDesOptions);
	}

}
