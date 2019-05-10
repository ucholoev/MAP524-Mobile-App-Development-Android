package com.example.ace.weatherview;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class WeatherDetails extends Fragment {

    public WeatherDetails() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    )
    {
        WeatherData data = getArguments().getParcelable(MainActivity.WEATHER_KEY);

        View v = inflater.inflate(R.layout.fragment_weather_details, container, false);

        TextView cityTextView = v.findViewById(R.id.cityTextView);
        TextView currentTemperatureTextView = v.findViewById(R.id.currentTemperatureTextView);
        TextView highTemperatureTextView = v.findViewById(R.id.highTemperatureTextView);
        TextView lowTemperatureTextView = v.findViewById(R.id.lowTemperatureTextView);
        TextView descriptionTextView = v.findViewById(R.id.weatherDescriptionTextView);
        TextView forecastHighTextView = v.findViewById(R.id.forecastHighTemperatureTextView);
        TextView forecastLowTextView = v.findViewById(R.id.forecastLowTemperatureTextView);

        if( data != null )
        {
            cityTextView.setText(data.getCity());

            currentTemperatureTextView.setText(
                    String.format(
                            getResources().getString(R.string.temperature_celsius),
                            data.getCurrentTemperature()
                    )
            );

            highTemperatureTextView.setText(
                    String.format(
                            getResources().getString(R.string.temperature_celsius),
                            data.getHighTemperature()
                    )
            );

            lowTemperatureTextView.setText(
                    String.format(
                            getResources().getString(R.string.temperature_celsius),
                            data.getLowTemperature()
                    )
            );

            descriptionTextView.setText(data.getDescription());

            forecastHighTextView.setText(
                    String.format(
                            getResources().getString(R.string.temperature_celsius),
                            data.getForecastHighTemperature()
                    )
            );

            forecastLowTextView.setText(
                    String.format(
                            getResources().getString(R.string.temperature_celsius),
                            data.getForecastLowTemperature()
                    )
            );
        }

        return v;
    }

}
