package com.hh99.nearby.chat.controller;

import com.hh99.nearby.chat.dto.request.ChatMessageDto;
import com.hh99.nearby.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MessageController {    
    private final ChatService chatService;

    @MessageMapping("/chat/message") //클라이언트가 Send 할수 있는 경로
    public void send(ChatMessageDto message) {
        chatService.Send(message);
    }

    @GetMapping("/api/chat/list/{challenge_id}") //특정 챌린지에 참여하고 있는 맴버 인원리스트
    public ResponseEntity<?> PeopleList(@PathVariable Long challenge_id) {
       return chatService.peopleList(challenge_id);
    }
}
