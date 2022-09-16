package com.hh99.nearby.chat.service;



import com.hh99.nearby.chat.dto.ChatRoom;
import com.hh99.nearby.entity.Member;
import com.hh99.nearby.entity.MemberChallenge;
import com.hh99.nearby.repository.MemberChallengeRepository;
import com.hh99.nearby.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChatService {

    private final MemberRepository memberRepository;

    private final MemberChallengeRepository memberChallengeRepository;

//    private Map<String, ChatRoom> chatRooms;

//    @PostConstruct
//    //의존관게 주입완료되면 실행되는 코드
//    private void init() {
//        chatRooms = new LinkedHashMap<>();
//    }

    public ResponseEntity<?> createRoom(UserDetails user, Long challege_Id) {

        return ResponseEntity.ok("ㄲ");
    }

    public ResponseEntity<?> checkChallenge(Long challengeId, UserDetails user) { //참여한 첼린지인지 확인
        Optional<Member> member = memberRepository.findByNickname(user.getUsername());
        Optional<MemberChallenge> memberChallenge = memberChallengeRepository.findByMember_IdAndChallenge_Id(member.get().getId(), challengeId);
        if(memberChallenge.isEmpty()){
            return ResponseEntity.badRequest().body(Map.of("msg","참여하지 않은 첼린지입니다."));
        }
        return ResponseEntity.ok().body(Map.of("msg", "참여하신 첼린지 입니다."));
    }

    private Map<Long, ChatRoom> chatRooms;

    @PostConstruct
    //의존관게 주입완료되면 실행되는 코드
    private void init() {
        chatRooms = new LinkedHashMap<>();
    }

    //채팅방 불러오기
    public List<ChatRoom> findAllRoom() {
        //채팅방 최근 생성 순으로 반환
        List<ChatRoom> result = new ArrayList<>(chatRooms.values());
        Collections.reverse(result);

        return result;
    }

    //채팅방 하나 불러오기
    public ChatRoom findById(String roomId) {
        return chatRooms.get(roomId);
    }

    //채팅방 생성
    public ChatRoom createRoom(String name) {
        ChatRoom chatRoom = ChatRoom.create(name);
        chatRooms.put(chatRoom.getRoomId(), chatRoom);
        System.out.println(chatRoom.getRoomId());
        return chatRoom;
    }




}
