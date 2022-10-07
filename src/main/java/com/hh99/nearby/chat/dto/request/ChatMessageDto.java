package com.hh99.nearby.chat.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatMessageDto { //뷰로 보내는 메세지
    public enum MessageType {ENTER, TALK, QUIT,END,DISCONNECT}

    private MessageType type;
    //채팅방 ID
    private String roomId;
    //보내는 사람
    private String sender;
    //내용
    private String message;
    //메시지 보내는 시간
    private String sendTime;
    //접속한 세션 아이디
    private String sessionId;
    //유저 레벨
    private String level;
}
