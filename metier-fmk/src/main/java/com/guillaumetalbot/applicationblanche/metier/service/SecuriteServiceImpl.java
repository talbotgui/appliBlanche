package com.guillaumetalbot.applicationblanche.metier.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.TreeSet;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.guillaumetalbot.applicationblanche.exception.BusinessException;
import com.guillaumetalbot.applicationblanche.metier.dao.securite.LienRoleRessourceRepository;
import com.guillaumetalbot.applicationblanche.metier.dao.securite.LienUtilisateurRoleRepository;
import com.guillaumetalbot.applicationblanche.metier.dao.securite.RessourceRepository;
import com.guillaumetalbot.applicationblanche.metier.dao.securite.RoleRepository;
import com.guillaumetalbot.applicationblanche.metier.dao.securite.UtilisateurRepository;
import com.guillaumetalbot.applicationblanche.metier.dto.UtilisateurAvecRolesEtAutorisations;
import com.guillaumetalbot.applicationblanche.metier.entite.Entite;
import com.guillaumetalbot.applicationblanche.metier.entite.securite.LienRoleRessource;
import com.guillaumetalbot.applicationblanche.metier.entite.securite.LienUtilisateurRole;
import com.guillaumetalbot.applicationblanche.metier.entite.securite.Ressource;
import com.guillaumetalbot.applicationblanche.metier.entite.securite.Role;
import com.guillaumetalbot.applicationblanche.metier.entite.securite.Utilisateur;

@Service
@Transactional
public class SecuriteServiceImpl implements SecuriteService {

	private static final Logger LOG = LoggerFactory.getLogger(SecuriteServiceImpl.class);

	/** Longueur minimale du mot de passe. */
	private static final int LOGIN_MDP_MIN = 6;

	/** Longueur minimale du nom d'un role. */
	private static final int ROLE_NOM_MIN = 3;

	@Autowired
	private LienRoleRessourceRepository lienRoleRessourceRepo;

	@Autowired
	private LienUtilisateurRoleRepository lienUtilisateurRoleRepo;

	@Autowired
	private RessourceRepository ressourceRepo;

	@Autowired
	private RoleRepository roleRepo;

	@Value(value = "${metier.selReference:1234567890}")
	private Long selPourReference;

	@Autowired
	private UtilisateurRepository utilisateurRepo;

	@Override
	public void associerRoleEtRessource(final String nomRole, final String clefRessource) {
		final LienRoleRessource lien = this.lienRoleRessourceRepo.chargerLien(nomRole, clefRessource);
		if (lien == null) {
			this.lienRoleRessourceRepo.save(new LienRoleRessource(new Role(nomRole), new Ressource(clefRessource)));
		} else {
			LOG.warn("Tentative de création d'un lien déjà existant entre le role '{}' et la ressource '{}'", nomRole, clefRessource);
		}
	}

	@Override
	public void associerUtilisateurEtRole(final String login, final String nomRole) {
		final LienUtilisateurRole lien = this.lienUtilisateurRoleRepo.chargerLien(login, nomRole);
		if (lien == null) {
			this.lienUtilisateurRoleRepo.save(new LienUtilisateurRole(new Role(nomRole), new Utilisateur(login)));
		} else {
			LOG.warn("Tentative de création d'un lien déjà existant entre l'utilisateur '{}' et le role '{}'", login, nomRole);
		}
	}

	@Override
	public Utilisateur chargerUtilisateurAvecRolesEtRessourcesAutoriseesReadOnly(final String login) {
		return this.utilisateurRepo.chargerUtilisateurAvecRolesEtRessourcesAutoriseesReadOnly(login);
	}

	@Override
	public Utilisateur chargerUtilisateurReadOnly(final String login) {
		return this.utilisateurRepo.chargerUtilisateurReadOnly(login);
	}

