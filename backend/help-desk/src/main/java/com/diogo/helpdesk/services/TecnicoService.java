package com.diogo.helpdesk.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.diogo.helpdesk.domain.Pessoa;
import com.diogo.helpdesk.domain.Tecnico;
import com.diogo.helpdesk.domain.dtos.TecnicoDTO;
import com.diogo.helpdesk.repositories.PessoaRepository;
import com.diogo.helpdesk.repositories.TecnicoRepository;
import com.diogo.helpdesk.services.exceptions.DataIntegrityViolationException;
import com.diogo.helpdesk.services.exceptions.ObjectNotFoundException;

@Service
public class TecnicoService {
    @Autowired
    private TecnicoRepository tecnicoRepository;

    @Autowired
    private PessoaRepository pessoaRepository;

    public Tecnico findById(Integer id) {
        Optional<Tecnico> obj = tecnicoRepository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Object Not Found"));
    }

    public List<Tecnico> findAll() {
        return tecnicoRepository.findAll();
    }

    public Tecnico create(TecnicoDTO objDTO) {
        objDTO.setId(null);
        validaPorCpfEEmail(objDTO);
        Tecnico newObj = new Tecnico(objDTO);
        return tecnicoRepository.save(newObj);
    }

    public Tecnico update(Integer id, TecnicoDTO objDTO) {
        objDTO.setId(id);
        Tecnico oldObj = findById(id);
        validaPorCpfEEmail(objDTO);
        oldObj = new Tecnico(objDTO);
        return tecnicoRepository.save(oldObj);
    }

    public void delete(Integer id) {
        Tecnico obj = findById(id);
        if (obj.getId() != null) {
            tecnicoRepository.deleteById(id);
        }
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
}
