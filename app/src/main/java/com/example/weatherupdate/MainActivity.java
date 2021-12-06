package com.example.weatherupdate;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private final String url = "http://api.openweathermap.org/data/2.5/weather?q=";
    private final String appid = "7d0d7d067ffc446761fb7eeb8d21b20a";
    RequestQueue requestQueue;
    EditText etCity;
    EditText etCountry;
    TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestQueue = Volley.newRequestQueue(this);

        initUi();

    }

    public void initUi() {
        etCity = findViewById(R.id.etCity);
        etCountry = findViewById(R.id.etCountry);
        tvResult = findViewById(R.id.tvResult);
    }

    public void getWeatherDetails(View view) {

        String tempUrl = "";
        String city = etCity.getText().toString().trim();
        String country = etCountry.getText().toString().trim();
        if (!city.isEmpty()) {

            if (!country.isEmpty()) {

                tempUrl = url + city + "," + country + "&appid=" + appid;
                Toast.makeText(getApplicationContext(), "Busqueda por ciudad y pais", Toast.LENGTH_SHORT).show();
            } else {
                tempUrl = url + city + "&appid=" + appid;
                Toast.makeText(getApplicationContext(), "ciudad ", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "El campo de la ciudad no pede estar vacia", Toast.LENGTH_SHORT).show();
        }

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                tempUrl,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        int size = response.length();
                        for (int i = 0; i < size; i++) {
                            try {
                                JSONObject jsonObject;
                                jsonObject = new JSONObject(response.get(i).toString());
                                String temperatura = jsonObject.getString("weather");
                                tvResult.append("id; " + temperatura + "\n");
                                Gson
                            } catch (JSONException e) {
                                e.printStackTrace();

                            }
                        }


                    }
                }, error -> {
            Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            tvResult.setText(error.toString());
            if (error instanceof ServerError) {
                Log.i("TAG", "SERVER ERROR");
            }

            if (error instanceof NoConnectionError) {
                Log.i("TAG", "There is Internet conecction");
            }
            if (error instanceof NetworkError) {
                Log.i("TAG", "Network error");
            }
        });
        requestQueue.add(jsonArrayRequest);
        return;
    }

}