package com.hh99.nearby.chat;

import com.hh99.nearby.chat.dto.ChatMessage;
import com.hh99.nearby.entity.Challenge;
import com.hh99.nearby.repository.ChallengeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.*;


@Component
@Slf4j
@RequiredArgsConstructor
public class Scheduler {

    public static final List<LocalDateTime> endTimeList = new ArrayList<>(); //엔드 타임을 저장할 리스트

    private final ChallengeRepository challengeRepository;

    private final SimpMessageSendingOperations sendingOperations;

    @Scheduled(cron = "0 * * * * *") //1분에 한번씩 실행
    public void checkEndTime() {
//        LocalDateTime now =LocalDateTime.now(ZoneId.of("Asia/Seoul")); //현재 서울 시간\
        LocalDateTime now = LocalDateTime.now();
        while (endTimeList.size() != 0 && endTimeList.get(0).isBefore(now)){
            //엔드타임리스트의 사이즈가 0이 아니거나 엔드타임리스트의 첫번째 값이 현재시간보다 과거일때 둘다 트루일때 까지만 실행
            List<Challenge> challenges = challengeRepository.findAllByEndTime(endTimeList.get(0)); //첼린지db에서 endtime이 같은 값을 꺼냄
            for(Challenge challenge : challenges){
                ChatMessage message = ChatMessage.builder() //메세지를 보낼 dto
                        .message("endTime 도착")
                        .type(ChatMessage.MessageType.END)
                        .build();
               sendingOperations.convertAndSend("/sub/chat/room/"+challenge.getId(),message); //룸에다가 콘솔로 값을 띄어줌
            }
            endTimeList.remove(0); //첫번째 인덱스 삭제
        }

    }

}
