package com.hh99.nearby.chat.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage { //뷰로 보내는 메세지
    public enum MessageType {
        ENTER, TALK, QUIT
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

    private List<String> peopleList;
}
