package com.gini.services;

import com.gini.repository.RagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class RagService {

    private final ChatClient chatClient;
    private final RagRepository ragRepository;

    public String getInfo(String question) {

        var documents = ragRepository.getInfo(question);

        var userPrompt = """
                Answer to this question: {question}.
                """;

        var systemInfo = """
                You are a java/Spring Framework developer answering question about Hibernate Spring Boot Hibernate etc,
                using only information from Vlad Mihalcea's book: High-performance-java-persistence-Vlad_Mihalcea found in 
                this {documents}
                """;


        return chatClient.prompt()
                .system(s -> s.text(systemInfo).param("documents", documents))
                .user(u -> u.text(userPrompt).param("question", question))
                .call()
                .content();

    }

    public void loadPDFs() {
        ragRepository.loadPDFs();
    }

    public void loadPDFs2(MultipartFile file) {
        ragRepository.loadPDFs2(file);
    }

}
