package com.diogo.helpdesk.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.diogo.helpdesk.domain.Cliente;
import com.diogo.helpdesk.domain.Pessoa;
import com.diogo.helpdesk.domain.dtos.ClienteDTO;
import com.diogo.helpdesk.repositories.ClienteRepository;
import com.diogo.helpdesk.repositories.PessoaRepository;
import com.diogo.helpdesk.services.exceptions.DataIntegrityViolationException;
import com.diogo.helpdesk.services.exceptions.ObjectNotFoundExeption;

@Service
public class ClienteService {
    @Autowired
    private ClienteRepository ClienteRepository;

    @Autowired
    private PessoaRepository pessoaRepository;

    public Cliente findById(Integer id) {
        Optional<Cliente> obj = ClienteRepository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundExeption("Object Not Found"));
    }

    public List<Cliente> findAll() {
        return ClienteRepository.findAll();
    }

    public Cliente create(ClienteDTO objDTO) {
        objDTO.setId(null);
        validaPorCpfEEmail(objDTO);
        Cliente newObj = new Cliente(objDTO);
        return ClienteRepository.save(newObj);
    }

    public Cliente update(Integer id, ClienteDTO objDTO) {
        objDTO.setId(id);
        Cliente oldObj = findById(id);
        validaPorCpfEEmail(objDTO);
        oldObj = new Cliente(objDTO);
        return ClienteRepository.save(oldObj);
    }

    public void delete(Integer id) {
        Cliente obj = findById(id);
        if (obj.getId() != null) {
            ClienteRepository.deleteById(id);
        }
    }

    private void validaPorCpfEEmail(ClienteDTO newObj) {
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