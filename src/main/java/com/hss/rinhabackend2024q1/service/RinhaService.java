package com.hss.rinhabackend2024q1.service;

import com.hss.rinhabackend2024q1.dto.ExtratoResponseDTO;
import com.hss.rinhabackend2024q1.dto.TransacaoRequestDTO;
import com.hss.rinhabackend2024q1.dto.TransacaoResponseDTO;
import com.hss.rinhabackend2024q1.exception.ClientNotFoundException;
import com.hss.rinhabackend2024q1.exception.NotEnoughMoneyException;

public interface RinhaService {
    
    TransacaoResponseDTO transfer(TransacaoRequestDTO dto, Long id) throws NotEnoughMoneyException, ClientNotFoundException;

    ExtratoResponseDTO statement(Long id) throws ClientNotFoundException;
}
