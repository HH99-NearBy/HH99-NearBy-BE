package com.hh99.nearby.repository;


import com.hh99.nearby.entity.Challenge;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface ChallengeRepository extends JpaRepository<Challenge, Long> {
    List<Challenge> findAllByEndTime(LocalDateTime localDateTime);

    @Query(value = "SELECT b FROM Challenge b WHERE b.title LIKE %:keyword%")
    Page<Challenge> findByTitle(String keyword, Pageable pageable);
}