package com.hh99.nearby.chat.config;

import com.hh99.nearby.chat.entity.Chat;
import com.hh99.nearby.chat.repository.ChatRepository;
import com.hh99.nearby.entity.Member;
import com.hh99.nearby.entity.MemberChallenge;
import com.hh99.nearby.repository.MemberChallengeRepository;
import com.hh99.nearby.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class StompHandler implements ChannelInterceptor {

    private final ChatRepository chatRepository;

    private final MemberChallengeRepository memberChallengeRepository;

    private final MemberRepository memberRepository;


    @Override
    @Transactional
    public Message preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        StompCommand command = accessor.getCommand();
        //입장할떄 보내준걸로
        if (command.compareTo(StompCommand.DISCONNECT) == 0) {
            String seesionId = (String) message.getHeaders().get("simpSessionId"); //세션아이디를 초기화
            Chat chat = chatRepository.findBysessionId(seesionId); //세션아이디로 데이터 찾음
            Optional<Member> member = memberRepository.findByNickname(chat.getSender()); //chatDB에 저장된 sender(닉네임)으로 데이터 찾기
            Optional<MemberChallenge> memberChallenge = memberChallengeRepository.findByMember_IdAndChallenge_Id(member.get().getId(),chat.getChallengeId()); //MemberId로 참여한 첼린지 데이터 찾기
            //위에 수정 |  맴버 아이디 + 프론트에서 보내준 challengeId를 받아서 찾은후 밑에 계산 실시
            Long endTime = System.currentTimeMillis(); // 사용자 연결해제 시간
            Long realTime = (endTime - chat.getEntryTime()) / (1000*60); // 연결해제 시간 분단위로
            if (memberChallenge.get().getRealTime() > 0) { // 리얼타임이 0보다 크다면
                Long realtime = memberChallenge.get().getRealTime(); // 데이터베이스 안에 리얼타임을 가져와서
                memberChallenge.get().update(realtime + realTime); //현재 계산된 리얼타임과 db에 저장된 realtime을 더해서 업데이트
            } else {
                memberChallenge.get().update(realTime); // 아니면 리얼타임 업데이트
            }
            System.out.println("총 리얼타임 : " + memberChallenge.get().getRealTime());
            chatRepository.delete(chat); // 다 업데이트하면 session아이디 삭제
        }
        return message;
    }
}
