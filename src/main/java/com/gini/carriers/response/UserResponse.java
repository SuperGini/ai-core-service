package com.gini.carriers.response;

public record UserResponse(
        String shortWeatherInfo,
        String longitude,
        String latitude,
        String city
) {
}
