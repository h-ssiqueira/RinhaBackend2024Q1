package com.hss.rinhabackend2024q1.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public record HistoricoDTO(Long valor, TipoTransacaoEnum tipo, String descricao,
                           @JsonProperty("realizada_em") LocalDateTime efetuada) {
}
