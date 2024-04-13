package com.hss.rinhabackend2024q1.controller;

import com.hss.rinhabackend2024q1.exception.ClientNotFoundException;
import com.hss.rinhabackend2024q1.exception.NotEnoughMoneyException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

@ControllerAdvice
public class ControllerAdviser {

    @ExceptionHandler(ClientNotFoundException.class)
    public ResponseEntity<String> handleNotFoundClient(ClientNotFoundException e) {
        return ResponseEntity.status(NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(NotEnoughMoneyException.class)
    public ResponseEntity<String> handleInsufficientMoney(NotEnoughMoneyException e) {
        return ResponseEntity.status(UNPROCESSABLE_ENTITY).body(e.getMessage());
    }
}
