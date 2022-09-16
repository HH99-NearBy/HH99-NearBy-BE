package com.hh99.nearby.chat.controller;


import com.hh99.nearby.chat.dto.ChatMessage;
import com.hh99.nearby.chat.dto.SessionMemberDto;
import com.hh99.nearby.chat.entity.Chat;
import com.hh99.nearby.chat.repository.ChatRepository;
import com.hh99.nearby.entity.Member;
import com.hh99.nearby.repository.MemberRepository;
import com.hh99.nearby.util.LevelCheck;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class MessageController {

    private final SimpMessageSendingOperations sendingOperations; // @EnableWebSocketMessageBroker를 통해서 등록되는 Bean이다. Broker로 메시지를 전달한다.

    private final ChatRepository chatRepository;

    private final MemberRepository memberRepository;

    private  final LevelCheck levelCheck;

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
            message.setMessage(message.getSender()+"님이 입장하였습니다."); // people list 를 같이 보내줄것
        }else if(ChatMessage.MessageType.QUIT.equals(message.getType())) {
            message.setMessage(message.getSender()+"님이 퇴장하였습니다.");
        }else {
            message.setMessage(message.getMessage()+message.getSendTime());
        }
        sendingOperations.convertAndSend("/sub/chat/room/"+message.getRoomId(),message);
    }

    @GetMapping("/api/chat/list/{challenge_id}") //특정 챌린지에 참여하고 있는 맴버 인원리스트
    public ResponseEntity<?> PeopleList(@PathVariable Long challenge_id) {
        List<Chat> chatList = chatRepository.findAllByChallengeId(challenge_id);//특정 챌린지에 참여하고 있는 채팅수
        List<SessionMemberDto> list = new ArrayList<SessionMemberDto>();//지금까지 특정 챌린지참여 하고 있는 맴버 정보 만들기 위한 리스트 선언
        for(Chat chat: chatList){
            Optional<Member> member = memberRepository.findByNickname(chat.getSender());
            SessionMemberDto sessionMemberDto = SessionMemberDto.builder()
                    .level(levelCheck.levelAndPoint(member.get().getNickname()).get(1)+"LV")
                    .nickname(member.get().getNickname())
                    .build();
            list.add(sessionMemberDto);
        }
        return ResponseEntity.ok().body(Map.of("msg", "조회완료", "data", list));
    }
}
