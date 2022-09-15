package com.hh99.nearby.chat.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@RequiredArgsConstructor
@Configuration
@EnableWebSocketMessageBroker //메시지브로커가 지원하는 WebSocket 메시지 처리
public class ChatConfig implements WebSocketMessageBrokerConfigurer {

    private final StompHandler stompHandler;



    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {

        registry.enableSimpleBroker("/sub"); // ※pub/sub으로 변경할것
        //topic은 한명이 message를 보내면 해당 토픽을 구독하고있는 n명에게 메시지를 뿌릴떄 사용
        //queue은 한명이 message를 보내면 발행한 한명에게 정보를 보내는경우

        registry.setApplicationDestinationPrefixes("/pub"); //메세지 보낼경로
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) { //Client에서 websocket연결할 때 사용할 API 경로를 설정해주는 메서드
        registry.addEndpoint("/ws/chat") //websocket연결할 때 사용할 API 경로
                .setAllowedOriginPatterns("*") //Cors 처리
                .withSockJS(); //SockJS
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(stompHandler);
    }


}
