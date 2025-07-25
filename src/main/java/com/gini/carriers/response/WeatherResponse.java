package com.gini.carriers.response;

public record WeatherResponse(
        String latitude,
        String longitude,
        int elevation,
        String units,
        LocationResponse current

) {

    public record LocationResponse(String summary, double temperature, WindResponse wind, PrecipitationResponse precipitation, double cloud_cover) {}
    public record WindResponse(double speed, double angle, String dir) {}
    public record PrecipitationResponse(double total, String type){}

}
