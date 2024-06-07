package com.befaruq.repository;

import com.befaruq.model.response.Inning;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InningRepository extends JpaRepository<Inning, Long> {
}