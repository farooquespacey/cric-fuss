package com.befaruq.repository;

import com.befaruq.model.response.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface MatchRepository extends JpaRepository<Match, Long> {
    @Query("SELECT m FROM Match m WHERE m.date = :date AND m.city = :city AND m.team1 = :team1 AND m.team2 = :team2")
    List<Match> findMatch(@Param("date")LocalDate date, @Param("city")String city, @Param("team1")String team1, @Param("team2")String team2);
}