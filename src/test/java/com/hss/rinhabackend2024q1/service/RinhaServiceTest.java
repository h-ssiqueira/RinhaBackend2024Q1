package com.hss.rinhabackend2024q1.service;

import com.hss.rinhabackend2024q1.dto.TipoTransacaoEnum;
import com.hss.rinhabackend2024q1.dto.TransacaoRequestDTO;
import com.hss.rinhabackend2024q1.exception.ClientNotFoundException;
import com.hss.rinhabackend2024q1.exception.NotEnoughMoneyException;
import com.hss.rinhabackend2024q1.persistence.ClienteRepository;
import com.hss.rinhabackend2024q1.persistence.TransacaoRepository;
import com.hss.rinhabackend2024q1.persistence.model.Cliente;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.stream.Stream;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class RinhaServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private TransacaoRepository transacaoRepository;

    @InjectMocks
    private RinhaService service;

    @AfterEach
    void checkMock() {
        verify(clienteRepository).findById(anyLong());
    }

    @ParameterizedTest
    @EnumSource(TipoTransacaoEnum.class)
    void transferSuccess(TipoTransacaoEnum type) throws NotEnoughMoneyException {
        when(clienteRepository.findById(anyLong())).thenReturn(of(buildClient()));
        when(clienteRepository.save(any())).thenReturn(buildClient());

        var response = service.transfer(new TransacaoRequestDTO(1L, type.getId(),"desc"),1L);

        assertThat(response, notNullValue());
        verify(clienteRepository).save(any());
    }

    @ParameterizedTest
    @MethodSource("transactionExceptionTestParams")
    void transferExceptions(Class<Exception> ex, TransacaoRequestDTO request, Long id) {
        when(clienteRepository.findById(5L)).thenReturn(of(buildClient()));
        when(clienteRepository.findById(6L)).thenReturn(empty());

        assertThrows(ex, () -> service.transfer(request,id));
    }

    @Test
    void statementSuccess() {
        when(clienteRepository.findById(anyLong())).thenReturn(of(buildClient()));
        when(transacaoRepository.findLastTransactionsById(anyLong(), any(Pageable.class))).thenReturn(new PageImpl<>(new ArrayList<>()));

        var response = service.statement(1L);

        assertThat(response, notNullValue());

        verify(transacaoRepository).findLastTransactionsById(anyLong(), any(Pageable.class));
    }

    @Test
    void statementNotFound() {
        when(clienteRepository.findById(anyLong())).thenReturn(empty());

        assertThrows(ClientNotFoundException.class, () -> service.statement(6L));
    }

    private Cliente buildClient() {
        return new Cliente(123L,12300L);
    }

    private static Stream<?> transactionExceptionTestParams() {
        return Stream.of(
                Arguments.of(NotEnoughMoneyException.class, new TransacaoRequestDTO(1000000L, "d","desc"), 5L),
                Arguments.of(ClientNotFoundException.class, new TransacaoRequestDTO(1L, "c","desc"), 6L)
        );
    }
}