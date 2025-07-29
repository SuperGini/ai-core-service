package com.gini.controller;

import com.gini.carriers.request.QuestionRequest;
import com.gini.services.RagService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RagController {


    private final RagService ragService;

    @GetMapping("/load")
    public void loadText() {
        ragService.loadPDFs();
    }


    @GetMapping("/rag")
    public String getInfo(@RequestBody QuestionRequest  questionRequest) {
        var response = ragService.getInfo(questionRequest.message());

        return response;
    }



}
