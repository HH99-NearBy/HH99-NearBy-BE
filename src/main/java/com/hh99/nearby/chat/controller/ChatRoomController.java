package com.hh99.nearby.chat.controller;


import com.hh99.nearby.chat.Dto.ChatRoom;
import com.hh99.nearby.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatRoomController {

    private final ChatService chatService;

    @PostMapping("/room/check/{challenge_Id}") //참여한 첼린지인지 확인
    public ResponseEntity<?> checkChallenge(@PathVariable Long challengeId, @AuthenticationPrincipal UserDetails user){
        return chatService.checkChallenge(challengeId,user);
    }




    // 채팅 리스트 화면
    @GetMapping("/room")
    public String rooms(Model model) {
        return "/chat/room";
    }

    // 모든 채팅방 목록 반환
    @GetMapping("/rooms")
    @ResponseBody
    public List<ChatRoom> room() {
        return chatService.findAllRoom();
    }

    // 채팅방 생성
    @PostMapping("/room") //채팅방 생성
    @ResponseBody
    public ChatRoom createRoom(@RequestParam String name) {

        System.out.println("방생성");
        return chatService.createRoom(name);
    }

    // 채팅방 입장 화면
    @GetMapping("/room/enter/{roomId}") //채팅방 입장
    public String roomDetail(Model model, @PathVariable String roomId) {
        model.addAttribute("roomId", roomId);
        System.out.println("방입장");
        return "/chat/roomdetail";
    }

    // 특정 채팅방 조회
    @GetMapping("/room/{roomId}")
    @ResponseBody
    public ChatRoom roomInfo(@PathVariable String roomId) {
        return chatService.findById(roomId);
    }



}
