package com.hss.rinhabackend2024q1.service;

import com.hss.rinhabackend2024q1.dto.ExtratoResponseDTO;
import com.hss.rinhabackend2024q1.dto.SaldoDTO;
import com.hss.rinhabackend2024q1.dto.TipoTransacaoEnum;
import com.hss.rinhabackend2024q1.dto.TransacaoRequestDTO;
import com.hss.rinhabackend2024q1.dto.TransacaoResponseDTO;
import com.hss.rinhabackend2024q1.exception.ClientNotFoundException;
import com.hss.rinhabackend2024q1.exception.NotEnoughMoneyException;
import com.hss.rinhabackend2024q1.persistence.ClienteRepository;
import com.hss.rinhabackend2024q1.persistence.TransacaoRepository;
import com.hss.rinhabackend2024q1.persistence.model.Transacao;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

import static com.hss.rinhabackend2024q1.dto.TipoTransacaoEnum.d;
import static org.springframework.data.domain.Sort.Direction.DESC;

@Service
public class RinhaService {

    private final ClienteRepository clienteRepository;
    private final TransacaoRepository transacaoRepository;

    public RinhaService(ClienteRepository clienteRepository, TransacaoRepository transacaoRepository) {
        this.clienteRepository = clienteRepository;
        this.transacaoRepository = transacaoRepository;
    }

    @Transactional(rollbackFor = NotEnoughMoneyException.class)
    public TransacaoResponseDTO transfer(TransacaoRequestDTO dto, Long id) throws NotEnoughMoneyException {
        var user = clienteRepository.findById(id).get();

        if(dto.tipo().equals(d.getId())) {
            user.debito(dto.valor());
            if(user.isChequeEspecialEstourado()) {
                throw new NotEnoughMoneyException();
            }
        } else {
            user.credito(dto.valor());
        }
        user.registraTransacao(new Transacao(dto.valor(), TipoTransacaoEnum.valueOf(dto.tipo()), dto.descricao(), ZonedDateTime.now(), user));
        var response = clienteRepository.save(user);
        return new TransacaoResponseDTO(response.getLimite(), response.getSaldo());
    }

    @Transactional(readOnly = true)
    public ExtratoResponseDTO statement(Long id) {
        var client = clienteRepository.findById(id).get();
        var page = transacaoRepository.findLastTransactionsById(id, PageRequest.of(0,10,DESC,"efetuada"));
        return new ExtratoResponseDTO(new SaldoDTO(client.getSaldo(), LocalDateTime.now(), client.getLimite()), page.getContent());
    }


}
