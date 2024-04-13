package com.hss.rinhabackend2024q1.persistence;

import com.hss.rinhabackend2024q1.persistence.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
