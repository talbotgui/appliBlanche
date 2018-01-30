package com.guillaumetalbot.applicationblanche.rest.controleur;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.guillaumetalbot.applicationblanche.metier.entite.client.Client;
import com.guillaumetalbot.applicationblanche.metier.service.ClientService;

@RestController
public class ClientRestControler {

	@Autowired
	private ClientService clientService;

	@RequestMapping(value = "/client/{refClient}", method = GET)
	public Client chargerClient(@PathVariable("refClient") final String refClient) {
		return this.clientService.chargerClientReadonly(refClient);
	}

	@RequestMapping(value = "/client/{refClient}", method = GET, produces = "application/json;details")
	public Client chargerClientAvecDossiers(@PathVariable("refClient") final String refClient) {
		return this.clientService.chargerClientAvecAdresseEtDossiersReadonly(refClient);
	}

	@RequestMapping(value = "/client", method = GET)
	public Collection<Client> listerClients() {
		return this.clientService.listerClients();
	}

	@RequestMapping(value = "/client", method = POST)
	public String sauvegarderClient(//
			@RequestParam(required = false, value = "id") final String refClient, //
			@RequestParam(value = "nom") final String nom) {
		return this.clientService.sauvegarderClient(refClient, nom);
	}
}
