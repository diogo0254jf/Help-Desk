package com.diogo.helpdesk.services;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.diogo.helpdesk.domain.Chamado;
import com.diogo.helpdesk.domain.Cliente;
import com.diogo.helpdesk.domain.Tecnico;
import com.diogo.helpdesk.domain.enums.Perfil;
import com.diogo.helpdesk.domain.enums.Prioridade;
import com.diogo.helpdesk.domain.enums.Status;
import com.diogo.helpdesk.repositories.ChamadoRepository;
import com.diogo.helpdesk.repositories.ClienteRepository;
import com.diogo.helpdesk.repositories.TecnicoRepository;

@Service
public class DBService {
    @Autowired
	private TecnicoRepository tecnicoRepository;

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private ChamadoRepository chamadoRepository;
	
    
    public void instanciaDB() {
        Tecnico tec1 = new Tecnico(null, "Diogo", "123.456.789-00", "diogo@mail.com", "123");
		tec1.addPerfil(Perfil.ADMIN);

		Cliente cli1 = new Cliente(null, "Ana", "987.654.321-00", "ana@mail.com", "123");
		
		Chamado c1 = new Chamado(null, Prioridade.MEDIA, Status.ANDAMENTO, "Chamado 1", "Primeiro chamado", tec1, cli1);
		
		tecnicoRepository.saveAll(Arrays.asList(tec1));
		clienteRepository.saveAll(Arrays.asList(cli1));
		chamadoRepository.saveAll(Arrays.asList(c1));
		
    }
}
