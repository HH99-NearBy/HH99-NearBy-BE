package com.hh99.nearby.chat.entity;


import com.hh99.nearby.entity.Timestamped;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Chat extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String sessionId; //채팅방 입장시 발행되는 sessionId

    @Column(nullable = false)
    private String sender; //메세지 작성자 (닉네임)

    @Column(nullable = false)
    private Long entryTime; //입장시간 (CurrentTime)
    
    @Column(nullable = false)
    private Long challengeId; //입장한 첼린지 아이디






}
