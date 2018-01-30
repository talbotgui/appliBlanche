package com.guillaumetalbot.applicationblanche.metier.entite;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import com.guillaumetalbot.applicationblanche.exception.BusinessException;

@MappedSuperclass
public class Entite implements Serializable, IdentifiableParReference {
	private static final Pattern PATTERN_REFERENCE = Pattern.compile("([0-9]*)-([0-9]*)");
	private static final long SEL_POUR_REFERENCE = 29091985;

	private static final long serialVersionUID = 1L;

	public static Long extraireIdentifiant(final String reference, final Class<? extends IdentifiableParReference> clazz) {
		// Si la référence est nulle
		if (reference == null) {
			return null;
		}

		// Si la référence n'est pas sur le bon modèle
		final Matcher matcher = PATTERN_REFERENCE.matcher(reference);
		if (!matcher.find()) {
			throw new BusinessException(BusinessException.OBJET_NON_EXISTANT, reference);
		}

		// Extraction des nombres
		final long objet = Long.decode(matcher.group(1)) - SEL_POUR_REFERENCE;
		final long id = Long.decode(matcher.group(2)) - SEL_POUR_REFERENCE;

		// Validation des nombres
		if (clazz.getName().hashCode() != objet) {
			throw new BusinessException(BusinessException.OBJET_NON_EXISTANT, reference);
		}
		if (id < 0) {
			throw new BusinessException(BusinessException.OBJET_NON_EXISTANT, reference);
		}

		// Initialisation de l'identifiant
		return id;

	}

	public static String genererReference(final Class<? extends IdentifiableParReference> clazz, final Long id) {
		if ((id == null) || (clazz == null)) {
			return null;
		}
		final String ref = (SEL_POUR_REFERENCE + clazz.getName().hashCode()) + "-" + (SEL_POUR_REFERENCE + id);
		return ref;
	}

	@Id
	@GeneratedValue
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
