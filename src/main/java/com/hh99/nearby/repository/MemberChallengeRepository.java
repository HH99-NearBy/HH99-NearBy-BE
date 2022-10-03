package com.hh99.nearby.repository;

import com.hh99.nearby.entity.Challenge;
import com.hh99.nearby.entity.Member;
import com.hh99.nearby.entity.MemberChallenge;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface MemberChallengeRepository extends JpaRepository<MemberChallenge,Long> {

    Optional<MemberChallenge> findByMember_IdAndChallenge_Id(Long memberId,Long ChallengeId);

    List<MemberChallenge> findByMember(Member member);

    List<MemberChallenge> findByMember_Nickname(String nickname, Pageable pageable);


    List<MemberChallenge> findAllByMember_nickname(String nickname);

    Long countAllByChallenge(Challenge challenge);
    Long countByChallengeAndMemberNickname(Challenge challenge, String nickname);

//    MemberChallenge findByMember_nickname(String nickname);
    @Query(value = "Select p FROM MemberChallenge p where p.startDay = current_date")
    List<MemberChallenge> oneday();

    @Query(value = "Select p FROM MemberChallenge p where p.startDay = current_date -1")
    List<MemberChallenge> twoday();

    @Query(value = "Select p FROM MemberChallenge p where p.startDay = current_date -2")
    List<MemberChallenge> threeday();
    List<MemberChallenge> findAllByStartDayEquals(LocalDate localDate);

    @Query(value = "Select p FROM MemberChallenge p where p.startDay = current_date -3")
    List<MemberChallenge> fourday();

    @Query(value = "Select p FROM MemberChallenge p where p.startDay = current_date -4")
    List<MemberChallenge> fiveday();
    @Query(value = "Select p FROM MemberChallenge p where p.startDay = current_date -5")
    List<MemberChallenge> sixday();

    @Query(value = "Select p FROM MemberChallenge p where p.startDay = current_date -6")
    List<MemberChallenge> sevenday();


}
