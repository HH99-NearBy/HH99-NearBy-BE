package com.hh99.nearby.chat.dto;

import com.hh99.nearby.chat.entity.ChatMessage;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessageRequestDto {
    private ChatMessage.MessageType type;
    private String roomId;
    private String nickname;
    private String sender;
    private String message;
}
