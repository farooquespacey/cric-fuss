package com.befaruq.repository;

import com.befaruq.model.response.Batting;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BattingRepository extends JpaRepository<Batting, Long> {
    @Query("SELECT b FROM Batting b WHERE b.inning.inningId = :inningId ORDER BY b.runs DESC")
    List<Batting> findTopNScorer(@Param("inningId")Long inningId, Pageable pageable);
}