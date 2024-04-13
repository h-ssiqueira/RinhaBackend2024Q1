package com.hss.rinhabackend2024q1.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public record SaldoDTO(Long total,
                       @JsonProperty("data_extrato") LocalDateTime dataExtrato,
                       Long limite) {
}
