package com.hss.rinhabackend2024q1.service;

import com.hss.rinhabackend2024q1.dto.ExtratoResponseDTO;
import com.hss.rinhabackend2024q1.dto.SaldoDTO;
import com.hss.rinhabackend2024q1.dto.TransacaoRequestDTO;
import com.hss.rinhabackend2024q1.dto.TransacaoResponseDTO;
import com.hss.rinhabackend2024q1.exception.ClientNotFoundException;
import com.hss.rinhabackend2024q1.exception.NotEnoughMoneyException;
import com.hss.rinhabackend2024q1.persistence.ClientRepository;
import com.hss.rinhabackend2024q1.persistence.TransactionRepository;
import com.hss.rinhabackend2024q1.persistence.model.Transacao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static com.hss.rinhabackend2024q1.dto.TipoTransacaoEnum.d;
import static org.springframework.data.domain.Sort.Direction.DESC;
import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

@Service
public class RinhaServiceBean implements RinhaService {

    private final ClientRepository clientRepository;
    private final TransactionRepository transactionRepository;

    @Autowired
    public RinhaServiceBean(ClientRepository clientRepository, TransactionRepository transactionRepository) {
        this.clientRepository = clientRepository;
        this.transactionRepository = transactionRepository;
    }

    @Override
    @Transactional(transactionManager = "transactionManagerRinha", rollbackFor = NotEnoughMoneyException.class, propagation = REQUIRES_NEW)
    public TransacaoResponseDTO transfer(TransacaoRequestDTO dto, Long id) throws NotEnoughMoneyException, ClientNotFoundException {
        var user = clientRepository.findById(id).orElseThrow(ClientNotFoundException::new);

        if(dto.tipo().equals(d)) {
            user.debito(dto.valor());
            if(user.isChequeEspecialEstourado()) {
                throw new NotEnoughMoneyException();
            }
        } else {
            user.credito(dto.valor());
        }
        user.registraTransacao(new Transacao(dto.valor(), dto.tipo(), dto.descricao(), LocalDateTime.now(), user));
        var response = clientRepository.save(user);
        return new TransacaoResponseDTO(response.getLimite(), response.getSaldo());
    }

    @Override
    @Transactional(readOnly = true)
    public ExtratoResponseDTO statement(Long id) throws ClientNotFoundException {
        var client = clientRepository.findById(id).orElseThrow(ClientNotFoundException::new);
        var page = transactionRepository.findLastTransactionsById(id, PageRequest.of(0,10,DESC,"efetuada"));
        return new ExtratoResponseDTO(new SaldoDTO(client.getSaldo(), LocalDateTime.now(), client.getLimite()), page.getContent());
    }


}