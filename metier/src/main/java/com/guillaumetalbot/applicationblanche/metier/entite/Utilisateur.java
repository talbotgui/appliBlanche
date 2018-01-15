package com.guillaumetalbot.applicationblanche.metier.entite;

import java.io.Serializable;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Entity
public class Utilisateur implements Serializable {

	private static final Logger LOG = LoggerFactory.getLogger(Utilisateur.class);

	/** Nombre de jours après lesquels une erreur de connexion est ignorée et annulée. */
	private static final long NB_JOUR_DELAI_EFFACEMENT_ECHEC = 3;

	private static final long serialVersionUID = 1L;

	@Id
	private String login;

	private String mdp;

	@Column(name = "PREMIER_ECHEC")
	private Date premierEchec;

	@ManyToMany
	@JoinTable(//
			name = "LIEN_UTILISATEUR_ROLE", //
			joinColumns = { @JoinColumn(name = "UTILISATEUR_LOGIN", insertable = false, updatable = false) }, //
			inverseJoinColumns = { @JoinColumn(name = "ROLE_NOM", insertable = false, updatable = false) })
	private Set<Role> roles;

	@Column(name = "SECOND_ECHEC")
	private Date secondEchec;

	@Column(name = "TROISIEME_ECHEC")
	private Date troisiemeEchec;

	public Utilisateur() {
		super();
	}

	public Utilisateur(final String login) {
		super();
		this.setLogin(login);
	}

	public Utilisateur(final String login, final String mdp) {
		super();
		this.setLogin(login);
		this.setMdp(mdp);
	}

	public void declarerConnexionEnEchec() {
		if (this.premierEchec == null) {
			this.premierEchec = new Date();
		} else if (this.secondEchec == null) {
			this.secondEchec = new Date();
		} else if (this.troisiemeEchec == null) {
			this.troisiemeEchec = new Date();
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

	public Date getPremierEchec() {
		if (this.premierEchec != null) {
			return new Date(this.premierEchec.getTime());
		}
		return null;
	}

	public Set<Role> getRoles() {
		if (this.roles == null) {
			return new HashSet<>();
		}
		return new HashSet<>(this.roles);
	}

	public Date getSecondEchec() {
		if (this.secondEchec != null) {
			return new Date(this.secondEchec.getTime());
		}
		return null;
	}

	public Date getTroisiemeEchec() {
		if (this.troisiemeEchec != null) {
			return new Date(this.troisiemeEchec.getTime());
		}
		return null;
	}

	public boolean isVerrouille() {
		// Calcul de la date avant laquelle un echec de connexion est oublié
		final Date dateLimite = Date.from(Instant.now().minus(NB_JOUR_DELAI_EFFACEMENT_ECHEC, ChronoUnit.DAYS));

		// Annulation des échecs de connexion
		for (int i = 0; i < 3; i++) {
			if ((this.premierEchec != null) && this.premierEchec.before(dateLimite)) {
				this.premierEchec = this.secondEchec;
				this.secondEchec = this.troisiemeEchec;
			}
		}

		return (this.premierEchec != null) && (this.secondEchec != null) && (this.troisiemeEchec != null);
	}

	public void setLogin(final String login) {
		this.login = login;
	}

	public void setMdp(final String mdp) {
		this.mdp = mdp;
	}

	public void setPremierEchec(final Date premierEchec) {
		if (premierEchec != null) {
			this.premierEchec = new Date(premierEchec.getTime());
		}
	}

	public void setRoles(final Set<Role> roles) {
		this.roles = new HashSet<>(roles);
	}

	public void setSecondEchec(final Date secondEchec) {
		if (secondEchec != null) {
			this.secondEchec = new Date(secondEchec.getTime());
		}
	}

	public void setTroisiemeEchec(final Date troisiemeEchec) {
		if (troisiemeEchec != null) {
			this.troisiemeEchec = new Date(troisiemeEchec.getTime());
		}
	}

	@Override
	public String toString() {
		return "Utilisateur [login=" + this.login + ", premierEchec=" + this.premierEchec + ", secondEchec=" + this.secondEchec + ", troisiemeEchec="
				+ this.troisiemeEchec + "]";
	}

}