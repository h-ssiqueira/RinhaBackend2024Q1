package com.hss.rinhabackend2024q1.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record TransacaoRequestDTO(@NotNull(message = "Valor cannot be null") Long valor,
                                  @NotNull(message = "Tipo cannot be null") TipoTransacaoEnum tipo,
                                  @NotEmpty(message = "Valor cannot be empty") @Size(min = 1, max = 10, message = "Descricao must be between 1 and 10 characters") String descricao) {
    
}
