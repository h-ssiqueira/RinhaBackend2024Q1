package com.hss.rinhabackend2024q1.controller;

import com.hss.rinhabackend2024q1.dto.ExtratoResponseDTO;
import com.hss.rinhabackend2024q1.dto.TransacaoRequestDTO;
import com.hss.rinhabackend2024q1.dto.TransacaoResponseDTO;
import com.hss.rinhabackend2024q1.exception.ClientNotFoundException;
import com.hss.rinhabackend2024q1.exception.NotEnoughMoneyException;
import com.hss.rinhabackend2024q1.service.RinhaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class RinhaController {

    private static final String transacaoPath = "/clientes/{id}/transacoes";
    private static final String extratoPath = "/clientes/{id}/extrato";

    private final RinhaService rinhaService;

    @Autowired
    public RinhaController(RinhaService rinhaService) {
        this.rinhaService = rinhaService;
    }

    @PostMapping(path = transacaoPath, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<TransacaoResponseDTO> makeTransaction(@RequestBody @Valid TransacaoRequestDTO request, @PathVariable("id") Long id) throws NotEnoughMoneyException, ClientNotFoundException {
        return ResponseEntity.ok(rinhaService.transfer(request, id));
    }

    @GetMapping(path = extratoPath, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ExtratoResponseDTO> retrieveStatement(@PathVariable("id") Long id) throws ClientNotFoundException {
        return ResponseEntity.ok(rinhaService.statement(id));
    }
}
