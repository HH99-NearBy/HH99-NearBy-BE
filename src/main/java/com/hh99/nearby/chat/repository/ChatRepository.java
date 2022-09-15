package com.hh99.nearby.chat.repository;

import com.hh99.nearby.chat.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<Chat, Long> {
    Chat findBysessionId(String seesionId);
}
