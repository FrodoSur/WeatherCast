package com.example.weathercast.menu.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(indices = {@Index(value = {City.CITY,City.TEMPERATURE,City.WEATHER})})
public class City {
    public final static String ID = "id";
    public final static String CITY ="city";
    public final static String TEMPERATURE ="temperature";
    public final static String WEATHER ="weather";

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ID)
    public long id;
    @ColumnInfo(name = CITY)
    public String city;
    @ColumnInfo(name = TEMPERATURE)
    public String temperature;
    @ColumnInfo(name = WEATHER)
    public String weather;

}
