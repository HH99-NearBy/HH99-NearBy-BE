package com.hh99.nearby.chat.service;

import com.hh99.nearby.chat.dto.ChatMessageResponseDto;
import com.hh99.nearby.chat.entity.ChatMessage;
import com.hh99.nearby.chat.repository.ChatMessageRepository;
import com.hh99.nearby.entity.Member;
import com.hh99.nearby.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;
    private final MemberRepository memberRepository;

    // destination 정보에서 roomId 추출
    public String getRoomId(String destination) {
        int lastIndex = destination.lastIndexOf('/');
        if (lastIndex != -1)
            return destination.substring(lastIndex + 1);
        else
            return "";
    }

    public ResponseEntity<?> sendChatMessage(ChatMessage chatMessage) {
        Optional<Member> member = memberRepository.findByNickname(chatMessage.getNickname());

        if (ChatMessage.MessageType.ENTER.equals(chatMessage.getType())) {
            chatMessage.setMessage(chatMessage.getMessage());
            chatMessage.setSender(member.get().getNickname() + "님이 입장하셨습니다.");
            ChatMessageResponseDto chatMessageResponseDto = new ChatMessageResponseDto(chatMessage);

        }
    }
}
