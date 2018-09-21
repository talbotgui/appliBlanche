package com.guillaumetalbot.applicationblanche.batch.csvclient.processor;

import java.util.Locale;

import org.springframework.batch.item.ItemProcessor;

import com.guillaumetalbot.applicationblanche.batch.csvclient.dto.LigneCsvImportClient;

/**
 * Classe permettant la transformation d'un type de donnée brut en donnée exploitable.
 */
public class LigneProcessor implements ItemProcessor<LigneCsvImportClient, LigneCsvImportClient> {

	@Override
	public LigneCsvImportClient process(final LigneCsvImportClient ligne) throws Exception {
		ligne.setNomClient(ligne.getNomClient().toUpperCase(Locale.FRANCE));
		return ligne;
	}

}
