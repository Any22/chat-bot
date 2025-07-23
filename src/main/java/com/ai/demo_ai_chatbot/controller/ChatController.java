package com.ai.demo_ai_chatbot.controller;

import com.ai.demo_ai_chatbot.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@CrossOrigin(origins="*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/bot")
public class ChatController {
    private final ChatService chatService;

    @PostMapping(value = "/query")
    @SneakyThrows
    ResponseEntity<String> askAQuestion(@RequestBody Map<String, String> query) {
        log.info("The request looks like {}", query);
        String request = query.get("text");
        String response = chatService.gettingAnswer(request);
        log.info("The response is {}",response);
        return ResponseEntity.ok(response);
    }


}