package com.example.weathercast.menu;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Binder;
import android.os.IBinder;
import android.widget.TextView;

import com.example.weathercast.R;
import com.example.weathercast.menu.interfaces.OpenWeather;
import com.google.android.material.textfield.TextInputEditText;
import com.example.weathercast.menu.WeatherRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MyService extends Service {

    private static final float AbsoluteZero = -273.15f;

    private OpenWeather openWeather;
    private TextView textTemp;              // Температура (в кельвинах)
    private SharedPreferences sharedPref;
    private TextView temperature;
    private String key="f9215e6fa047766ced19815e1266a0a8";
    private String city="Moscow";
    private String resultSending;
    // Для связывания Activity и сервиса
    private final IBinder binder = new ServiceBinder();

    // Вызывается только один раз, при создании сервиса
    @Override
    public IBinder onBind(Intent intent) {
        initRetorfit();
        requestRetrofit(city, key);
        return binder;
    }
    public String getCityWeather(){
           return resultSending;
       }

    private void initRetorfit() {
        Retrofit retrofit;
        retrofit = new Retrofit.Builder()
                .baseUrl("http://api.openweathermap.org/") // Базовая часть
                // адреса
                // Конвертер, необходимый для преобразования JSON
                // в объекты
                .addConverterFactory(GsonConverterFactory.create())                .build();
        // Создаём объект, при помощи которого будем выполнять запросы
        openWeather = retrofit.create(OpenWeather.class);
    }

    private void requestRetrofit(String city, String keyApi) {
        openWeather.loadWeather(city, keyApi)
                .enqueue(new Callback<WeatherRequest>() {
                    @Override
                    public void onResponse(Call<WeatherRequest> call, Response<WeatherRequest> response) {
                        if (response.body() != null) {
                            float result = response.body().getMain().getTemp() + AbsoluteZero;
                            resultSending = Float.toString(result);
                        }
                    }

                    @Override
                    public void onFailure(Call<WeatherRequest> call, Throwable t) {
                        textTemp.setText("Error");
                    }
                });
    }


    // Класс связи между клиентом и сервисом
    class ServiceBinder extends Binder {
        MyService getService() {
            return MyService.this;
        }
        String getCityWeather(){
                   return getService().getCityWeather();
               }

    }


}
