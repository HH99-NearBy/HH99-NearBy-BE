package com.hh99.nearby.chat.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatMessage { //뷰로 보내는 메세지
    public enum MessageType {
        ENTER, TALK, QUIT,END
    }

    private MessageType type;
    //채팅방 ID
    private String roomId;
    //보내는 사람
    private String sender;
    //내용
    private String message;

    private String sendTime;

    private String sessionId;

    private String level;
}
