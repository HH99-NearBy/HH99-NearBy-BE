package com.hh99.nearby.util;

import com.hh99.nearby.entity.Member;
import com.hh99.nearby.repository.MemberChallengeRepository;
import com.hh99.nearby.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Component
@Transactional
public class Score {
    private final MemberRepository memberRepository;
    private final LevelCheck levelCheck;

    @Scheduled(cron = "0 55 4 * * *")
    public void score(){
        List<Member> member = memberRepository.findAll();
        for (int i = 0; i<member.size(); i++){
            long points = 0;
            member.get(i).update(points+levelCheck.levelAndPoint(member.get(i).getNickname()).get(0));

        }
    }
}
