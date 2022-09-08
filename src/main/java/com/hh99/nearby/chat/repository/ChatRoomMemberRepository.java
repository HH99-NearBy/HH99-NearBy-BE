package com.hh99.nearby.chat.repository;

import com.hh99.nearby.chat.entity.ChatRoomMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomMemberRepository extends JpaRepository<ChatRoomMember,Long> {
}
