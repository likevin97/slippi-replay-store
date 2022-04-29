package com.slippi.replays.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import com.slippi.replays.entities.ReplayEntityProjection;
import com.slippi.replays.entities.ReplayEntity;

@Repository
public interface ReplayRepository extends JpaRepository<ReplayEntity, Long> {

    List<ReplayEntity> findByConnectCode(String connectCode);

    List<ReplayEntity> findAll();

    @Transactional
    Long deleteByCreatedAtBefore(OffsetDateTime offsetDateTime);

    public List<ReplayEntityProjection> findAllByConnectCode(String connectCode, Pageable pageable);

    @Modifying
    @Transactional
    @Query(value="DELETE FROM replays r where (r.connect_code = ?1) and (r.id NOT in ?2)", nativeQuery=true)
    void deleteByExcludedId(String connectCode, List<UUID> idList);
}
