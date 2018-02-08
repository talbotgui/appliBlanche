package com.guillaumetalbot.applicationblanche.rest.controleur;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.guillaumetalbot.applicationblanche.metier.entite.client.Client;
import com.guillaumetalbot.applicationblanche.metier.service.ClientService;
import com.guillaumetalbot.applicationblanche.rest.application.RestApplication;

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

	@RequestMapping(value = "/v1/clients", method = GET)
	public Collection<Client> listerClients() {
		return this.clientService.listerClients();
	}

	@RequestMapping(value = "/v1/clients", method = POST)
	public String sauvegarderClient(//
			@RequestParam(required = false, value = "refClient") final String refClient, //
			@RequestParam(value = "nom") final String nom) {
		return this.clientService.sauvegarderClient(refClient, nom);
	}
}
