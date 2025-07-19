package com.gini.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.model.NoopApiKey;
import org.springframework.ai.model.tool.ToolCallingManager;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//https://github.com/spring-projects/spring-ai/blob/main/models/spring-ai-openai/src/test/java/org/springframework/ai/openai/chat/proxy/OllamaWithOpenAiChatModelIT.java

@Configuration()
public class AiConfig {


    @Bean
    public ChatClient chatClient(OpenAiChatModel openAiChatModel) {
        var x = ChatClient.builder(openAiChatModel)
//                .defaultOptions(chatOptions)
                .build();

        return x;
    }

    @Bean
    public OpenAiApi chatCompletionApi() {
        return OpenAiApi.builder()
                .baseUrl("http://localhost:11434")
                .apiKey(new NoopApiKey())
                .build();
    }

    @Bean
    public OpenAiChatModel openAiClient(OpenAiApi openAiApi) {
        return OpenAiChatModel.builder()
                .openAiApi(openAiApi)
                .toolCallingManager(ToolCallingManager.builder().build())
                .defaultOptions(OpenAiChatOptions.builder().model("gemma3:12b")
                        .topP(0.9)
                        .maxTokens(300000)
                        .maxCompletionTokens(50000)
                        .temperature(0.5)
                        .build()
                )
                .build();
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
