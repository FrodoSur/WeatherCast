package com.example.weathercast.menu;

import android.app.Application;

import androidx.room.Room;

import com.example.weathercast.menu.dao.HistoryDao;
import com.example.weathercast.menu.database.CityDatabase;


// Паттерн Singleton, наследуем класс Application, создаём базу данных
// в методе onCreate
public class App extends Application {

    private static App instance;

    // База данных
    private CityDatabase db;

    // Получаем объект приложения
    public static App getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        // Сохраняем объект приложения (для Singleton’а)
        instance = this;

        // Строим базу
        db = Room.databaseBuilder(
                getApplicationContext(),
                CityDatabase.class,
                "city_database")
                .allowMainThreadQueries()
                .build();
    }

    // Получаем EducationDao для составления запросов
    public HistoryDao getHistoryDao() {
        return db.getHistoryDao();
    }
}
