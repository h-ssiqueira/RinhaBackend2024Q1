package com.hss.rinhabackend2024q1.exception;

public class ClientNotFoundException extends Exception {

    public ClientNotFoundException() {
        super("Client not found!");
    }
}
