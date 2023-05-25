package com.diogo.helpdesk.domain.dtos;

public class CredenciaisDTO {
    private String email;
    private String senha;

    public CredenciaisDTO() {
    }

    public CredenciaisDTO(String email, String senha) {
        this.email = email;
        this.senha = senha;
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }
    
}
