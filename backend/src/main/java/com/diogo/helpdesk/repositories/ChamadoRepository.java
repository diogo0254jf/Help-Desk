package com.diogo.helpdesk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.diogo.helpdesk.domain.Chamado;

public interface ChamadoRepository extends JpaRepository<Chamado, Integer> {
    
}
