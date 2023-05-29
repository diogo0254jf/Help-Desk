package com.diogo.helpdesk.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.diogo.helpdesk.domain.Pessoa;
import com.diogo.helpdesk.repositories.PessoaRepository;
import com.diogo.helpdesk.security.UserSS;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private PessoaRepository repo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Pessoa> obj = repo.findByEmail(email);
        if(obj.isPresent()) {
            return new UserSS(obj.get().getId(), obj.get().getEmail(), obj.get().getSenha(), obj.get().getPerfis());
        }
        throw new UsernameNotFoundException(email);
    }

    
}
