package com.gini.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class RagRepository {

    private final VectorStore vectorStore;

    public String getInfo(String question) {

        var request = SearchRequest.builder()
                .query(question)
                .topK(8)
//                .similarityThreshold(0.5)
                .build();


        var d = vectorStore.similaritySearch(request)
                .stream()
                .map(x -> x.getText())
                .collect(Collectors.joining("/n"));


        return d;


    }




}
