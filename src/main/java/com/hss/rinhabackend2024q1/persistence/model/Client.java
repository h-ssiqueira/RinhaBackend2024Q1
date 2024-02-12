package com.hss.rinhabackend2024q1.persistence.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "client")
public class Client {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "`limit`")
    private Long limite;

    @Column(name = "balance")
    private Long saldo;

    @OneToMany(mappedBy = "cliente", cascade = ALL)
    private List<Transacao> transacoes;

    public Client() {}

    public Client(Long limite, Long saldo) {
        this.limite = limite;
        this.saldo = saldo;
    }

    public Long getId() {
        return id;
    }

    public Long getLimite() {
        return limite;
    }

    public Long getSaldo() {
        return saldo;
    }

    public void credito(Long dinheiro) {
        this.saldo += dinheiro;
    }

    public void debito(Long dinheiro) {
        this.saldo -= dinheiro;
    }

    public void registraTransacao(Transacao transacao) {
        if(transacoes == null) {
            transacoes = new ArrayList<>();
        }
        transacoes.add(transacao);
    }

    public boolean isChequeEspecialEstourado() {
        return this.saldo < - this.limite;
    }

    public List<Transacao> getTransacoes() {
        return transacoes;
    }
}
