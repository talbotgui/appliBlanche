package com.guillaumetalbot.applicationblanche.metier.entite.securite;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.guillaumetalbot.applicationblanche.metier.entite.MutableUtil;

@Entity
public class Utilisateur implements Serializable {

	private static final Logger LOG = LoggerFactory.getLogger(Utilisateur.class);

	/** Nombre de jours après lesquels une erreur de connexion est ignorée et annulée. */
	private static final long NB_JOUR_DELAI_EFFACEMENT_ECHEC = 3;

	private static final long serialVersionUID = 1L;

	@Id
	private String login;

	@JsonIgnore
	private String mdp;

	@Column(name = "PREMIER_ECHEC")
	private LocalDateTime premierEchec;

	@ManyToMany
	@JoinTable(//
			name = "LIEN_UTILISATEUR_ROLE", //
			joinColumns = { @JoinColumn(name = "UTILISATEUR_LOGIN", insertable = false, updatable = false) }, //
			inverseJoinColumns = { @JoinColumn(name = "ROLE_NOM", insertable = false, updatable = false) })
	private Set<Role> roles;

	@Column(name = "SECOND_ECHEC")
	private LocalDateTime secondEchec;

	@Column(name = "TROISIEME_ECHEC")
	private LocalDateTime troisiemeEchec;

	public Utilisateur() {
		super();
	}

	public Utilisateur(final String login) {
		super();
		this.login = login;
	}

	public Utilisateur(final String login, final String mdp) {
		this(login);
		this.mdp = mdp;
	}

	public void declarerConnexionEnEchec() {
		if (this.premierEchec == null) {
			this.premierEchec = LocalDateTime.now();
		} else if (this.secondEchec == null) {
			this.secondEchec = LocalDateTime.now();
		} else if (this.troisiemeEchec == null) {
			this.troisiemeEchec = LocalDateTime.now();
		} else {
			LOG.warn("Tentative répétées de connexion de l'utilisateur {}", this.login);
		}
	}

	public void declarerConnexionReussie() {
		this.premierEchec = null;
		this.secondEchec = null;
		this.troisiemeEchec = null;
	}

	public String getLogin() {
		return this.login;
	}

	public String getMdp() {
		return this.mdp;
	}

	public LocalDateTime getPremierEchec() {
		return this.premierEchec;
	}

	public Set<Role> getRoles() {
		return MutableUtil.getMutable(this.roles);
	}

	public LocalDateTime getSecondEchec() {
		return this.secondEchec;
	}

	public LocalDateTime getTroisiemeEchec() {
		return this.troisiemeEchec;
	}

	public boolean isVerrouille() {
		// Calcul de la date avant laquelle un echec de connexion est oublié
		final LocalDateTime dateLimite = LocalDateTime.now().minus(NB_JOUR_DELAI_EFFACEMENT_ECHEC, ChronoUnit.DAYS);

		// Annulation des échecs de connexion
		for (int i = 0; i < 3; i++) {
			if (this.premierEchec != null && this.premierEchec.isBefore(dateLimite)) {
				this.premierEchec = this.secondEchec;
				this.secondEchec = this.troisiemeEchec;
			}
		}

		return this.premierEchec != null && this.secondEchec != null && this.troisiemeEchec != null;
	}

	public void setMdp(final String mdp) {
		this.mdp = mdp;
	}

	public void setPremierEchec(final LocalDateTime premierEchec) {
		this.premierEchec = premierEchec;
	}

	public void setRoles(final Set<Role> roles) {
		this.roles = roles;
	}

	public void setSecondEchec(final LocalDateTime secondEchec) {
		this.secondEchec = secondEchec;
	}

	public void setTroisiemeEchec(final LocalDateTime troisiemeEchec) {
		this.troisiemeEchec = troisiemeEchec;
	}

	@Override
	public String toString() {
		return "Utilisateur [login=" + this.login + ", premierEchec=" + this.premierEchec + ", secondEchec=" + this.secondEchec + ", troisiemeEchec="
				+ this.troisiemeEchec + "]";
	}

}
