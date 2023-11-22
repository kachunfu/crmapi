package com.fdmgroup.crmapi.exceptions;

public class ManagerNotFoundException extends RuntimeException {

    public ManagerNotFoundException(String id) {
        super("Could not find manager: " + id);
    }
}