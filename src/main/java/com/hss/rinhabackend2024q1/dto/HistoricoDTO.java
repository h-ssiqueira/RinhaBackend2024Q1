package com.hss.rinhabackend2024q1.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

public record HistoricoDTO(Long valor, TipoTransacaoEnum tipo, String descricao,
                           @JsonProperty("realizada_em") ZonedDateTime efetuada) {
}
