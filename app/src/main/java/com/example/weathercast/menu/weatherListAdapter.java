package com.example.weathercast.menu;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weathercast.R;

import java.util.ArrayList;
import java.util.List;


public class weatherListAdapter extends RecyclerView.Adapter {
    class DayViewHolder extends RecyclerView.ViewHolder{
        TextView dateMain;
        TextView temperatureMain;
        ImageView weatherMain;
        public DayViewHolder(@NonNull View itemView) {
            super(itemView);
            dateMain = itemView.findViewById(R.id.dateName);
            temperatureMain = itemView.findViewById(R.id.temperatureName);
            weatherMain = itemView.findViewById(R.id.imageName);
        }
        void bind(String date, String temperature, String weather){
            dateMain.setText(date);
            temperatureMain.setText(temperature);
            if(weather.equals("солнце")){
                Picasso.get()
                        .load("https://c7.hotpng.com/preview/812/34/493/weather-meteorology-overcast-cloud-weather.jpg")
                        .into(weatherMain);
            }
            else
            {

                Picasso.get()
                        .load("https://www.clipartmax.com/png/middle/102-1023856_rain-icon-icon.png")
                        .into(weatherMain);
            }

        }
    }
    private List<String> weatherList = new ArrayList<String>() {
        {
        }
    };
    private List<String> tempretureList = new ArrayList<String>() {
        {
        }
    };
    private List<String> dateList = new ArrayList<String>() {
        {
        }
    };
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DayViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((DayViewHolder)holder).bind(dateList.get(position),tempretureList.get(position),weatherList.get(position));
    }

    @Override
    public int getItemCount() {
        return dateList.size();
    }
}
