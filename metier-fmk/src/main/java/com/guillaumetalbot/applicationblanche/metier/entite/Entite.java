package com.guillaumetalbot.applicationblanche.metier.entite;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.guillaumetalbot.applicationblanche.exception.BusinessException;

@MappedSuperclass
public class Entite implements Serializable, IdentifiableParReference {
	private static final Pattern PATTERN_REFERENCE = Pattern.compile("^([\\-]?[0-9]*)-([0-9]*)$");

	/** Sel utilisé pour générer et lire des références */
	/* donnée présente dans un fichier de configuration et initialisée au démarrage de l'application */
	public static long selPourReference;

	private static final long serialVersionUID = 1L;

	public static Long extraireIdentifiant(final String reference, final Class<? extends IdentifiableParReference> clazz) {
		// Si la référence est nulle
		if (StringUtils.isEmpty(reference)) {
			return null;
		}

		// Si la référence n'est pas sur le bon modèle
		final Matcher matcher = PATTERN_REFERENCE.matcher(reference);
		if (!matcher.find()) {
			throw new BusinessException(BusinessException.REFERENCE_NON_VALIDE, reference);
		}

		// Extraction des nombres
		final long objet = Long.decode(matcher.group(1)) - selPourReference;
		final long id = Long.decode(matcher.group(2)) - selPourReference;

		// Validation des nombres
		if (clazz.getName().hashCode() != objet) {
			throw new BusinessException(BusinessException.REFERENCE_NON_VALIDE, reference);
		}
		if (id < 0) {
			throw new BusinessException(BusinessException.REFERENCE_NON_VALIDE, reference);
		}

		// Initialisation de l'identifiant
		return id;

	}

	public static String genererReference(final Class<? extends IdentifiableParReference> clazz, final Long id) {
		if (id == null || clazz == null) {
			return null;
		}
		return selPourReference + clazz.getName().hashCode() + "-" + (selPourReference + id);
	}

	@Id
	@GeneratedValue
	@JsonIgnore
	private Long id;

	public Long getId() {
		return this.id;
	}

	@Override
	public String getReference() {
		return Entite.genererReference(this.getClass(), this.id);
	}

	@Override
	public void setReference(final String reference) {
		this.id = Entite.extraireIdentifiant(reference, this.getClass());
	}

}
