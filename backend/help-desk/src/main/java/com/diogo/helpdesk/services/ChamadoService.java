package com.diogo.helpdesk.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.diogo.helpdesk.domain.Chamado;
import com.diogo.helpdesk.domain.Cliente;
import com.diogo.helpdesk.domain.Tecnico;
import com.diogo.helpdesk.domain.dtos.ChamadoDTO;
import com.diogo.helpdesk.domain.enums.Prioridade;
import com.diogo.helpdesk.domain.enums.Status;
import com.diogo.helpdesk.repositories.ChamadoRepository;
import com.diogo.helpdesk.services.exceptions.ObjectNotFoundException;

@Service
public class ChamadoService {
    @Autowired
	private ChamadoRepository repository;
	@Autowired
	private TecnicoService tecnicoService;
	@Autowired
	private ClienteService clienteService;

    public Chamado findById(Integer id) {
        Optional<Chamado> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Chamado não encontrado"));
    }

    public List<Chamado> findAll() {
        return repository.findAll();
    }

    public Chamado create(ChamadoDTO objDTO) {
        return repository.save(newChamado(objDTO));
    }

    public Chamado update(Integer id, @Valid ChamadoDTO objDTO) {
		objDTO.setId(id);
		Chamado oldObj = findById(id);
		oldObj = newChamado(objDTO);
		return repository.save(oldObj);
	}

    private Chamado newChamado(ChamadoDTO obj) {
		System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
		System.out.println("TECNICO: " + obj.getTecnico());
		Tecnico tecnico = tecnicoService.findById(obj.getTecnico());
		System.out.println("CLIENTE: " + obj.getCliente());
		Cliente cliente = clienteService.findById(obj.getCliente());
		System.out.println("A");
		Chamado chamado = new Chamado();
		System.out.println("B");
		
		if(obj.getId() != null) {
			System.out.println("C");
			chamado.setId(obj.getId());
		}
		
		if(obj.getStatus().equals(2)) {
			System.out.println("D");
			chamado.setDataFechamento(LocalDate.now());
		}
		System.out.println("TECNICO: " + chamado.getId());
		chamado.setTecnico(tecnico);
		System.out.println("TECNICO: " + chamado.getTecnico());
		chamado.setCliente(cliente);
		System.out.println("CLIENTE: " + chamado.getCliente());
		chamado.setPrioridade(Prioridade.toEnum(obj.getPrioridade()));
		System.out.println("PRIORIDADE: " + chamado.getPrioridade());
		chamado.setStatus(Status.toEnum(obj.getStatus()));
		System.out.println("STATUS: " + chamado.getStatus());
		chamado.setTitulo(obj.getTitulo());
		System.out.println("TITULO: " + chamado.getTitulo());
		chamado.setObservacoes(obj.getObservacoes());
		System.out.println("OBSERVACOES: " + chamado.getObservacoes());
		System.out.println(chamado.getTecnico() + " " + chamado.getCliente() + " " + chamado.getPrioridade() + " " + chamado.getStatus() + " " + chamado.getTitulo() + " " + chamado.getObservacoes());
		System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n aa");
		return chamado;
	}
}
