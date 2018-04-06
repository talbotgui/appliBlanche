package com.guillaumetalbot.applicationblanche.metier.service;

import java.util.HashMap;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.guillaumetalbot.applicationblanche.metier.dao.i18n.LibelleRepository;
import com.guillaumetalbot.applicationblanche.metier.entite.i18n.Libelle;

@Service
@Transactional
public class LibelleServiceImpl implements LibelleService {

	@Autowired
	private LibelleRepository libelleRepo;

	@Override
	public Map<String, String> listerParLangue(final String langue) {
		final Map<String, String> map = new HashMap<>();
		for (final Libelle l : this.libelleRepo.listerParLangue(langue)) {
			map.put(l.getClef(), l.getLibelle());
		}
		return map;
	}
}