	@Override
	public void desassocierRoleEtRessource(final String nomRole, final String clefRessource) {
		final LienRoleRessource lien = this.lienRoleRessourceRepo.chargerLien(nomRole, clefRessource);
		if (lien != null) {
			this.lienRoleRessourceRepo.delete(lien);
		} else {
			throw new BusinessException(BusinessException.OBJET_NON_EXISTANT, "LienRoleRessource", nomRole + "-" + clefRessource);
		}
	}

	@Override
	public void desassocierUtilisateurEtRole(final String login, final String nomRole) {
		final LienUtilisateurRole lien = this.lienUtilisateurRoleRepo.chargerLien(login, nomRole);
		if (lien != null) {
			this.lienUtilisateurRoleRepo.delete(lien);
		} else {
			throw new BusinessException(BusinessException.OBJET_NON_EXISTANT, "LienUtilisateurRole", login + "-" + nomRole);
		}
	}

	@Override
	public void deverrouillerUtilisateur(final String login) {
		final Utilisateur u = this.utilisateurRepo.findById(login)
				.orElseThrow(() -> new BusinessException(BusinessException.OBJET_NON_EXISTANT, "Utilisateur", login));
		u.declarerConnexionReussie();
		this.utilisateurRepo.save(u);
	}

	@Override
	public void initialiserOuCompleterConfigurationSecurite(final Collection<Ressource> ressources, final String loginAdmin, final String mdpAdmin,
			final String roleAdmin) {

		// Initialisation / completion des ressources
		final Collection<Ressource> ressourcesExistantes = new TreeSet<>((o1, o2) -> o1.comparerClefEtChemin(o2));
		ressourcesExistantes.addAll((Collection<Ressource>) this.ressourceRepo.findAll());
		final Collection<Ressource> ressourcesDansLeCode = new TreeSet<>((o1, o2) -> o1.comparerClefEtChemin(o2));
		ressourcesDansLeCode.addAll(ressources);

		final Collection<Ressource> ressourcesAcreer = new ArrayList<>();
		for (final Ressource r : ressources) {
			if (!ressourcesExistantes.contains(r)) {
				ressourcesAcreer.add(r);
			}
		}
		this.ressourceRepo.saveAll(ressourcesAcreer);

		final Collection<Ressource> ressourcesAsupprimer = new ArrayList<>();
		for (final Ressource r : ressourcesExistantes) {
			if (!ressourcesDansLeCode.contains(r)) {
				ressourcesAsupprimer.add(r);
			}
		}
		this.supprimerRessources(ressourcesAsupprimer);

		// Si aucun utilisateur existant, création d'un administrateur avec tous les droits
		if (this.utilisateurRepo.listerUtilisateur().isEmpty()) {
			this.sauvegarderUtilisateurAvecTousLesDroits(loginAdmin, mdpAdmin, roleAdmin);
		}

		// Pour surveiller un minimum l'évolution des clefs et chemin (modification du nom de la méthode ou du path)
		List<String> ressourcesSimilaires = this.ressourceRepo.listerRessourcesAuClefSimilaire();
		if (!ressourcesSimilaires.isEmpty()) {
			LOG.warn("Certaines ressources ont une même clef : {}", ressourcesSimilaires);
		}
		ressourcesSimilaires = this.ressourceRepo.listerRessourcesAuCheminSimilaire();
		if (!ressourcesSimilaires.isEmpty()) {
			LOG.warn("Certaines ressources ont un même chemin : {}", ressourcesSimilaires);
		}
	}

	/**
	 * Méthode initilisant le sel utilisé dans la création et la lecture des références uniques d'une entite.
	 *
	 */
	@PostConstruct
	protected void initialiseSelPourCalculReferencesDansLesEntites() {
		Entite.SEL_POUR_REFERENCE = this.selPourReference;
	}

	@Override
	public Page<Ressource> listerRessources(final Pageable page) {
		return this.ressourceRepo.listerRessources(page);
	}

	@Override
	public Collection<Role> listerRoles() {
		return this.roleRepo.listerRoles();
	}

	@Override
	public Collection<Utilisateur> listerUtilisateurs() {
		return this.utilisateurRepo.listerUtilisateur();
	}

