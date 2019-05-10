package com.example.ace.weatherview;

public interface OnWeatherRequestCompleted {

    void onTaskCompleted(WeatherData data);
    void onTaskFailed(Exception e);
}
