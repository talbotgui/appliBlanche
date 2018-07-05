package com.guillaumetalbot.applicationblanche.metier.dao.client;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.guillaumetalbot.applicationblanche.metier.dto.ClientDto;

public interface ClientDtoRepository {

	Page<ClientDto> listerClientsDto(Pageable requete);

}
