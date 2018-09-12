package com.guillaumetalbot.applicationblanche.rest.controleur;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.guillaumetalbot.applicationblanche.rest.dto.ElementMonitoring;
import com.jamonapi.MonKey;
import com.jamonapi.Monitor;
import com.jamonapi.MonitorFactory;

/**
 * Classe exposant les données de monitoring générées par JaMon. *
 */
@RestController
public class MonitoringRestControler {

	private static final String CLEF_A_EXCLURE_1 = "MonProxy-Interface";
	private static final String CLEF_A_EXCLURE_2 = "com.jamonapi.Exceptions";
	private static final String CLEF_A_EXCLURE_3 = "java.sql.SQLFeatureNotSupportedException";

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/monitoring", method = GET)
	public Collection<ElementMonitoring> lireDonneesDuMonitoring() {

		final Collection<ElementMonitoring> resultats = new ArrayList<>();
		for (final Map.Entry<MonKey, Monitor> e : (Collection<Map.Entry<MonKey, Monitor>>) MonitorFactory.getMap().entrySet()) {
			final String clef = e.getKey().getDetails().toString();
			if (clef.startsWith(CLEF_A_EXCLURE_1) || clef.startsWith(CLEF_A_EXCLURE_2) || clef.startsWith(CLEF_A_EXCLURE_3)) {
				continue;
			}

			final Double nbAppels = e.getValue().getHits();
			final Double tempsMin = e.getValue().getMin();
			final Double tempsMoyen = e.getValue().getAvg();
			final Double tempsMax = e.getValue().getMax();
			final Double tempsCumule = e.getValue().getTotal();

			resultats.add(new ElementMonitoring(clef, nbAppels, tempsMin, tempsMoyen, tempsMax, tempsCumule));
		}

		return resultats;
	}
}
