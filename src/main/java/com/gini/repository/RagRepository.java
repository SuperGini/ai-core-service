package com.gini.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Repository;

import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class RagRepository {

    @Value("classpath:/book/High-performance-java-persistence-Vlad_Mihalcea.pdf")
    private Resource resource;

    private final VectorStore vectorStore;

    public void loadPDFs() {
        var documents = new TikaDocumentReader(resource)
                .read();

        var splitDocuments = new TokenTextSplitter()
                .apply(documents);

        vectorStore.add(splitDocuments);
    }

    public String getInfo(String question) {

        var request = SearchRequest.builder()
                .query(question)
                .topK(8)
//                .similarityThreshold(0.5)
                .build();


        var searchResponse = vectorStore.similaritySearch(request)
                .stream()
                .map(Document::getText)
                .collect(Collectors.joining("\n"));


        return searchResponse;
    }




}
