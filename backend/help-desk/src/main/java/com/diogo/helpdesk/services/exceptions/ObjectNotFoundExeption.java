package com.diogo.helpdesk.services.exceptions;

public class ObjectNotFoundExeption extends RuntimeException {
    private static final long serialVersionUID = 1L;
    
    public ObjectNotFoundExeption(String msg) {
        super(msg);
    }
}