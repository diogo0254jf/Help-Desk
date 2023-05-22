package com.diogo.helpdesk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.diogo.helpdesk.domain.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
    
}
