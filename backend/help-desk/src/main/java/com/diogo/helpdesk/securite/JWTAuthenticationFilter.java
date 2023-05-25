package com.diogo.helpdesk.securite;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.diogo.helpdesk.domain.dtos.CredenciaisDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private AuthenticationManager authenticationManager;
    private JWTUtil jwtUtil;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
        super();
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
            throws AuthenticationException {
        try {
            CredenciaisDTO creds = new ObjectMapper().readValue(req.getInputStream(), CredenciaisDTO.class);
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(creds.getEmail(),
                    creds.getSenha(), new ArrayList<>());
            Authentication auth = authenticationManager.authenticate(authToken);
            return auth;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void successfulAuthentication(HttpServletRequest req, HttpServletResponse res,
            javax.servlet.FilterChain chain,
            Authentication auth) throws IOException, javax.servlet.ServletException {
        String username = ((UserSS) auth.getPrincipal()).getUsername();
        String token = jwtUtil.generateToken(username);
        res.setHeader("access-control-expose-headers", "Authorization");
        res.addHeader("Authorization", "Bearer " + token);
    }

    public void unsuccessfulAuthentication(HttpServletRequest req, HttpServletResponse res,
            javax.servlet.FilterChain chain,
            Authentication auth) throws IOException, javax.servlet.ServletException {

        res.setStatus(401);
        res.setContentType("application/json");
        res.getWriter().append(json());
    }

    private CharSequence json() {
        long date = new Date().getTime();
        return "{" +
                "\timestamp\" : " + date + "," +
                "\t\"status\" : 401," +
                "\t\"error\" : \"Não autorizado\"," +
                "\t\"message\" : \"Email ou senha inválidos\"," +
                "\t\"path\" : \"/login\"}";
    }

}
