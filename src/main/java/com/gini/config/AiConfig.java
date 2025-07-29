package com.gini.config;

import com.gini.advisor.LoggerAdvisor;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SafeGuardAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.memory.repository.jdbc.JdbcChatMemoryRepository;
import org.springframework.ai.chat.memory.repository.jdbc.PostgresChatMemoryRepositoryDialect;
import org.springframework.ai.model.tool.ToolCallingManager;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

//https://github.com/spring-projects/spring-ai/blob/main/models/spring-ai-openai/src/test/java/org/springframework/ai/openai/chat/proxy/OllamaWithOpenAiChatModelIT.java

/**
 * Using OpenAPI API with Ollama and Gemma3 and llama:3.2
 */
@Configuration(proxyBeanMethods = false)
@RequiredArgsConstructor
public class AiConfig {

    private final LoggerAdvisor loggerAdvisor;
    private final JdbcTemplate jdbcTemplate;

    @Bean
    public ChatClient chatClient(OpenAiChatModel openAiChatModel, ChatMemory chatMemory) {
        var x = ChatClient.builder(openAiChatModel)
                .defaultAdvisors(
                        loggerAdvisor,
                        MessageChatMemoryAdvisor.builder(chatMemory).build(),
                        new SafeGuardAdvisor(List.of("bau bau", "hi hi hi"))
                )
//                .defaultOptions(chatOptions)
                .build();

        return x;
    }

    @Bean
    public OpenAiApi chatCompletionApi() {
        return OpenAiApi.builder()
                .baseUrl("http://localhost:11434")
                .apiKey("fake_api_key")
                .build();
    }

    @Bean
    public OpenAiChatModel openAiClient(OpenAiApi openAiApi) {
        return OpenAiChatModel.builder()
                .openAiApi(openAiApi)
                .toolCallingManager(ToolCallingManager.builder().build())
//                .defaultOptions(OpenAiChatOptions.builder().model("gemma3:12b")
                .defaultOptions(OpenAiChatOptions.builder().model("llama3.2")
                        .topP(0.9)
                        .maxTokens(300000)
                        .maxCompletionTokens(50000)
                        .temperature(0.8)
                        .build()
                )
                .build();
    }

    /**
     * .maxMessages(100) -> represents the number of messages saved in the database for each conversationId.
     * If I ask the LLm a question this represents 1 message. If the LLM responds this represents another message.
     * So for each question 2 messages will be saved in the database: 1 message for the question, and 1 message for the response
     * See: TestController -> @GetMapping("/question2")
     * How it Works: for every conversationId the app does a call to the database, takes all the content for that conversationId
     * and sends it to the LLM. The content we have the more tokens we consume
     */

    @Bean
    public ChatMemory chatMemory() {
        var chatMemoryRepository = JdbcChatMemoryRepository.builder()
                .jdbcTemplate(jdbcTemplate)
                .dialect(new PostgresChatMemoryRepositoryDialect())
                .build();

        var chatMemory = MessageWindowChatMemory.builder()
                .chatMemoryRepository(chatMemoryRepository)
                .maxMessages(100) // -> see comment above
                .build();

        return chatMemory;
    }


    //
//    @Bean //because i defined the configuration in the yaml file this values are not set on the chat client
//    public ChatOptions chatOptions(){
//        return ChatOptions.builder()
//                .
//                .temperature(0.5)  // -> determines how randpm the answer is. Modify this or topP but not both.

    /// /                .topP(0.2) -> determines the randomness of the tokens
//                .build();
//    }


}
