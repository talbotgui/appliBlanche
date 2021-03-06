package com.guillaumetalbot.applicationblanche.rest.securite;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.guillaumetalbot.applicationblanche.metier.entite.securite.Utilisateur;

/** Classe mappant un utilisateur de Spring avec l'entité métier du projet */
public class UserDetailsDto implements UserDetails {
	private static final long serialVersionUID = 1L;

	/** Composition avec un utilisateur. */
	private final Utilisateur utilisateur;

	public UserDetailsDto(final Utilisateur utilisateur) {
		super();
		this.utilisateur = utilisateur;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return new ArrayList<>();
	}

	@Override
	public String getPassword() {
		return this.utilisateur.getMdp();
	}

	@Override
	public String getUsername() {
		return this.utilisateur.getLogin();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return !this.utilisateur.isVerrouille();
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
