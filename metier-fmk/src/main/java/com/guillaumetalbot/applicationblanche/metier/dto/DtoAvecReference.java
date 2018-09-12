package com.guillaumetalbot.applicationblanche.metier.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.guillaumetalbot.applicationblanche.metier.entite.Entite;
import com.guillaumetalbot.applicationblanche.metier.entite.IdentifiableParReference;

public abstract class DtoAvecReference implements Serializable, IdentifiableParReference {
	private static final long serialVersionUID = 1L;

	@JsonIgnore
	private Long id;

	public DtoAvecReference() {
		super();
	}

	public DtoAvecReference(final Long id) {
		super();
		this.id = id;
	}

	protected abstract Class<? extends IdentifiableParReference> getClasseEntiteCorrespondante();

	@JsonIgnore
	public Long getId() {
		return this.id;
	}

	@Override
	public String getReference() {
		return Entite.genererReference(this.getClasseEntiteCorrespondante(), this.id);
	}

	public void setId(final Long id) {
		this.id = id;
	}

	@Override
	public void setReference(final String reference) {
		this.id = Entite.extraireIdentifiant(reference, this.getClasseEntiteCorrespondante());
	}

}
