package com.kodat.of.chargingstationservice.repository;

import com.kodat.of.chargingstationservice.entity.Station;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StationRepository extends JpaRepository<Station, Long> {

}
