package com.hh99.nearby.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class MemberChallenge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long realTime; // 첼린지 진행한 시간

    @JoinColumn(name = "member_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member; //첼린지 참여 맴버

    @JoinColumn(name = "challenge_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Challenge challenge; //참여한 첼린지

    public void update(Long realtime){
        this.realTime = realtime;

    }

}
