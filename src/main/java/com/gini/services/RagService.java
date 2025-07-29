package com.gini.services;

import com.gini.repository.RagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RagService {

    @Value("classpath:/book/High-performance-java-persistence-Vlad_Mihalcea.pdf")
    private Resource resource;

    private final VectorStore vectorStore;
    private final ChatClient chatClient;
    private final RagRepository ragRepository;

    public void loadPDFs() {
        var documents = new TikaDocumentReader(resource)
                .read();
        var spritDocuments = new TokenTextSplitter()
                .apply(documents);

        vectorStore.add(spritDocuments);
    }


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


}
