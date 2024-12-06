package com.kodat.of.chargingstationservice.repository;

import com.kodat.of.chargingstationservice.entity.Connections;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConnectionsRepository extends JpaRepository<Connections, Long> {
}
