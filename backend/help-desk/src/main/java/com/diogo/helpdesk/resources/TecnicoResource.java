package com.diogo.helpdesk.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.diogo.helpdesk.domain.Tecnico;
import com.diogo.helpdesk.domain.dtos.TecnicoDTO;
import com.diogo.helpdesk.services.TecnicoService;

@RestController
@RequestMapping(value = "/tecnicos")
public class TecnicoResource {

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
    public ResponseEntity<Tecnico> create(@Valid @RequestBody TecnicoDTO objDTO) {
        objDTO.setId(null);
        Tecnico newObj = service.create(objDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newObj.getId()).toUri();
        return ResponseEntity.created(uri).body(newObj);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<TecnicoDTO> update(@PathVariable Integer id, @RequestBody TecnicoDTO objDTO) {
        Tecnico obj = service.update(id, objDTO);
        return ResponseEntity.status(HttpStatus.OK).body(new TecnicoDTO(obj));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Integer> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(id);
    }
}