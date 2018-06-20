package com.guillaumetalbot.applicationblanche.rest.controleur.dto;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PageablePourLesTest<T> implements Page<T> {

	private final List<T> contenu;

	public PageablePourLesTest() {
		super();
		this.contenu = new ArrayList<>();
	}

	public PageablePourLesTest(final List<T> contenu) {
		super();
		this.contenu = contenu;
	}

	@Override
	public List<T> getContent() {
		return this.contenu;
	}

	@Override
	public int getNumber() {
		return 1;
	}

	@Override
	public int getNumberOfElements() {
		return 1;
	}

	@Override
	public int getSize() {
		return 1;
	}

	@Override
	public Sort getSort() {
		return null;
	}

	@Override
	public long getTotalElements() {
		return 1;
	}

	@Override
	public int getTotalPages() {
		return 1;
	}

	@Override
	public boolean hasContent() {
		return true;
	}

	@Override
	public boolean hasNext() {
		return false;
	}

	@Override
	public boolean hasPrevious() {
		return false;
	}

	@Override
	public boolean isFirst() {
		return false;
	}

	@Override
	public boolean isLast() {
		return false;
	}

	@Override
	public Iterator<T> iterator() {
		return null;
	}

	@Override
	public <U> Page<U> map(final Function<? super T, ? extends U> arg0) {
		return null;
	}

	@Override
	public Pageable nextPageable() {
		return null;
	}

	@Override
	public Pageable previousPageable() {
		return null;
	}
}
