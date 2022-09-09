package com.hh99.nearby.chat.controller;

import com.hh99.nearby.chat.dto.ChatMessageRequestDto;
import com.hh99.nearby.chat.service.ChatMessageService;
import com.hh99.nearby.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChatMessageController {

    private final ChatMessageService chatMessageService;
    private final MemberRepository memberRepository;

//    @MessageMapping("/api/chat/message")
//    public ResponseEntity<?> message(@RequestBody ChatMessageRequestDto chatMessageRequestDto,)
}
