package com.gini.controller;

import com.gini.carriers.request.QuestionRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestController {


    private final ChatClient chatClient;

    @GetMapping("/question")
    public String getResponse(@RequestBody QuestionRequest questionRequest) {

        String systemInfo = """
                Only replay to questions about the picture provided by the user.
                Do not replay to any questions that are not related to the picture given
                """;

        var response = chatClient.prompt()
                .system(systemInfo)
                .user(u -> u.text(questionRequest.message())
                        .media(MimeTypeUtils.IMAGE_JPEG, new ClassPathResource("picture/example.jpg"))
                )
                .call()
                .content();

        return response;

    }

    @GetMapping("/question2")
    public String getResponse2(@RequestBody QuestionRequest questionRequest) {

        var response = chatClient.prompt()
                .user(u -> u.text(questionRequest.message())
                )
                .call()
                .content();
        return response;
    }

    @GetMapping("/question3")
    public String question3(@RequestParam String character) {

        var response = chatClient.prompt()
                .user(u -> u.text("Give some information about this movie character {character}")
                        .param("character", character)
                )
                .call()
                .content();

        return response;
    }





}
