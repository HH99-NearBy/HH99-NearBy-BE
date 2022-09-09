package com.hh99.nearby.chat.service;

import com.hh99.nearby.chat.entity.ChatRoom;
import com.hh99.nearby.chat.repository.ChatRoomRepository;
import com.hh99.nearby.entity.Challenge;
import com.hh99.nearby.repository.ChallengeRepository;
import com.hh99.nearby.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

//    @Resource(name = "redisTemplate")
//    private HashOperations<String, String, String> hashOpsEnterInfo;

    private final MemberRepository memberRepository;
    private final ChallengeRepository challengeRepository;
    private final ChatRoomRepository chatRoomRepository;

    public ResponseEntity<?> createChatRoom(@PathVariable Long id, @AuthenticationPrincipal UserDetails user){

        Optional<Challenge> challenge = challengeRepository.findById(id);

        ChatRoom chatRoom = ChatRoom.builder()
                .id(id)
                .chatRoomName(challenge.get().getTitle())
                .challengeId(id)
                .build();
        chatRoomRepository.save(chatRoom);
        return ResponseEntity.ok().body(Map.of("msg","채팅방 생성 완료"));
    }
}
