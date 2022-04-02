package com.example.a4350_project_1;

public class WeatherTableBuilder {
    private String location;
    private String weatherJson;

    public WeatherTableBuilder setLocation(String location) {
        this.location = location;
        return this;
    }

    public WeatherTableBuilder setWeatherJson(String weatherJson) {
        this.weatherJson = weatherJson;
        return this;
    }

    public WeatherTable createWeatherTable() {
        return new WeatherTable(location, weatherJson);
    }
}