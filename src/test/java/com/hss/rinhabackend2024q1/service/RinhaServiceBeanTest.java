package com.hss.rinhabackend2024q1.service;

import com.hss.rinhabackend2024q1.dto.TipoTransacaoEnum;
import com.hss.rinhabackend2024q1.dto.TransacaoRequestDTO;
import com.hss.rinhabackend2024q1.exception.ClientNotFoundException;
import com.hss.rinhabackend2024q1.exception.NotEnoughMoneyException;
import com.hss.rinhabackend2024q1.persistence.ClientRepository;
import com.hss.rinhabackend2024q1.persistence.TransactionRepository;
import com.hss.rinhabackend2024q1.persistence.model.Client;
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

import static com.hss.rinhabackend2024q1.dto.TipoTransacaoEnum.c;
import static com.hss.rinhabackend2024q1.dto.TipoTransacaoEnum.d;
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
class RinhaServiceBeanTest {

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private RinhaServiceBean service;

    @AfterEach
    void checkMock() {
        verify(clientRepository).findById(anyLong());
    }

    @ParameterizedTest
    @EnumSource(TipoTransacaoEnum.class)
    void transferSuccess(TipoTransacaoEnum type) throws NotEnoughMoneyException, ClientNotFoundException {
        when(clientRepository.findById(anyLong())).thenReturn(of(buildClient()));
        when(clientRepository.save(any())).thenReturn(buildClient());

        var response = service.transfer(new TransacaoRequestDTO(1L, type,"desc"),1L);

        assertThat(response, notNullValue());
        verify(clientRepository).save(any());
    }

    @ParameterizedTest
    @MethodSource("transactionExceptionTestParams")
    void transferExceptions(Class<Exception> ex, TransacaoRequestDTO request, Long id) {
        when(clientRepository.findById(5L)).thenReturn(of(buildClient()));
        when(clientRepository.findById(6L)).thenReturn(empty());

        assertThrows(ex, () -> service.transfer(request,id));
    }

    @Test
    void statementSuccess() throws ClientNotFoundException {
        when(clientRepository.findById(anyLong())).thenReturn(of(buildClient()));
        when(transactionRepository.findLastTransactionsById(anyLong(), any(Pageable.class))).thenReturn(new PageImpl<>(new ArrayList<>()));

        var response = service.statement(1L);

        assertThat(response, notNullValue());

        verify(transactionRepository).findLastTransactionsById(anyLong(), any(Pageable.class));
    }

    @Test
    void statementNotFound() {
        when(clientRepository.findById(anyLong())).thenReturn(empty());

        assertThrows(ClientNotFoundException.class, () -> service.statement(6L));
    }

    private Client buildClient() {
        return new Client(123L,12300L);
    }

    private static Stream<?> transactionExceptionTestParams() {
        return Stream.of(
                Arguments.of(NotEnoughMoneyException.class, new TransacaoRequestDTO(1000000L, d,"desc"), 5L),
                Arguments.of(ClientNotFoundException.class, new TransacaoRequestDTO(1L, c,"desc"), 6L)
        );
    }
}