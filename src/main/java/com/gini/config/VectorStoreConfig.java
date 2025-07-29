package com.gini.config;

import org.springframework.ai.document.MetadataMode;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.openai.OpenAiEmbeddingModel;
import org.springframework.ai.openai.OpenAiEmbeddingOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.ai.vectorstore.pgvector.PgVectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.springframework.ai.vectorstore.pgvector.PgVectorStore.PgIndexType.HNSW;

@Configuration
public class VectorStoreConfig {

    @Bean
    public PgVectorStore vectorsore(JdbcTemplate jdbcTemplate, OpenAiApi openAiApi) {


        var options = new OpenAiEmbeddingOptions();
        options.setModel("llama3.2");

        var embedingModel = new OpenAiEmbeddingModel(openAiApi, MetadataMode.EMBED, options);

        var vectorStore = PgVectorStore
                .builder(jdbcTemplate, embedingModel)
                .dimensions(3072)
                .indexType(HNSW)
                .build();

        return vectorStore;
    }
}