	@Override
	public Collection<UtilisateurAvecRolesEtAutorisations> listerUtilisateursAvecRolesEtAutorisations() {
		return this.utilisateurRepo.listerUtilisateursAvecRolesEtAutorisations().stream()//
				.map(u -> new UtilisateurAvecRolesEtAutorisations(u))//
				.collect(Collectors.toList());
	}

	@Override
	public void notifierConnexion(final String login, final boolean status) {
		// Chargement de l'utilisateur
		final Utilisateur u = this.utilisateurRepo.findById(login)
				// Si pas d'utilisateur correspondant
				.orElseThrow(() -> new BusinessException(BusinessException.ERREUR_LOGIN));

		// Enregistrement des echecs ou nettoyage des echecs
		if (status) {
			u.declarerConnexionReussie();
		} else {
			u.declarerConnexionEnEchec();
		}

		// Enregistrement des modifications
		this.utilisateurRepo.save(u);

	}

	@Override
	public void reinitialiserMotDePasse(final String login) {
		final Utilisateur u = this.utilisateurRepo.findById(login)
				.orElseThrow(() -> new BusinessException(BusinessException.OBJET_NON_EXISTANT, "Utilisateur", login));
		u.setMdp(ChiffrementUtil.encrypt(login));
		this.utilisateurRepo.save(u);
	}

	@Override
	public void sauvegarderRole(final String nomRole) {
		if (nomRole == null || nomRole.length() < ROLE_NOM_MIN) {
			throw new BusinessException(BusinessException.ERREUR_ROLE_NOM, ROLE_NOM_MIN);
		}

		if (!this.roleRepo.findById(nomRole).isPresent()) {
			this.roleRepo.save(new Role(nomRole));
		} else {
			throw new BusinessException(BusinessException.OBJET_DEJA_EXISTANT, "Role", nomRole);
		}
	}

	@Override
	public Utilisateur sauvegarderUtilisateur(final String login, final String mdp) {

		this.valideLoginOuMotDePasse(login);

		// Recherche
		final Optional<Utilisateur> optU = this.utilisateurRepo.findById(login);

		// Creation
		if (!optU.isPresent()) {

			this.valideLoginOuMotDePasse(mdp);

			return this.utilisateurRepo.save(new Utilisateur(login, ChiffrementUtil.encrypt(mdp)));
		}

		// MaJ
		else {
			final Utilisateur u = optU.get();
			if (mdp != null) {
				this.valideLoginOuMotDePasse(mdp);
				u.setMdp(ChiffrementUtil.encrypt(mdp));
			}
			return this.utilisateurRepo.save(u);
		}
	}

	private void sauvegarderUtilisateurAvecTousLesDroits(final String loginAdmin, final String mdpAdmin, final String roleAdmin) {
		final Utilisateur utilisateur = this.sauvegarderUtilisateur(loginAdmin, mdpAdmin);
		final Role role = this.roleRepo.save(new Role(roleAdmin));
		this.lienUtilisateurRoleRepo.save(new LienUtilisateurRole(role, utilisateur));
		this.lienRoleRessourceRepo.saveAll(this.lienRoleRessourceRepo.listerLiensInexistantsAvecToutesLesRessources(roleAdmin));
	}

	private void supprimerRessources(final Collection<Ressource> ressources) {
		if (ressources.isEmpty()) {
			return;
		}

		this.lienRoleRessourceRepo.supprimerParRessources(ressources);

		// Suppression des ressources
		for (final Ressource r : ressources) {
			this.ressourceRepo.delete(r);
		}
	}

	@Override
	public void supprimerUtilisateur(final String login) {
		this.utilisateurRepo.deleteById(login);
	}

	private void valideLoginOuMotDePasse(final String loginOuMdp) {
		if (loginOuMdp == null || loginOuMdp.length() < LOGIN_MDP_MIN) {
			throw new BusinessException(BusinessException.ERREUR_LOGIN_MDP, LOGIN_MDP_MIN);
		}
	}
}
