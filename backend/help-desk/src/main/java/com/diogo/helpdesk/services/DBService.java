package com.diogo.helpdesk.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
	@Autowired
	private BCryptPasswordEncoder encoder;
	public void instanciaDB() {

		Tecnico tec1 = new Tecnico(null, "Diogo", "123.456.789-00", "diogo@mail.com", encoder.encode("123"));
		tec1.addPerfil(Perfil.ADMIN);
		Cliente cli1 = new Cliente(null, "Ana", "987.654.321-00", "ana@mail.com", encoder.encode("123"));
		Chamado c1 = new Chamado(null, Prioridade.MEDIA, Status.ANDAMENTO, "Chamado 1", "Primeiro chamado", tec1, cli1);

		tecnicoRepository.save(tec1);
		clienteRepository.save(cli1);
		chamadoRepository.save(c1);

		Tecnico tec2 = new Tecnico(null, "João", "111.222.333-00", "joao@mail.com", encoder.encode("456"));
		tec2.addPerfil(Perfil.CLIENTE);
		Cliente cli2 = new Cliente(null, "Maria", "444.555.666-00", "maria@mail.com", encoder.encode("789"));
		Chamado c2 = new Chamado(null, Prioridade.BAIXA, Status.ABERTO, "Chamado 2", "Segundo chamado", tec2, cli2);

		tecnicoRepository.save(tec2);
		clienteRepository.save(cli2);
		chamadoRepository.save(c2);

		

	}
}
