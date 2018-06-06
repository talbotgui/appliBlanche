package com.guillaumetalbot.applicationblanche.rest.controleur;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.guillaumetalbot.applicationblanche.exception.BusinessException;
import com.guillaumetalbot.applicationblanche.metier.service.ClientService;

/**
 * Ce composant existe uniquement pour vérifier la configuration de la gestion des transactions.
 *
 * Les transactions doivent être au niveau des services et non des controlleurs. Donc, malgré l'exception, le client doit être sauvegardé.
 */
@RestController
public class ComposantPourTestTransactionRestControler {

	@Autowired
	private ClientService clientService;

	/**
	 * Cette URL est totalement fictive et ne fait pas partie de l'application (le présent composant est dans le répertoire src/test/resources !
	 * 
	 * @param refClient
	 * @param nom
	 * @return
	 */
	@RequestMapping(value = "/vTest/clients", method = POST)
	public String sauvegarderClient(//
			@RequestParam(required = false, value = "refClient") final String refClient, //
			@RequestParam(value = "nom") final String nom) {

		// appel à une méthode transactionnelle
		this.clientService.sauvegarderClient(refClient, nom);

		// lancement d'une erreur
		throw new BusinessException(BusinessException.ERREUR_SHA);
	}
}
