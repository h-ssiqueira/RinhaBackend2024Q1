package com.hss.rinhabackend2024q1.controller;

import com.hss.rinhabackend2024q1.dto.ExtratoResponseDTO;
import com.hss.rinhabackend2024q1.dto.TransacaoRequestDTO;
import com.hss.rinhabackend2024q1.dto.TransacaoResponseDTO;
import com.hss.rinhabackend2024q1.exception.ClientNotFoundException;
import com.hss.rinhabackend2024q1.exception.NotEnoughMoneyException;
import com.hss.rinhabackend2024q1.service.RinhaService;
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

    public RinhaController(RinhaService rinhaService) {
        this.rinhaService = rinhaService;
    }

    @PostMapping(path = transacaoPath, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<TransacaoResponseDTO> makeTransaction(@RequestBody TransacaoRequestDTO request, @PathVariable("id") Long id) {
        try{
            if(request == null ||
               request.valor() == null ||
               request.descricao() == null || request.descricao().isEmpty() || request.descricao().length() > 10 ||
               request.tipo() == null || !request.tipo().equals("c") && !request.tipo().equals("d")) {
                throw new IllegalArgumentException();
            }
            checkId(id);
            return ResponseEntity.ok(rinhaService.transfer(request, id));
        } catch (ClientNotFoundException ex) {
            return ResponseEntity.notFound().build();
        } catch (NotEnoughMoneyException ex) {
            return ResponseEntity.unprocessableEntity().build();
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping(path = extratoPath, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ExtratoResponseDTO> retrieveStatement(@PathVariable("id") Long id) {
        try{
            checkId(id);
            return ResponseEntity.ok(rinhaService.statement(id));
        } catch (ClientNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    private void checkId(Long id) throws ClientNotFoundException {
        if(id < 1 || id > 5) {
            throw new ClientNotFoundException();
        }
    }
}
