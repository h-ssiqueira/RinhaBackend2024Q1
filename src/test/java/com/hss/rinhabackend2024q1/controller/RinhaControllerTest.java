package com.hss.rinhabackend2024q1.controller;

import com.hss.rinhabackend2024q1.exception.ClientNotFoundException;
import com.hss.rinhabackend2024q1.exception.NotEnoughMoneyException;
import com.hss.rinhabackend2024q1.service.RinhaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.stream.Stream;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.APPLICATION_XML_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(properties = "spring.liquibase.enabled=false")
@AutoConfigureMockMvc
class RinhaControllerTest {

    private static final String URI_TRANSACTION = "/clientes/{id}/transacoes";
    private static final String URI_STATEMENT = "/clientes/{id}/extrato";

    private static final String VALID_TRANSACTION_REQUEST = """
                                {
                                    \"valor\": 1000,
                                    \"tipo\" : \"c\",
                                    \"descricao\" : \"descricao\"
                                }""";

    @Autowired
    private MockMvc mvc;

    @MockBean
    private RinhaService service;

    @Test
    void makeTransactionSuccess() throws Exception {
        mvc.perform(post(URI_TRANSACTION,1)
                        .content(VALID_TRANSACTION_REQUEST)
                        .contentType(APPLICATION_JSON_VALUE)
                        .accept(APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

    @ParameterizedTest
    @MethodSource("getBadRequestTransactionParams")
    void makeTransactionBadRequests(String id, String requestBody) throws Exception {
        mvc.perform(post(URI_TRANSACTION,id)
                        .content(requestBody)
                        .contentType(APPLICATION_JSON_VALUE)
                        .accept(APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest());
    }

    @Test
    void makeTransactionMethodNotAllowed() throws Exception {
        mvc.perform(get(URI_TRANSACTION,1)
                        .content(VALID_TRANSACTION_REQUEST)
                        .contentType(APPLICATION_JSON_VALUE)
                        .accept(APPLICATION_JSON_VALUE))
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    void makeTransactionNotAcceptable() throws Exception {
        mvc.perform(post(URI_TRANSACTION,1)
                        .content(VALID_TRANSACTION_REQUEST)
                        .contentType(APPLICATION_JSON_VALUE)
                        .accept(APPLICATION_XML_VALUE))
                .andExpect(status().isNotAcceptable());
    }

    @Test
    void makeTransactionUnsupportedMediaType() throws Exception {
        mvc.perform(post(URI_TRANSACTION,1)
                        .content(VALID_TRANSACTION_REQUEST)
                        .contentType(APPLICATION_XML_VALUE)
                        .accept(APPLICATION_JSON_VALUE))
                .andExpect(status().isUnsupportedMediaType());
    }

    @Test
    void makeTransactionInvalidRequest() throws Exception {
        mvc.perform(post(URI_TRANSACTION,1)
                        .content("{}")
                        .contentType(APPLICATION_JSON_VALUE)
                        .accept(APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest());
    }

    @Test
    void makeTransactionWhenClientNotFound() throws Exception {
        when(service.transfer(any(), anyLong())).thenThrow(new ClientNotFoundException());

        mvc.perform(post(URI_TRANSACTION,6)
                        .content(VALID_TRANSACTION_REQUEST)
                        .contentType(APPLICATION_JSON_VALUE)
                        .accept(APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound());

        verify(service).transfer(any(), anyLong());
    }

    @Test
    void makeTransactionNotEnoughMoney() throws Exception {
        when(service.transfer(any(), anyLong())).thenThrow(new NotEnoughMoneyException());

        mvc.perform(post(URI_TRANSACTION,1)
                        .content(VALID_TRANSACTION_REQUEST)
                        .contentType(APPLICATION_JSON_VALUE)
                        .accept(APPLICATION_JSON_VALUE))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$", equalTo("Client exceeded account limit!")));

        verify(service).transfer(any(), anyLong());
    }

    @Test
    void retrieveStatementSuccess() throws Exception {
        mvc.perform(get(URI_STATEMENT,1)
                        .accept(APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    void retrieveStatementInvalidId() throws Exception {
        mvc.perform(get(URI_STATEMENT,"a")
                        .accept(APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest());
    }

    @Test
    void retrieveStatementNotAcceptable() throws Exception {
        mvc.perform(get(URI_STATEMENT,1)
                        .accept(APPLICATION_XML_VALUE))
                .andExpect(status().isNotAcceptable());
    }

    @Test
    void retrieveStatementMethodNotAllowed() throws Exception {
        mvc.perform(post(URI_STATEMENT,1)
                        .accept(APPLICATION_JSON_VALUE))
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    void retrieveStatementNotFound() throws Exception {
        when(service.statement(anyLong())).thenThrow(new ClientNotFoundException());

        mvc.perform(get(URI_STATEMENT,6)
                        .accept(APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound());

        verify(service).statement(anyLong());
    }

    private static Stream<Arguments> getBadRequestTransactionParams() {
        return Stream.of(
                Arguments.of("a",VALID_TRANSACTION_REQUEST),
                Arguments.of("5","{}")
        );
    }
}