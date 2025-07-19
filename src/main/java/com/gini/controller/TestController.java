package com.gini.controller;

import com.gini.carriers.QuestionRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestController {


    private final ChatClient chatClient;

    @GetMapping("/question")
    public String getResponse(@RequestBody QuestionRequest questionRequest) {

        var response = chatClient.prompt()
                .user(u -> u.text(questionRequest.message())
                        .media(MimeTypeUtils.IMAGE_JPEG, new ClassPathResource("picture/example.jpg"))
                )
                .call()
                .content();

        return response;

    }




}
