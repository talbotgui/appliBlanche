package com.guillaumetalbot.applicationblanche.rest.controleur;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.guillaumetalbot.applicationblanche.rest.controleur.utils.RestControlerUtils;
import com.guillaumetalbot.applicationblanche.rest.dto.ElementMonitoring;
import com.jamonapi.MonKey;
import com.jamonapi.Monitor;
import com.jamonapi.MonitorFactory;

import io.swagger.annotations.ApiOperation;

/**
 * Classe exposant les données de monitoring générées par JaMon. *
 */
@RestController
public class MonitoringRestControler {

	private static final String CLEF_A_EXCLURE_1 = "MonProxy-Interface";
	private static final String CLEF_A_EXCLURE_2 = "com.jamonapi.Exceptions";
	private static final String CLEF_A_EXCLURE_3 = "java.sql.SQLFeatureNotSupportedException";

	@GetMapping(value = "/monitoring")
	@ApiOperation(value = "Lire les données de monitoring", notes = "Lecture paginée")
	public Page<ElementMonitoring> lireDonneesDuMonitoring(@RequestParam(required = false, value = "pageSize") final Integer pageSize,
			@RequestParam(required = false, value = "pageNumber") final Integer pageNumber,
			@RequestParam(required = false, value = "triPar") final String triPar,
			@RequestParam(required = false, value = "ordreTri") final Boolean ordreTri) {

		Pageable page = RestControlerUtils.creerPageSiPossible(pageSize, pageNumber, RestControlerUtils.creerTriSiPossible(triPar, ordreTri));
		if (page == null) {
			page = new QPageRequest(0, 20);
		}

		return this.rechercherPageDeMonitoring(page);
	}

	@SuppressWarnings("unchecked")
	private Page<ElementMonitoring> rechercherPageDeMonitoring(final Pageable page) {

		// Récupération de la liste des clefs
		final Set<Map.Entry<MonKey, Monitor>> entreesSet = MonitorFactory.getMap().entrySet();

		// Suppression des clefs à exclures
		final List<Map.Entry<MonKey, Monitor>> entrees = entreesSet.stream().filter((entree) -> {
			final String clef = entree.getKey().getDetails().toString();
			return !(clef.startsWith(CLEF_A_EXCLURE_1) || clef.startsWith(CLEF_A_EXCLURE_2) || clef.startsWith(CLEF_A_EXCLURE_3));
		}).collect(Collectors.toList());

		// Tri
		if (page.getSort() != null && page.getSort().isSorted()) {
			Comparator<Map.Entry<MonKey, Monitor>> comparator = null;

			// Par nbAppels
			if (page.getSort().getOrderFor("nbAppels") != null) {
				final int sens = page.getSort().getOrderFor("nbAppels").isAscending() ? 1 : -1;
				comparator = (e1, e2) -> (int) (sens * 100 * (e1.getValue().getHits() - e2.getValue().getHits()));
			}

			// Par tempsCumule
			else if (page.getSort().getOrderFor("tempsCumule") != null) {
				final int sens = page.getSort().getOrderFor("tempsCumule").isAscending() ? 1 : -1;
				comparator = (e1, e2) -> (int) (sens * 100 * (e1.getValue().getTotal() - e2.getValue().getTotal()));
			}

			// Par tempsMoyen
			else if (page.getSort().getOrderFor("tempsMoyen") != null) {
				final int sens = page.getSort().getOrderFor("tempsMoyen").isAscending() ? 1 : -1;
				comparator = (e1, e2) -> (int) (sens * 100 * (e1.getValue().getAvg() - e2.getValue().getAvg()));
			}

			// Par tempsMax
			else if (page.getSort().getOrderFor("tempsMax") != null) {
				final int sens = page.getSort().getOrderFor("tempsMax").isAscending() ? 1 : -1;
				comparator = (e1, e2) -> (int) (sens * 100 * (e1.getValue().getMax() - e2.getValue().getMax()));
			}

			// Par tempsMin
			else if (page.getSort().getOrderFor("tempsMin") != null) {
				final int sens = page.getSort().getOrderFor("tempsMin").isAscending() ? 1 : -1;
				comparator = (e1, e2) -> (int) (sens * 100 * (e1.getValue().getMin() - e2.getValue().getMin()));
			}

			// par clef (par défaut)
			else {
				final int sens = page.getSort().getOrderFor("clef").isAscending() ? 1 : -1;
				comparator = (e1, e2) -> sens
						* e1.getKey().getDetails().toString().toUpperCase().compareTo(e2.getKey().getDetails().toString().toUpperCase());

			}
			Collections.sort(entrees, comparator);
		}

		// Pagination
		final List<Map.Entry<MonKey, Monitor>> entreesArenvoyer = new ArrayList<>();
		for (int i = (int) page.getOffset(); i < page.getOffset() + page.getPageSize() && i < entrees.size(); i++) {
			entreesArenvoyer.add(entrees.get(i));
		}

		// récupération des résultats
		final List<ElementMonitoring> resultats = new ArrayList<>();
		for (final Map.Entry<MonKey, Monitor> e : entreesArenvoyer) {
			final String clef = e.getKey().getDetails().toString();
			final Double nbAppels = e.getValue().getHits();
			final Double tempsMin = e.getValue().getMin();
			final Double tempsMoyen = e.getValue().getAvg();
			final Double tempsMax = e.getValue().getMax();
			final Double tempsCumule = e.getValue().getTotal();
			resultats.add(new ElementMonitoring(clef, nbAppels, tempsMin, tempsMoyen, tempsMax, tempsCumule));
		}

		return new PageImpl<ElementMonitoring>(resultats, page, entrees.size());

	}
}
