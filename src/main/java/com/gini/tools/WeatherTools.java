package com.gini.tools;

import com.gini.carriers.response.LocationResponse;
import com.gini.carriers.response.WeatherResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;


/**
 * It doesn't work to call one tool after another. Or the AI model does not know how to call it.
 *
 * */
@Slf4j
@Component
@RequiredArgsConstructor
public class WeatherTools {

    @Value("${weather.key}")
    private String weatherKey;
    private final RestClient restClient;

    /**
     * It doesn't work to call the first function and then the second one. The AI does not know to call the second function
     * */

//
//    @Tool(description = """
//            1. This is the first function to be called that returns 'locationId'.
//            2. This is the first method from the chain of tools to find the weather for a city.
//            3. Use the retuned value from this function as a parameter in the 'getWeatherForCity' function,
//            that is the second function in the weather chain.
//            """)
//    public String locationId(@ToolParam(description = "city name") String city) {
//
//        var locationId = restClient.get().uri(builder ->
//                        builder.path("/find_places")
//                                .queryParam("language", "en")
//                                .queryParam("text", city)
//                                .queryParam("key", weatherKey)
//                                .build()
//                )
//                .retrieve()
//                .toEntity(new ParameterizedTypeReference<List<LocationResponse>>() {
//                }).getBody()
//                .getFirst().place_id(); // yes bla bla might have NullPointerException (is just a dummy app :))
//
//        log.info("LOCATION ID: {}", locationId);
//        return locationId;
//    }

//    @Tool(description = """
//            1. This is the second function to be called that will return the weather of a city.
//            3. The returned valued of 'locationId' function will be used here as a parameter
//            4. This is the second function in the weather chain.
//            """)
//    public WeatherResponse getWeatherForCity(@ToolParam(description = "locationId") String locationId) {
//        var weatherResponse = restClient.get().uri(builder ->
//                        builder.path("/point")
//                                .queryParam("language", "en")
//                                .queryParam("place_id", locationId)
//                                .queryParam("key", weatherKey)
//                                .build()
//                )
//                .retrieve()
//                .toEntity(WeatherResponse.class)
//                .getBody();
//        log.info("WEATHER RESPONSE: {}", weatherResponse);
//        return weatherResponse;
//    }

    @Tool(description = """
            Returns the weather report for a city in Romania.
            When asked about the weather in a city in Romania.
            Do not run this tool if the town is not in Romania
            Check if the city is in Romania before calling this tool
            """)
    public WeatherResponse getWeatherForCity(@ToolParam(description = "city name for what we need to know the weather") String city) {

        var locationId = restClient.get().uri(builder ->
                        builder.path("/find_places")
                                .queryParam("language", "en")
                                .queryParam("text", city)
                                .queryParam("key", weatherKey)
                                .build()
                )
                .retrieve()
                .toEntity(new ParameterizedTypeReference<List<LocationResponse>>() {})
                .getBody()
                .getFirst()
                .place_id();

        var weatherResponse = restClient.get().uri(builder ->
                        builder.path("/point")
                                .queryParam("language", "en")
                                .queryParam("place_id", locationId)
                                .queryParam("key", weatherKey)
                                .build()
                )
                .retrieve()
                .toEntity(WeatherResponse.class)
                .getBody();
        log.info("WEATHER RESPONSE: {}", weatherResponse);
        return weatherResponse;
    }








}
