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

	@RequestMapping(value = "/client/{idClient}", method = GET)
	public Client chargerClient(@PathVariable("idClient") final Long idClient) {
		return this.clientService.chargerClientReadonly(idClient);
	}

	@RequestMapping(value = "/client/{idClient}", method = GET, produces = "application/json;details")
	public Client chargerClientAvecDossiers(@PathVariable("idClient") final Long idClient) {
		return this.clientService.chargerClientAvecAdresseEtDossiersReadonly(idClient);
	}

	@RequestMapping(value = "/client", method = GET)
	public Collection<Client> listerClients() {
		return this.clientService.listerClients();
	}

	@RequestMapping(value = "/client", method = POST)
	public Long sauvegarderClient(//
			@RequestParam(required = false, value = "id") final Long id, //
			@RequestParam(value = "nom") final String nom) {
		return this.clientService.sauvegarderClient(id, nom);
	}
}
