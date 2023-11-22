package com.fdmgroup.crmapi.exceptions;

public class AgentNotFoundException extends RuntimeException {

    public AgentNotFoundException(String id) {
        super("Could not find agent: " + id);
    }
}