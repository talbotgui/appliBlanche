package com.guillaumetalbot.applicationblanche.metier.entite;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class Entite implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;

	public Entite() {
		super();
	}

	public Entite(final Long id) {
		super();
		this.id = id;
	}

	public Long getId() {
		return this.id;
	}

}
