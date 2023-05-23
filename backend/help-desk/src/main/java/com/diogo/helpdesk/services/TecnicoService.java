package com.diogo.helpdesk.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.diogo.helpdesk.domain.Tecnico;
import com.diogo.helpdesk.repositories.TecnicoRepository;
import com.diogo.helpdesk.services.exceptions.ObjectNotFoundExeption;

@Service
public class TecnicoService {
    @Autowired
    private TecnicoRepository tecnicoRepository;

    public Tecnico findById(Integer id) {
        Optional<Tecnico> obj = tecnicoRepository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundExeption("Object Not Found"));
    }

    public List<Tecnico> findAll() {
        return tecnicoRepository.findAll();
    }

}
