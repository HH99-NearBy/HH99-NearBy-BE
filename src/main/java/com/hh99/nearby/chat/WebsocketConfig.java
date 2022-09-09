package com.hh99.nearby.chat;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Slf4j
@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
@EnableWebSocket
public class WebsocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        log.info("/sub 발동");
        config.enableSimpleBroker("/sub"); // prefix /sub 로 수신 메시지 구분
        //여기로 데이터가 들어온 경우 messageMapping 으로 JUMP
        log.info("/pub 발동");
        config.setApplicationDestinationPrefixes("/pub"); // prefix /pub 로 발행 요청
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        WebSocketMessageBrokerConfigurer.super.registerStompEndpoints(registry);
        registry.addEndpoint("/ws-stomp") // url/chatting 웹 소켓 연결 주소
                .setAllowedOriginPatterns("*")
                .withSockJS(); // sock.js를 통하여 낮은 버전의 브라우저에서도 websocket 이 동작할수 있게 한다
    }
}
