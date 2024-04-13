package com.hss.rinhabackend2024q1.dto;

public enum TipoTransacaoEnum {

    c("c","credito"), d("d","debito");

    String id;
    String valor;

    TipoTransacaoEnum(String id, String valor) {
        this.id = id;
        this.valor = valor;
    }

    public String getId() {
        return id;
    }

    public String getValor() {
        return valor;
    }
}
