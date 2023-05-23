package com.diogo.helpdesk.resources;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.diogo.helpdesk.domain.Pessoa;
import com.diogo.helpdesk.domain.Tecnico;
import com.diogo.helpdesk.domain.dtos.TecnicoDTO;
import com.diogo.helpdesk.repositories.PessoaRepository;
import com.diogo.helpdesk.services.TecnicoService;
import com.diogo.helpdesk.services.exceptions.DataIntegrityViolationException;

@RestController
@RequestMapping(value = "/tecnicos")
public class TecnicoResource {
    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private TecnicoService service;

    @GetMapping(value = "/{id}")
    public ResponseEntity<TecnicoDTO> findById(@PathVariable Integer id) {
        Tecnico obj = service.findById(id);
        return ResponseEntity.status(HttpStatus.FOUND).body(new TecnicoDTO(obj));
    }

    @GetMapping
    public ResponseEntity<List<TecnicoDTO>> findAll() {
        List<Tecnico> list = service.findAll();
        List<TecnicoDTO> listdto = list.stream().map(obj -> new TecnicoDTO(obj)).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.FOUND).body(listdto);
    }

    @PostMapping
    public ResponseEntity<Tecnico> create(@RequestBody TecnicoDTO objDTO) {
        objDTO.setId(null);
        validaPorCpfEEmail(objDTO);
        Tecnico newObj = service.create(objDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newObj.getId()).toUri();
        return ResponseEntity.created(uri).body(newObj);
    }

    private void validaPorCpfEEmail(TecnicoDTO newObj) {
        Optional<Pessoa> obj = pessoaRepository.findByCpf(newObj.getCpf());

        if (obj.isPresent() && obj.get().getId() != newObj.getId()) {
            throw new DataIntegrityViolationException("CPF já cadastrado no sistema");
        }

        obj = pessoaRepository.findByEmail(newObj.getEmail());

        if (obj.isPresent() && obj.get().getId() != newObj.getId()) {
            throw new DataIntegrityViolationException("Email já cadastrado no sistema");
        }

    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Integer> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(id);
    }
}