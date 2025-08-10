package com.gini.controller;

import com.gini.carriers.request.QuestionRequest;
import com.gini.carriers.response.QuestionResponse;
import com.gini.services.RagService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
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
    @PostMapping("/load2")
//    @RequestMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void loadPdf(@RequestPart MultipartFile file) {
        System.out.println("--------------");
        ragService.loadPDFs2(file);
    }

    @CrossOrigin("*")
    @PostMapping("/rag")
    public QuestionResponse getInfo(@RequestBody QuestionRequest questionRequest) {
        var response = ragService.getInfo(questionRequest.message());

        return new QuestionResponse(response) ;
    }


}
