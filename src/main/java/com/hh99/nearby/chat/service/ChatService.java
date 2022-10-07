package com.hh99.nearby.chat.service;

import com.hh99.nearby.chat.dto.request.ChatMessageDto;
import com.hh99.nearby.chat.dto.response.SessionMemberDto;
import com.hh99.nearby.entity.Chat;
import com.hh99.nearby.entity.Member;
import com.hh99.nearby.repository.ChatRepository;
import com.hh99.nearby.repository.MemberRepository;
import com.hh99.nearby.util.LevelCheck;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final SimpMessageSendingOperations sendingOperations; // @EnableWebSocketMessageBroker를 통해서 등록되는 Bean이다. Broker로 메시지를 전달한다.

    private final ChatRepository chatRepository;

    private final MemberRepository memberRepository;

    private  final LevelCheck levelCheck;

    public void Send(ChatMessageDto message) {
        LocalTime now = LocalTime.now(); // 현재 채팅시간
        message.setSendTime(now.format(DateTimeFormatter.ofPattern("a HH시 mm분"))); //채팅 모양 변환
        if (ChatMessageDto.MessageType.ENTER.equals(message.getType())) { //입장시
            String level = "LV."+levelCheck.levelAndPoint(message.getSender()).get(1); //Lv 계산
            Long challengeid = Long.valueOf(message.getRoomId()); //프론트에서 첼린지 번호를 넘겨줌
            chatRepository.save(Chat.builder() //chat 엔티티에 저장
                    .sender(message.getSender()) //글쓴이
                    .sessionId(message.getSessionId()) //세션아이디
                    .entryTime(System.currentTimeMillis()) // 시작시간
                    .challengeId(challengeid) // 첼린지아이디
                    .build());
            message.setLevel(level); // Lv 저장
            message.setMessage(message.getSender()+"님이 입장하였습니다."); // people list 를 같이 보내줄것
        }else if(ChatMessageDto.MessageType.QUIT.equals(message.getType())) {
            message.setMessage(message.getSender()+"님이 퇴장하였습니다.");
        }
        sendingOperations.convertAndSend("/sub/chat/room/"+message.getRoomId(),message);
    }

    public ResponseEntity<?> peopleList (Long challenge_id){
        List<Chat> chatList = chatRepository.findAllByChallengeId(challenge_id);//특정 챌린지에 참여하고 있는 채팅수
        List<SessionMemberDto> list = new ArrayList<SessionMemberDto>();//지금까지 특정 챌린지참여 하고 있는 맴버 정보 만들기 위한 리스트 선언
        for(Chat chat: chatList){
            Optional<Member> member = memberRepository.findByNickname(chat.getSender());
            SessionMemberDto sessionMemberDto = SessionMemberDto.builder()
                    .level("LV."+levelCheck.levelAndPoint(member.get().getNickname()).get(1))
                    .nickname(member.get().getNickname())
                    .entryTime(chat.getEntryTime())
                    .build();
            list.add(sessionMemberDto);
        }
        return ResponseEntity.ok().body(Map.of("msg", "조회완료", "data", list));
    }
}
