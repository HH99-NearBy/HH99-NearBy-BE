package com.hh99.nearby.chat.controller;

import com.hh99.nearby.chat.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    @PostMapping("/api/chat/challenge/{id}/room")
    public ResponseEntity<?> createChatRoom(@PathVariable Long id, @AuthenticationPrincipal UserDetails user){
        return chatRoomService.createChatRoom(id,user);
    }
}
