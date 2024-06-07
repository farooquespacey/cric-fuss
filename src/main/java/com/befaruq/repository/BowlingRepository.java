package com.befaruq.repository;

import com.befaruq.model.response.Bowling;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BowlingRepository extends JpaRepository<Bowling, Long> {
    @Query("SELECT b FROM Bowling b WHERE b.inning.inningId = :inningId ORDER BY b.wickets DESC")
    List<Bowling> findTopNBowler(@Param("inningId")Long inningId, Pageable pageable);
}