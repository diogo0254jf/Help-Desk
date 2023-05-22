package com.diogo.helpdesk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.diogo.helpdesk.domain.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Integer> {
    
}
