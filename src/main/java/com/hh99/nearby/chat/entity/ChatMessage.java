package com.hh99.nearby.chat.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage {

    public enum MessageType {
        ENTER, TALK, QUIT
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private MessageType type;

    @Column
    private String roomId;

    // Redis MessageListener 로 뒙소켓을 통해 바로 채팅방에 메시지를 전달해주기 위한 값을 따로 설정해주었다
    @Column
    private String nickname;

    @Column
    private String sender;

    @Column
    private String message;

    @Column
    private String createdAt;


}
