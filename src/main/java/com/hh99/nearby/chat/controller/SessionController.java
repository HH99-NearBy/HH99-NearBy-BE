package com.hh99.nearby.chat.controller;

import com.hh99.nearby.chat.dto.request.SessionRequestDto;
import com.hh99.nearby.chat.service.ViewChatService;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api-sessions")
public class SessionController {

    private final ViewChatService viewChatService;

    @RequestMapping(value = "/get-token", method = RequestMethod.POST)
    public ResponseEntity<?> getToken(@RequestBody SessionRequestDto SessionRequestDto)
            throws ParseException {
        return viewChatService.entryveiw(SessionRequestDto);
    }

    @RequestMapping(value = "/remove-user", method = RequestMethod.POST)
    public ResponseEntity<?> removeUser(@RequestBody SessionRequestDto SessionRequestDto)
            throws Exception {
        return viewChatService.leaveview(SessionRequestDto);
    }
}
