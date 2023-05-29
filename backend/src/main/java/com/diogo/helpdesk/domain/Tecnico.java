package com.diogo.helpdesk.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import com.diogo.helpdesk.domain.dtos.TecnicoDTO;
import com.diogo.helpdesk.domain.enums.Perfil;

@Entity
public class Tecnico extends Pessoa {
    private static final long serialVersionUID = 1L;

    @OneToMany(mappedBy = "tecnico")
    private List<Chamado> chamados = new ArrayList<>();

    public Tecnico() {
        super();
        addPerfil(Perfil.CLIENTE);
    }


    public Tecnico(Integer id, String nome, String cpf, String email, String senha) {
        super(id, nome, cpf, email, senha);
    }

    public Tecnico(TecnicoDTO objDTO) {
        super(objDTO.getId(), objDTO.getNome(), objDTO.getCpf(), objDTO.getEmail(), objDTO.getSenha());
    }

    public List<Chamado> getChamados() {
        return chamados;
    }

    public void setChamados(List<Chamado> chamados) {
        this.chamados = chamados;
    }

}
