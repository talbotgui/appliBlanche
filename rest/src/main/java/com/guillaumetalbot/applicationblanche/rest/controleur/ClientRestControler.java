package com.guillaumetalbot.applicationblanche.rest.controleur;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.guillaumetalbot.applicationblanche.metier.entite.client.Client;
import com.guillaumetalbot.applicationblanche.metier.service.ClientService;
import com.guillaumetalbot.applicationblanche.rest.application.RestApplication;
import com.guillaumetalbot.applicationblanche.rest.controleur.utils.RestControlerUtils;

@RestController
public class ClientRestControler {

	@Autowired
	private ClientService clientService;

	@RequestMapping(value = "/v1/clients/{refClient}", method = GET)
	public Client chargerClient(@RequestHeader("Accept") final String accept, @PathVariable("refClient") final String refClient) {
		if (RestApplication.MIME_JSON_DETAILS.equals(accept)) {
			return this.clientService.chargerClientAvecAdresseEtDossiersReadonly(refClient);
		} else {
			return this.clientService.chargerClientReadonly(refClient);
		}
	}

	/**
	 * Fonction de recherche de résumés de client (ClientDto) avec pagination optionnelle.
	 *
	 * @param pageSize
	 *            Paramètre de pagination optionnel
	 * @param pageNumber
	 *            Paramètre de pagination optionnel
	 * @return
	 */
	@RequestMapping(value = "/v1/clients", method = GET)
	public Object listerClientDto(@RequestParam(required = false, value = "pageSize") final Integer pageSize,
			@RequestParam(required = false, value = "pageNumber") final Integer pageNumber) {
		final Pageable page = RestControlerUtils.creerPageSiPossible(pageSize, pageNumber);
		if (page != null) {
			return this.clientService.listerClientsDto(new QPageRequest(pageNumber, pageSize));
		} else {
			return this.clientService.listerClients();
		}
	}

	@RequestMapping(value = "/v1/clients", method = POST)
	public String sauvegarderClient(//
			@RequestParam(required = false, value = "refClient") final String refClient, //
			@RequestParam(value = "nom") final String nom) {
		return this.clientService.sauvegarderClient(refClient, nom);
	}
}
