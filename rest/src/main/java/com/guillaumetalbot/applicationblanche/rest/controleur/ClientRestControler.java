package com.guillaumetalbot.applicationblanche.rest.controleur;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.guillaumetalbot.applicationblanche.metier.entite.client.Client;
import com.guillaumetalbot.applicationblanche.metier.service.ClientService;
import com.guillaumetalbot.applicationblanche.rest.controleur.utils.RestControlerUtils;

@RestController
@RequestMapping("/v1/clients")
public class ClientRestControler {

	@Autowired
	private ClientService clientService;

	@GetMapping(value = "/{refClient}")
	public Client chargerClient(@RequestHeader("Accept") final String accept, @PathVariable("refClient") final String refClient) {
		if (RestControlerUtils.MIME_JSON_DETAILS.equals(accept)) {
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
	 * @param triParNom
	 *            Tri par le nom du client
	 * @return
	 */
	@GetMapping(value = "")
	public Object listerClientDto(@RequestParam(required = false, value = "pageSize") final Integer pageSize,
			@RequestParam(required = false, value = "pageNumber") final Integer pageNumber,
			@RequestParam(required = false, value = "triParNom") final Boolean triParNom) {
		final Sort tri = RestControlerUtils.creerTriSiPossible("nom", triParNom);
		final Pageable page = RestControlerUtils.creerPageSiPossible(pageSize, pageNumber, tri);
		if (page != null) {
			return this.clientService.listerClientsDto(page);
		} else {
			return this.clientService.listerClients();
		}
	}

	@PostMapping(value = "")
	public String sauvegarderClient(//
			@RequestParam(required = false, value = "reference") final String reference, //
			@RequestParam(value = "nom") final String nom) {
		return '"' + this.clientService.sauvegarderClient(reference, nom) + '"';
	}

	@DeleteMapping(value = "/{refClient}")
	public void supprimerClient(@PathVariable("refClient") final String refClient) {
		this.clientService.supprimerClient(refClient);
	}
}
