package com.guillaumetalbot.applicationblanche.batch.jsonclient;

import java.util.Map;

import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.JsonLineMapper;

import com.guillaumetalbot.applicationblanche.batch.csvclient.dto.LigneCsvImportClient;

/**
 * Code inspiré de
 * https://github.com/debop/spring-batch-experiments/blob/master/chapter05/src/main/java/kr/spring/batch/chapter05/file/WrappedJsonLineMapper.java
 */
public class ClientJsonLineMapper implements LineMapper<LigneCsvImportClient> {

	private JsonLineMapper delegate;

	@Override
	public LigneCsvImportClient mapLine(final String line, final int lineNumber) throws Exception {

		final Map<String, Object> map = this.delegate.mapLine(line, lineNumber);

		final LigneCsvImportClient ligne = new LigneCsvImportClient();
		ligne.setCodePostal((String) map.get("codePostal"));
		ligne.setNomClient((String) map.get("nomClient"));
		ligne.setRue((String) map.get("rue"));
		ligne.setVille((String) map.get("ville"));

		return ligne;
	}

	public void setDelegate(final JsonLineMapper delegate) {
		this.delegate = delegate;
	}

}
