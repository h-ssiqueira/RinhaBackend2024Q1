package com.hss.rinhabackend2024q1.persistence;

import com.hss.rinhabackend2024q1.dto.HistoricoDTO;
import com.hss.rinhabackend2024q1.persistence.model.Transacao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TransacaoRepository extends JpaRepository<Transacao, Long> {

    @Query("""
            SELECT new com.hss.rinhabackend2024q1.dto.HistoricoDTO(t.valor, t.tipo, t.descricao, t.efetuada) 
            FROM Transacao t
            WHERE t.cliente.id = :id
            """)
    Page<HistoricoDTO> findLastTransactionsById(@Param("id") Long id, Pageable pageable);
}
