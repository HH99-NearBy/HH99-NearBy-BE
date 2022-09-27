package com.hh99.nearby.repository;


import com.hh99.nearby.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat, Long> {
    Chat findBysessionId(String seesionId);

    List<Chat> findAllByChallengeId(Long challengeId);
}
