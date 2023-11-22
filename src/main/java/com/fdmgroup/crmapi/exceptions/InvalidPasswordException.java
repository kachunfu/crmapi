package com.fdmgroup.crmapi.exceptions;

public class InvalidPasswordException extends RuntimeException {

    public InvalidPasswordException() {
        super("Password provided does not match the users password");
    }
}