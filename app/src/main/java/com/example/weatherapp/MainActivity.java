package com.example.weatherapp;
import static android.app.ProgressDialog.show;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private LinearLayout backgroundImageView;
    private ImageView imageView;

    private TextView cityTv;
    private TextView dateTv;
    private TextView tempTv;
    private TextView descTv;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cityTv = findViewById(R.id.cityTv);
        dateTv = findViewById(R.id.DateTv);
        tempTv = findViewById(R.id.TempTv);
        descTv = findViewById(R.id.DescTv);
        imageView = findViewById(R.id.imageView);
//        backgroundImageView = findViewById(R.id.backgroundImageView);
//        backgroundImageView.setBackgroundResource(R.drawable.constant);

        String CurrentForecast = "https://api.openweathermap.org/data/2.5/weather?q=Kathmandu&appid=b31f986179635820b5d31e28a4cf9fc2";
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                CurrentForecast,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String jsonData = response;
                        Log.d("JSON DATA(Kathmandu):", jsonData);
                        parseWeather(jsonData);
                    }


                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(stringRequest);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);


        ArrayList<DataModel2> tasks = new ArrayList<DataModel2>();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        recyclerView.setLayoutManager(layoutManager);

        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, tasks);
        recyclerView.setAdapter(adapter);
        String forecast = "https://api.openweathermap.org/data/2.5/forecast?q=kathmandu&appid=b31f986179635820b5d31e28a4cf9fc2";



        StringRequest forecastStringRequest = new StringRequest(
                Request.Method.GET,
                forecast,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String jsonData = response;
                        Log.d("JSON DATA (Forecast): ", jsonData);

                        jsonArrayDecode(jsonData, adapter);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(forecastStringRequest);

        EditText locationEt = findViewById(R.id.locationEt);

        locationEt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    String location = locationEt.getText().toString();

        String forecast = "https://api.openweathermap.org/data/2.5/forecast?q=" + location + "&appid=b31f986179635820b5d31e28a4cf9fc2";
        String CurrentForecast = "https://api.openweathermap.org/data/2.5/weather?q=" + location + "&appid=b31f986179635820b5d31e28a4cf9fc2";


        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);

        StringRequest forecastStringRequest = new StringRequest(
                Request.Method.GET,
                forecast,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String jsonData = response;
                        Log.d("JSON DATA (Forecast): ", jsonData);

                        jsonArrayDecode(jsonData, adapter);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Something went wrong.", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(forecastStringRequest);



        StringRequest weatherStringRequest = new StringRequest(
                Request.Method.GET,
                CurrentForecast,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String jsonData = response;
                        Log.d("JSON DATA (Weather): ", jsonData);

                        parseWeather(jsonData);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(weatherStringRequest);

        return true;
    }
                return false;
}
        });
                }
    public void jsonArrayDecode(String jsonData, RecyclerViewAdapter adapter) {
        ArrayList<DataModel2> data = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            JSONArray listArray = jsonObject.getJSONArray("list");

            for (int i = 0; i < listArray.length(); i++) {
                JSONObject listItem = listArray.getJSONObject(i);

                String dateTxt = listItem.getString("dt_txt");
                String[] dateTimeParts = dateTxt.split(" ");
                String date = dateTimeParts[0];

                JSONObject mainObject = listItem.getJSONObject("main");
                double temperatureInKelvin = mainObject.getDouble("temp");
                double temperatureInCelsius = temperatureInKelvin - 273.15;
                String temperature = String.format("%.2f", temperatureInCelsius) + " °C";

                JSONArray weatherArray = listItem.getJSONArray("weather");
                String iconCode = weatherArray.getJSONObject(0).getString("icon");
                String imagesView= "https://openweathermap.org/img/wn/" + iconCode + "@2x.png";

                data.add(new DataModel2( date, imagesView, temperature));  }

            adapter.setPosts(data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
        public void parseWeather(String jsonData) {
            try {
                JSONObject jsonObject = new JSONObject(jsonData);
                JSONObject mainObject = jsonObject.getJSONObject("main");
                JSONArray weatherArray = jsonObject.getJSONArray("weather");
                JSONObject weatherObject = weatherArray.getJSONObject(0);
                String weatherCondition = weatherObject.getString("main");


                JSONArray weatherArrayTwo = jsonObject.getJSONArray("weather");
                String iconCode = weatherArrayTwo.getJSONObject(0).getString("icon");
                String imageUrl = "https://openweathermap.org/img/wn/" + iconCode + "@2x.png";

                String city = jsonObject.getString("name");

                String date = jsonObject.getString("dt");
                long timestamp = Long.parseLong(date);
                String currentDate = formatDate(timestamp);

                double temperatureInKelvin = mainObject.getDouble("temp");
                double temperatureInCelsius = temperatureInKelvin - 273.15;
                String temp = String.format("%.2f", temperatureInCelsius) + " °C";
                String description = weatherObject.getString("description");


                cityTv.setText(city);
                dateTv.setText(currentDate);
                tempTv.setText(temp);
                descTv.setText(description);
//                imageView.setImageResource(imageView);

                Picasso.get().load(imageUrl).into(imageView);

//                if (weatherCondition.equals("Clear")) {
//                    backgroundImageView.setBackgroundResource(R.drawable.image);
//                } else if (weatherCondition.equals("Clouds")) {
//                    backgroundImageView.setBackgroundResource(R.drawable.cloudy);
//                } else if (weatherCondition.equals("Rain")) {
//                    backgroundImageView.setBackgroundResource(R.drawable.rainy);
//                } else if (weatherCondition.equals("Snow")) {
//                    backgroundImageView.setBackgroundResource(R.drawable.snow);
//                } else {
//                    backgroundImageView.setBackgroundResource(R.drawable.constant);
//                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    private String formatDate(long timestamp) {
        Date date = new Date(timestamp * 1000);  // seconds to milliseconds
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault());
        return sdf.format(date);
    }

}






