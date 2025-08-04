package com.gini.controller;

import com.gini.carriers.request.QuestionRequest;
import com.gini.carriers.response.QuestionResponse;
import com.gini.services.RagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class RagController {


    private final RagService ragService;

    @CrossOrigin("*")
    @GetMapping("/load")
    public void loadText() {
        ragService.loadPDFs();
    }

    @CrossOrigin("*")
    @PostMapping("/rag")
    public QuestionResponse getInfo(@RequestBody QuestionRequest questionRequest) {
        var response = ragService.getInfo(questionRequest.message());

        return new QuestionResponse(response) ;
    }


}
