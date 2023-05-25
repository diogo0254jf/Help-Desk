package com.diogo.helpdesk.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.diogo.helpdesk.domain.Cliente;
import com.diogo.helpdesk.domain.Pessoa;
import com.diogo.helpdesk.domain.dtos.ClienteDTO;
import com.diogo.helpdesk.repositories.ClienteRepository;
import com.diogo.helpdesk.repositories.PessoaRepository;
import com.diogo.helpdesk.services.exceptions.DataIntegrityViolationException;
import com.diogo.helpdesk.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
    @Autowired
    private ClienteRepository repository;

    @Autowired
    private PessoaRepository pessoaRepository;
    @Autowired
    private BCryptPasswordEncoder encoder;

    public Cliente findById(Integer id) {
        Optional<Cliente> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id));
    }

    public List<Cliente> findAll() {
        return repository.findAll();
    }

    public Cliente create(ClienteDTO objDTO) {
        objDTO.setId(null);
        objDTO.setSenha(encoder.encode(objDTO.getSenha()));
        validaPorCpfEEmail(objDTO);
        Cliente newObj = new Cliente(objDTO);
        return repository.save(newObj);
    }

    public Cliente update(Integer id, ClienteDTO objDTO) {
        objDTO.setId(id);
        Cliente oldObj = findById(id);
        validaPorCpfEEmail(objDTO);
        oldObj = new Cliente(objDTO);
        return repository.save(oldObj);
    }

    public void delete(Integer id) {
        Cliente obj = findById(id);
        if (obj.getId() != null) {
            repository.deleteById(id);
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
