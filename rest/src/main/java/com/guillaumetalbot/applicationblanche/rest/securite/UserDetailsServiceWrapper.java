package com.guillaumetalbot.applicationblanche.rest.securite;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.guillaumetalbot.applicationblanche.metier.entite.securite.Utilisateur;
import com.guillaumetalbot.applicationblanche.metier.service.SecuriteService;

/**
 * Wrapper permettant d'adapter le service métier de l'application et les interfaces de Spring (UserDetails et UserDetailsService)
 */
@Service
public class UserDetailsServiceWrapper implements UserDetailsService {

	@Autowired
	private SecuriteService securiteService;

	@Override
	public UserDetails loadUserByUsername(final String login) throws UsernameNotFoundException {
		final Utilisateur u = this.securiteService.chargerUtilisateurReadOnly(login);
		if (u == null) {
			throw new UsernameNotFoundException("Mauvais paramètres de connexion");
		}
		if (u.isVerrouille()) {
			throw new UsernameNotFoundException("Compte verrouille");
		}
		return new UserDetailsDto(u);
	}

	public void notifierConnexion(final String login, final boolean status) {
		this.securiteService.notifierConnexion(login, status);
	}

}
