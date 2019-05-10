package com.example.ace.weatherview;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class YahooApiManager {
    private String city;

    public YahooApiManager(String city)
    {
        this.city = city;
    }

    public WeatherData getWeather() throws ApiException
    {
        JSONObject json = makeRequest();
        WeatherData data = parseJson(json);
        return data;
    }

    private JSONObject makeRequest() throws ApiException
    {
        HttpURLConnection urlConnection;

        String yql = String.format(
                "select * from weather.forecast where u=\"c\" and woeid in (select woeid from geo.places(1) where text=\"%s\")",
                city
        );

        try
        {
            yql = URLEncoder.encode(yql, "utf-8");

            // https://query.yahooapis.com/v1/public/yql?
            // q=select%20*%20from%20weather.forecast%20where%20u%3D%22c%22%20and%20woeid%20in%20(select%20woeid%20from%20geo.places(1)%20where%20text%3D%22nome%2C%20ak%22)
            // &format=json

            StringBuilder urlString = new StringBuilder();
            urlString.append("https://query.yahooapis.com/v1/public/yql?");
            urlString.append("q=").append(yql);
            urlString.append("&format=json");

            URL url = new URL(urlString.toString());

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(inputStream)
            );

            String temp;
            StringBuilder response = new StringBuilder();

            while (( temp = bufferedReader.readLine()) != null)
            {
                response.append(temp);
            }

            JSONObject json = (JSONObject) new JSONTokener(
                    response.toString()
            ).nextValue();

            Log.d(MainActivity.LOG_KEY, json.toString());

            return json;

        }catch (IOException|JSONException e)
        {
            Log.e(MainActivity.LOG_KEY, e.getClass().getName() + ": " + e.getMessage());

            throw new ApiException("Unable to process request", e);
        }
    }

    private WeatherData parseJson(JSONObject json) throws ApiException
    {
        if (json == null)
        {
            Log.e(MainActivity.LOG_KEY, "parseJson: parameter is null");
        }

        try
        {
            JSONObject item = json
                    .getJSONObject("query")
                    .getJSONObject("results")
                    .getJSONObject("channel")
                    .getJSONObject("item");

            JSONObject condition = item.getJSONObject("condition");

            int currentTemperature = condition.getInt("temp");
            String description = condition.getString("text");

            JSONArray forecast = item.getJSONArray("forecast");

            int highTemperature = forecast.getJSONObject(0).getInt("high");
            int lowTemperature = forecast.getJSONObject(0).getInt("low");
            int forecastHighTemperature = forecast.getJSONObject(1).getInt("high");
            int forecastLowTemperature = forecast.getJSONObject(1).getInt("low");

            WeatherData data = new WeatherData(
                    city,
                    currentTemperature,
                    highTemperature,
                    lowTemperature,
                    description,
                    forecastHighTemperature,
                    forecastLowTemperature
            );

            return data;

        } catch (JSONException e)
        {
            Log.e(MainActivity.LOG_KEY, e.getClass().getName() + ": " + e.getMessage());

            throw new ApiException("Unable to parse JSON data", e);
        }
    }
}
