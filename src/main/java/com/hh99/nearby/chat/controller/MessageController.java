package com.hh99.nearby.chat.controller;



import com.hh99.nearby.chat.dto.ChatMessage;
import com.hh99.nearby.chat.entity.Chat;
import com.hh99.nearby.chat.repository.ChatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequiredArgsConstructor
public class MessageController {

    private final SimpMessageSendingOperations sendingOperations; // @EnableWebSocketMessageBroker를 통해서 등록되는 Bean이다. Broker로 메시지를 전달한다.

    private final ChatRepository chatRepository;


    @MessageMapping("/chat/message") //클라이언트가 Send 할수 있는 경로
    public void enter(ChatMessage message) {
        System.out.println("채팅 시작!");
        LocalTime now = LocalTime.now(); // 현재 채팅시간
        message.setSendTime(now.format(DateTimeFormatter.ofPattern("a HH시 mm분"))); //채팅 모양 변환
        if (ChatMessage.MessageType.ENTER.equals(message.getType())) { //입장시
            Long challengeid = Long.valueOf(message.getRoomId()); //프론트에서 첼린지 번호를 넘겨줌
            chatRepository.save(Chat.builder() //chat 엔티티에 저장
                            .sender(message.getSender()) //글쓴이
                            .sessionId(message.getSessionId()) //세션아이디
                            .entryTime(System.currentTimeMillis()) // 시작시간
                            .challengeId(challengeid) // 첼린지아이디
                    .build());
            message.setMessage(message.getSender()+"님이 입장하였습니다."+message.getSendTime()); // people list 를 같이 보내줄것
        }else if(ChatMessage.MessageType.QUIT.equals(message.getType())) {
            message.setMessage(message.getSender()+"님이 퇴장하였습니다."+message.getSendTime());
        }else {
            message.setMessage(message.getMessage()+message.getSendTime());
        }
        sendingOperations.convertAndSend("/sub/chat/room/"+message.getRoomId(),message);
    }
}
