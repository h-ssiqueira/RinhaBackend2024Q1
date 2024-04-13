package com.hss.rinhabackend2024q1.persistence.model;

import com.hss.rinhabackend2024q1.dto.TipoTransacaoEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "transaction")
public class Transacao {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "value")
    private Long valor;

    @Column(name = "type")
    @Enumerated(STRING)
    private TipoTransacaoEnum tipo;

    @Column(name = "description")
    private String descricao;

    @Column(name = "transaction_date")
    private LocalDateTime efetuada;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Cliente cliente;

    public Transacao() {}

    public Transacao(Long valor, TipoTransacaoEnum tipo, String descricao, LocalDateTime efetuada, Cliente cliente) {
        this.valor = valor;
        this.tipo = tipo;
        this.descricao = descricao;
        this.efetuada = efetuada;
        this.cliente = cliente;
    }

    public Transacao setValor(Long valor) {
        this.valor = valor;
        return this;
    }

    public Transacao setTipo(TipoTransacaoEnum tipo) {
        this.tipo = tipo;
        return this;
    }

    public Transacao setDescricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public Transacao setEfetuada(LocalDateTime efetuada) {
        this.efetuada = efetuada;
        return this;
    }

    public Transacao setCliente(Cliente cliente) {
        this.cliente = cliente;
        return this;
    }

    public Long getValor() {
        return valor;
    }

    public TipoTransacaoEnum getTipo() {
        return tipo;
    }

    public String getDescricao() {
        return descricao;
    }

    public LocalDateTime getEfetuada() {
        return efetuada;
    }

    public Cliente getCliente() {
        return cliente;
    }
}
