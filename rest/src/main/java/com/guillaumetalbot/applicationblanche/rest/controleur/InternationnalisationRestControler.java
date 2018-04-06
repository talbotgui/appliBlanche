package com.guillaumetalbot.applicationblanche.rest.controleur;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.guillaumetalbot.applicationblanche.metier.service.LibelleService;

@RestController
@CrossOrigin
public class InternationnalisationRestControler {

	@Autowired
	private LibelleService libelleService;

	@RequestMapping(value = "/i18n/{langue}", method = GET)
	public Map<String, String> chargerLibelles(@PathVariable(value = "langue") final String langue) {
		return this.libelleService.listerParLangue(langue);
	}
}
