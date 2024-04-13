package com.hss.rinhabackend2024q1.exception;

public class NotEnoughMoneyException extends Exception {

    public NotEnoughMoneyException() {
        super("Client exceeded account limit!");
    }
}
