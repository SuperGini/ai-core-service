package com.gini.controller;

import com.gini.carriers.request.QuestionRequest;
import com.gini.carriers.response.WeatherResponse;
import com.gini.tools.WeatherTools;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class WeatherController {

    private final WeatherTools weatherTools;
    private final ChatClient chatClient;

    private static final String systemInfo = """
            You are a meteorologist that can help with information about
            the weather, in Romania. In general the question will be about the weather in Romanian cities or zones.
            
            ---------------------------------------------------
            Before you answer the question you need to check:
            If the city is in Romania.
            ---------------------------------------------------
            --------------------------------------------------------------------------------------------------------
            DO NOT ANSWER TO THIS TYPE OF QUESTIONS:
            Do not answer question that are not related to weather.
            Do not answer question that are not about the weather in cities in Romania.
            --------------------------------------------------------------------------------------------------------
            
            If you get asked other question that are not about the weather in Romania just reply with this sentence:
            "I don't have information about this subject. I can only replay to questions about whether in Romania."
            
            If the user does not give you the name of the city in Romania and only asks about the weather in general
            respond with this question:
            "I can only respond to question about weathers in Romanian cities. Please provide the name of a city."
            """;

    @GetMapping("/weather3")
    public String question4(@RequestBody QuestionRequest questionRequest) {

        var response = chatClient.prompt()
                .system(systemInfo)
                .user(u -> u.text(questionRequest.message()))
                .tools(weatherTools)
                .call()
                .content();

        return response;
    }

    // STRUCTURED OUTPUT
    @GetMapping("/weather5")
    public WeatherResponse question5(@RequestBody QuestionRequest questionRequest) {
        var systemInfo = """
                You are a meteorologist that can help with information about
                the weather, in Romania. In general the question will be about the weather in Romanian cities or zones.
                
                ---------------------------------------------------
                Before you answer the question you need to check:
                If the city is in Romania.
                ---------------------------------------------------
                --------------------------------------------------------------------------------------------------------
                DO NOT ANSWER TO THIS TYPE OF QUESTIONS:
                Do not answer question that are not related to weather.
                Do not answer question that are not about the weather in cities in Romania.
                --------------------------------------------------------------------------------------------------------
                
                If you get asked other question that are not about the weather in Romania just reply with this sentence:
                "I don't have information about this subject. I can only replay to questions about whether in Romania."
                
                If the user does not give you the name of the city in Romania and only asks about the weather in general
                respond with this sentence:
                "I can only respond to question about weathers in Romanian cities. Please provide the name of a city."
                
                IMPORTANT!!!!
                If you can't create the JSON schema or the question is not related with weather from Romania cities,
                just put the response in the 'errorResponse' of the JSON.
                
                EXAMPLE of JSON when you can't respond to user question:
                1. Example: 1
                    {
                        "errorResponse": "I can only respond to question about weathers in Romanian cities. Please provide the name of a city."
                    }
                2. Example 2:
                    {
                        "errorResponse":"I don't have information about this subject. I can only replay to questions about whether in Romania."
                    }
                """;


        var response = chatClient.prompt()
                .system(systemInfo)
                .system("If you cant return the response as a JSON just put the response in the 'errorResponse' filed of the JSON")
                .user(u -> u.text(questionRequest.message()))
                .tools(weatherTools)
                .call()
                .entity(WeatherResponse.class);


        return response;
    }


}
