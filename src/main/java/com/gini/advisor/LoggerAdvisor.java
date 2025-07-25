package com.gini.advisor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClientRequest;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.client.advisor.api.CallAdvisor;
import org.springframework.ai.chat.client.advisor.api.CallAdvisorChain;
import org.springframework.stereotype.Component;

/**
 *<a href="https://docs.spring.io/spring-ai/reference/api/advisors.html#_implementing_an_advisor">Logging</a>
 * */

@Slf4j
@Component
public class LoggerAdvisor implements CallAdvisor {

    @Override
    public ChatClientResponse adviseCall(ChatClientRequest chatClientRequest, CallAdvisorChain chain) {

        log.info("BEFORE: {}", chatClientRequest);

        var adviseResponse = chain.nextCall(chatClientRequest);

        log.info("AFTER: {}", adviseResponse);

        var usage = adviseResponse.chatResponse().getMetadata().getUsage();

        log.info("""
                
                Answer tokens:*********
                prompt tokens:     {}
                completion tokens: {}
                -----------------------
                Total tokens:      {}
                ***********************
                """, usage.getPromptTokens(), usage.getCompletionTokens(), usage.getTotalTokens());

        return adviseResponse;
    }


    @Override
    public String getName() {
        return this.getClass().getSimpleName();
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
