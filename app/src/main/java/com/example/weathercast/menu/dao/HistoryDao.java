package com.example.weathercast.menu.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.weathercast.menu.model.City;

import java.util.List;

public class HistoryDao {

    // Описание объекта, обрабатывающего данные
// @Dao - доступ к данным
// В этом классе описывается, как будет происходить обработка данных
    @Dao
    public interface EducationDao {

        // Метод для добавления студента в базу данных
        // @Insert - признак добавления
        // onConflict - что делать, если такая запись уже есть
        // В данном случае просто заменим её
        @Insert(onConflict = OnConflictStrategy.REPLACE)
        void insertCity(City city);

        // Метод для замены данных студента
        @Update
        void updateCity(City city);

        // Удаляем данные студента
        @Delete
        void deleteCity(City city);

        // Удаляем данные студента, зная ключ
        @Query("DELETE FROM city WHERE id = :id")
        void deteleCityById(long id);

        // Забираем данные по всем студентам
        @Query("SELECT * FROM city")
        List<City> getAllcities();

        // Получаем данные одного студента по id
        @Query("SELECT * FROM city WHERE id = :id")
        City getCityById(long id);

        //Получаем количество записей в таблице
        @Query("SELECT COUNT() FROM city")
        long getCountStudents();
    }

}
