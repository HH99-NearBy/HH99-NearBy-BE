package com.hh99.nearby.util;

import com.hh99.nearby.entity.Member;
import com.hh99.nearby.entity.MemberChallenge;
import com.hh99.nearby.repository.MemberChallengeRepository;
import com.hh99.nearby.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Transactional
public class Graph {

    private final MemberRepository memberRepository;
    private final MemberChallengeRepository memberChallengeRepository;

    @Scheduled(cron = "0 0 * * * *")
    public void SevenDaysGraph() {
//        System.out.println("==========");
//        System.out.println("스케줄러 실행 중");
//        System.out.println("==========");


        List<Member> memberList = memberRepository.findAll();
        if (memberList.size() != 0) {

            for (int j = 0; j < memberList.size(); j++) {

                List<Long> sevendaysGraph = new ArrayList<>();
                long oneTime = 0;
                long twoTime = 0;
                long threeTime = 0;
                long fourTime = 0;
                long fiveTime = 0;
                long sixTime = 0;
                long sevenTime = 0;

                List<MemberChallenge> memberChallengeSevenDay = memberChallengeRepository.sevenday();
                for (int i = 0; i < memberChallengeSevenDay.size(); i++) {
                    if (memberList.get(j).getNickname().equals(memberChallengeSevenDay.get(i).getMember().getNickname())) {
                        sevenTime += memberChallengeSevenDay.get(i).getRealTime();
                    }
                }

                List<MemberChallenge> memberChallengeSixDay = memberChallengeRepository.sixday();
                for (int i = 0; i < memberChallengeSixDay.size(); i++) {
                    if (memberList.get(j).getNickname().equals(memberChallengeSixDay.get(i).getMember().getNickname())) {
                        sixTime += memberChallengeSixDay.get(i).getRealTime();
                    }
                }

                List<MemberChallenge> memberChallengeFiveDay = memberChallengeRepository.fiveday();
                for (int i = 0; i < memberChallengeFiveDay.size(); i++) {
                    if (memberList.get(j).getNickname().equals(memberChallengeFiveDay.get(i).getMember().getNickname())) {
                        fiveTime += memberChallengeFiveDay.get(i).getRealTime();
                    }
                }

                List<MemberChallenge> memberChallengeFourDay = memberChallengeRepository.fourday();
                for (int i = 0; i < memberChallengeFourDay.size(); i++) {
                    if (memberList.get(j).getNickname().equals(memberChallengeFourDay.get(i).getMember().getNickname())) {
                        fourTime += memberChallengeFourDay.get(i).getRealTime();
                    }
                }

                List<MemberChallenge> memberChallengeThreeDay = memberChallengeRepository.threeday();
                for (int i = 0; i < memberChallengeThreeDay.size(); i++) {
                    if (memberList.get(j).getNickname().equals(memberChallengeThreeDay.get(i).getMember().getNickname())) {
                        threeTime += memberChallengeThreeDay.get(i).getRealTime();
                    }
                }

                List<MemberChallenge> memberChallengeTwoDay = memberChallengeRepository.twoday();
                for (int i = 0; i < memberChallengeTwoDay.size(); i++) {
                    if (memberList.get(j).getNickname().equals(memberChallengeTwoDay.get(i).getMember().getNickname())) {
                        twoTime += memberChallengeTwoDay.get(i).getRealTime();
                    }
                }
                //오늘
                List<MemberChallenge> memberChallengeOneDay = memberChallengeRepository.oneday();
                for (int i = 0; i < memberChallengeOneDay.size(); i++) {
                    if (memberList.get(j).getNickname().equals(memberChallengeOneDay.get(i).getMember().getNickname())) {
                        oneTime += memberChallengeOneDay.get(i).getRealTime();
                    }
                }

                sevendaysGraph.add(sevenTime);
                sevendaysGraph.add(sixTime);
                sevendaysGraph.add(fiveTime);
                sevendaysGraph.add(fourTime);
                sevendaysGraph.add(threeTime);
                sevendaysGraph.add(twoTime);
                sevendaysGraph.add(oneTime);

                memberList.get(j).update(sevendaysGraph);
            }

        }
    }
}
