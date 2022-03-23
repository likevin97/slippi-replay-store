package com.slippi.replays.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;

import com.slippi.replays.entities.ReplayEntity;

@Repository
public interface ReplayRepository extends JpaRepository<ReplayEntity, Long> {

    List<ReplayEntity> findByConnectCode(String connectCode);

    List<ReplayEntity> findAll();

    @Transactional
    Long deleteByCreatedAtBefore(OffsetDateTime offsetDateTime);
}
