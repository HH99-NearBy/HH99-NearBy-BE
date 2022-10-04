package com.hh99.nearby.util;

import com.hh99.nearby.entity.Member;
import com.hh99.nearby.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
@Transactional
public class Rank {

    private final MemberRepository memberRepository;
    private final LevelCheck levelCheck;
    private final Graph graph;

    @Scheduled(cron = "0 0 * * * *")
    public void scheduleRank(){
        List<Member> allByOrderByPointsDesc = memberRepository.rank();
        if (allByOrderByPointsDesc != null) {
            for (int i = 0; i < allByOrderByPointsDesc.size(); i++) {
                long myRank = (long) i + 1;
                String nickname = allByOrderByPointsDesc.get(i).getNickname();
                memberRepository.updateRank(myRank, nickname);
            }
        }
    }
}
