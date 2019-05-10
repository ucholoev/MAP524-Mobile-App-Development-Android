package com.example.ace.weatherview;

import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements OnWeatherRequestCompleted {

    public static final String WEATHER_KEY = "WEATHER_DATA";
    public static final String LOG_KEY = "WEATHER_VIEWER";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final AppDataManager appDataManager = new AppDataManager(this);

        ImageButton searchButton = findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText searchText = findViewById(R.id.searchText);
                String city = searchText.getText().toString();

                appDataManager.saveCity(city);

                WeatherRequest task = new WeatherRequest(MainActivity.this);
                task.execute(city);
            }
        });

        String city = appDataManager.getCity();

        if (city != null && !city.isEmpty())
        {
            WeatherRequest task = new WeatherRequest(MainActivity.this);
            task.execute(city);
        }
    }

    @Override
    public void onTaskCompleted(WeatherData data) {
        // Show the WeatherData to the user

        Bundle args = new Bundle();
        args.putParcelable(WEATHER_KEY, data);

        WeatherDetails weatherDetails = new WeatherDetails();
        weatherDetails.setArguments(args);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        fragmentTransaction.replace(R.id.weatherFragment, weatherDetails).commit();
    }

    @Override
    public void onTaskFailed(Exception e)
    {
        CharSequence text = this.getResources().getString(R.string.search_error);
        Toast toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
        toast.show();
    }

    public static class WeatherRequest extends AsyncTask<String, Void, WeatherData>
    {
        private OnWeatherRequestCompleted listener;
        private ApiException e;

        public WeatherRequest(OnWeatherRequestCompleted listener)
        {
            this.listener = listener;
            this.e = null;
        }

        @Override
        protected WeatherData doInBackground(String... params) {
            String city = params[0];
            WeatherData data = null;

            try
            {
                YahooApiManager apiManager = new YahooApiManager(city);

                 data = apiManager.getWeather();

                /**Fake data just for the example
                 *
                 * WeatherData data = new WeatherData(
                 *                     city,
                 *                     20,
                 *                     25,
                 *                     5,
                 *                     "Sunny"
                 *             );
                 */
            } catch (ApiException e)
            {
                Log.e(MainActivity.LOG_KEY, e.getClass().getName() + ": " + e.getMessage());
                this.e = e;
            }


            return data;
        }

        @Override
        protected void onPostExecute(WeatherData data)
        {
            if (this.e != null)
            {
                listener.onTaskFailed(e);
            }else
            {
                listener.onTaskCompleted(data);
            }
        }

    }
}
