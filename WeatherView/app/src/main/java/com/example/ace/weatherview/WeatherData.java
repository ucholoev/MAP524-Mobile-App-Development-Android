package com.example.ace.weatherview;

import android.os.Parcel;
import android.os.Parcelable;

public class WeatherData implements Parcelable {
    private String city;
    private int currentTemperature;
    private int highTemperature;
    private int lowTemperature;
    private String description;
    private int forecastHighTemperature;
    private int forecastLowTemperature;

    public WeatherData(
            String city,
            int currentTemperature,
            int highTemperature,
            int lowTemperature,
            String description,
            int forecastHighTemperature,
            int forecastLowTemperature
    )
    {
        this.city = city;
        this.currentTemperature = currentTemperature;
        this.highTemperature = highTemperature;
        this.lowTemperature = lowTemperature;
        this.description = description;
        this.forecastHighTemperature = forecastHighTemperature;
        this.forecastLowTemperature = forecastLowTemperature;
    }

    private WeatherData(Parcel in)
    {
        this(
                in.readString(),
                in.readInt(),
                in.readInt(),
                in.readInt(),
                in.readString(),
                in.readInt(),
                in.readInt()
        );
    }

    public static final Creator<WeatherData> CREATOR = new Creator<WeatherData>() {
        @Override
        public WeatherData createFromParcel(Parcel in) {
            return new WeatherData(in);
        }

        @Override
        public WeatherData[] newArray(int size) {
            return new WeatherData[size];
        }
    };

    public String getCity()
    {
        return city;
    }

    public int getCurrentTemperature()
    {
        return currentTemperature;
    }

    public int getHighTemperature()
    {
        return highTemperature;
    }

    public int getLowTemperature()
    {
        return lowTemperature;
    }

    public String getDescription()
    {
        return description;
    }

    public int getForecastHighTemperature()
    {
        return forecastHighTemperature;
    }

    public int getForecastLowTemperature()
    {
        return forecastLowTemperature;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(getCity());
        dest.writeInt(getCurrentTemperature());
        dest.writeInt(getHighTemperature());
        dest.writeInt(getLowTemperature());
        dest.writeString(getDescription());
        dest.writeInt(getForecastHighTemperature());
        dest.writeInt(getForecastLowTemperature());
    }
}
