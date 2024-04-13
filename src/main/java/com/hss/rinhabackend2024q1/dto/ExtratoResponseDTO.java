package com.hss.rinhabackend2024q1.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record ExtratoResponseDTO(SaldoDTO saldo,
                                 @JsonProperty("ultimas_transacoes") List<HistoricoDTO> transacoes) {
    
}
