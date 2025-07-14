package com.ai.demo_ai_chatbot.service;

import com.ai.demo_ai_chatbot.data.Content;
import com.ai.demo_ai_chatbot.data.Part;
import com.ai.demo_ai_chatbot.data.Request;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatService {
    @Value("${gemini.api.url}")
    public String geminiApiUrl ;

    @Value("${gemini.api.key}")
    public String geminiApiKey ;

    private final WebClient webclient;

    @SneakyThrows
    public String gettingAnswer(String query) {

        List<Part> listOfParts = new ArrayList<>();
        listOfParts.add( Part.builder().text(query).build());

        List<Content> listOfContent = new ArrayList<>();
        listOfContent.add(Content.builder().parts(listOfParts).build());

        Request request = Request.builder().contents(listOfContent).build();

        ObjectMapper mapper = new ObjectMapper();
        log.info("The Request is: {}", mapper.writeValueAsString(request));

        return webclient.post()
                .uri(geminiApiUrl+geminiApiKey)
                .header("Content-Type","application/json")
                .bodyValue(request)  //request
                .retrieve()
                .bodyToMono(String.class)//reactive wrapper for String
                .block();
    }

}
