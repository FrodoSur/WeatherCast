package com.example.weathercast.menu.interfaceForRequest;

import com.example.weathercast.menu.data.WeatherRequest;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface weatherRequestCoord {
    @GET("data/2.5/weather")
    Call<WeatherRequest> loadWeather(@Query("lat") String latitude, @Query("lon") String longitude, @Query("appid") String keyApi);
}
