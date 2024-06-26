package com.hss.rinhabackend2024q1.service;

import com.hss.rinhabackend2024q1.dto.ExtratoResponseDTO;
import com.hss.rinhabackend2024q1.dto.SaldoDTO;
import com.hss.rinhabackend2024q1.dto.TransacaoRequestDTO;
import com.hss.rinhabackend2024q1.dto.TransacaoResponseDTO;
import com.hss.rinhabackend2024q1.exception.ClientNotFoundException;
import com.hss.rinhabackend2024q1.exception.NotEnoughMoneyException;
import com.hss.rinhabackend2024q1.persistence.ClienteRepository;
import com.hss.rinhabackend2024q1.persistence.TransacaoRepository;
import com.hss.rinhabackend2024q1.persistence.model.Transacao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static com.hss.rinhabackend2024q1.dto.TipoTransacaoEnum.d;
import static org.springframework.data.domain.Sort.Direction.DESC;

@Service
public class RinhaServiceBean implements RinhaService {

    private final ClienteRepository clienteRepository;
    private final TransacaoRepository transacaoRepository;

    @Autowired
    public RinhaServiceBean(ClienteRepository clienteRepository, TransacaoRepository transacaoRepository) {
        this.clienteRepository = clienteRepository;
        this.transacaoRepository = transacaoRepository;
    }

    @Override
    @Transactional
    public TransacaoResponseDTO transfer(TransacaoRequestDTO dto, Long id) throws NotEnoughMoneyException, ClientNotFoundException {
        var user = clienteRepository.findById(id).orElseThrow(ClientNotFoundException::new);

        if(dto.tipo().equals(d)) {
            if(user.isChequeEspecialEstourado(dto.valor())) {
                throw new NotEnoughMoneyException();
            } else {
                user.debito(dto.valor());
            }
        } else {
            user.credito(dto.valor());
        }
        transacaoRepository.save(new Transacao(dto.valor(), dto.tipo(), dto.descricao(), LocalDateTime.now(), user));
        clienteRepository.save(user);
        return new TransacaoResponseDTO(user.getLimite(), user.getSaldo());
    }

    @Override
    @Transactional(readOnly = true)
    public ExtratoResponseDTO statement(Long id) throws ClientNotFoundException {
        var client = clienteRepository.findById(id).orElseThrow(ClientNotFoundException::new);
        var page = transacaoRepository.findLastTransactionsById(id, PageRequest.of(0,10,DESC,"efetuada"));
        return new ExtratoResponseDTO(new SaldoDTO(client.getSaldo(), LocalDateTime.now(), client.getLimite()), page.getContent());
    }


}
