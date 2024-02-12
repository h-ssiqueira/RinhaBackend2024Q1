package com.hss.rinhabackend2024q1.persistence;

import com.hss.rinhabackend2024q1.persistence.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
}
