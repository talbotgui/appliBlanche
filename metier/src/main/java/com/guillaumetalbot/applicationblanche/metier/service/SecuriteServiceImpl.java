package com.guillaumetalbot.applicationblanche.metier.service;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.guillaumetalbot.applicationblanche.exception.BusinessException;
import com.guillaumetalbot.applicationblanche.exception.BusinessExceptionSansRollback;
import com.guillaumetalbot.applicationblanche.metier.dao.securite.LienRoleRessourceRepository;
import com.guillaumetalbot.applicationblanche.metier.dao.securite.LienUtilisateurRoleRepository;
import com.guillaumetalbot.applicationblanche.metier.dao.securite.RessourceRepository;
import com.guillaumetalbot.applicationblanche.metier.dao.securite.RoleRepository;
import com.guillaumetalbot.applicationblanche.metier.dao.securite.UtilisateurRepository;
import com.guillaumetalbot.applicationblanche.metier.dto.UtilisateurAvecRolesEtAutorisations;
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

	@Autowired
	private LienRoleRessourceRepository lienRoleRessourceRepo;

	@Autowired
	private LienUtilisateurRoleRepository lienUtilisateurRoleRepo;

	@Autowired
	private RessourceRepository ressourceRepo;

	@Autowired
	private RoleRepository roleRepo;

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
		final Utilisateur u = this.utilisateurRepo.findOne(login);
		u.declarerConnexionReussie();
		this.utilisateurRepo.save(u);
	}

	private String encrypt(final String mdp) {
		try {
			final MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(mdp.getBytes("UTF-8"));
			return new String(md.digest(), "UTF-8");
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			throw new BusinessException(BusinessException.ERREUR_SHA, e);
		}
	}

	@Override
	public void initialiserOuCompleterConfigurationSecurite(final Collection<String> clefsRessources, final String loginAdmin, final String mdpAdmin,
			final String roleAdmin) {

		// Initialisation / completion des ressources
		final Collection<String> clefsExistantes = this.ressourceRepo.listerClefsRessources();
		final Collection<String> clefsAcreer = new ArrayList<>(clefsRessources);
		clefsAcreer.removeAll(clefsExistantes);
		for (final String clef : clefsAcreer) {
			this.ressourceRepo.save(new Ressource(clef));
		}

		// Si aucun utilisateur existant, création d'un administrateur avec tous les droits
		if (this.utilisateurRepo.listerUtilisateur().isEmpty()) {
			final Utilisateur utilisateur = this.utilisateurRepo.save(new Utilisateur(loginAdmin, mdpAdmin));
			final Role role = this.roleRepo.save(new Role(roleAdmin));
			this.lienUtilisateurRoleRepo.save(new LienUtilisateurRole(role, utilisateur));
			this.lienRoleRessourceRepo.save(this.lienRoleRessourceRepo.listerLiensInexistantsAvecToutesLesRessources(roleAdmin));
		}
	}

	@Override
	public Collection<Ressource> listerRessources() {
		return this.ressourceRepo.listerRessources();
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
	public void reinitialiserMotDePasse(final String login) {
		final Utilisateur u = this.utilisateurRepo.findOne(login);
		u.setMdp(this.encrypt(login));
		this.utilisateurRepo.save(u);
	}

	@Override
	public void sauvegarderRole(final String nomRole) {
		if (this.roleRepo.findOne(nomRole) == null) {
			this.roleRepo.save(new Role(nomRole));
		} else {
			throw new BusinessException(BusinessException.OBJET_DEJA_EXISTANT, "Role", nomRole);
		}
	}

	@Override
	public void sauvegarderUtilisateur(final String login, final String mdp) {

		this.valideLoginOuMotDePasse(login);

		// Recherche
		final Utilisateur u = this.utilisateurRepo.findOne(login);

		// Creation
		if (u == null) {

			this.valideLoginOuMotDePasse(mdp);

			this.utilisateurRepo.save(new Utilisateur(login, this.encrypt(mdp)));
		}

		// MaJ
		else {
			if (mdp != null) {
				this.valideLoginOuMotDePasse(mdp);
				u.setMdp(this.encrypt(mdp));
			}
			this.utilisateurRepo.save(u);
		}
	}

	@Override
	public void supprimerUtilisateur(final String login) {
		this.utilisateurRepo.delete(login);
	}

	private void valideLoginOuMotDePasse(final String loginOuMdp) {
		if ((loginOuMdp == null) || (loginOuMdp.length() < LOGIN_MDP_MIN)) {
			throw new BusinessException(BusinessException.ERREUR_LOGIN_MDP, LOGIN_MDP_MIN);
		}
	}

	@Override
	@Transactional(dontRollbackOn = BusinessExceptionSansRollback.class)
	public void verifierUtilisateur(final String login, final String mdp) {

		final Utilisateur u = this.utilisateurRepo.findOne(login);

		// Si pas d'utilisateur correspondant
		if (u == null) {
			throw new BusinessException(BusinessException.ERREUR_LOGIN);
		}

		// Si l'utilisateur est verrouilé
		else if (u.isVerrouille()) {
			throw new BusinessException(BusinessException.ERREUR_LOGIN_VEROUILLE);
		}

		// Si erreur dans le mot de passe
		else if (!u.getMdp().equals(this.encrypt(mdp))) {

			// Init des dates d'echec et sauvegarde des modifications
			u.declarerConnexionEnEchec();
			this.utilisateurRepo.save(u);

			// renvoi de l'erreur
			throw new BusinessExceptionSansRollback(BusinessException.ERREUR_LOGIN);
		}

		// Si tout se passe bien, suppression des erreurs de connexion
		else {
			u.declarerConnexionReussie();
			this.utilisateurRepo.save(u);
		}
	}
}
