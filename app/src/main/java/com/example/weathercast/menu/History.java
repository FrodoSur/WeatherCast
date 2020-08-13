package com.example.weathercast.menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.weathercast.R;
import com.example.weathercast.menu.dao.HistoryDao;
import com.example.weathercast.menu.model.City;

public class History extends AppCompatActivity {

    private HistoryAdapter adapter;
    private CitySource historySource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        setSupportActionBar(toolbar);

        initRecyclerView();
    }

    // инициализация списка
    private void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        HistoryDao educationDao = App
                .getInstance()
                .getHistoryDao();
        historySource = new HistorySource(educationDao);

        adapter = new HistoryAdapter(educationSource,this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        // Добавление новой записи
        if (id == R.id.action_add) {
            historySource.addCity();
            adapter.notifyDataSetChanged();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.view_context:
                City viewStudent = historySource
                        .getCities()
                        .get((int) adapter.getMenuPosition());
                return true;
            // Добавить запись
            case R.id.add_context:
                // Получаем студента со случайными данными
                City studentForInsert = new RandomStudent(getResources())
                        .rndStudent();
                // Добавляем студента
                educationSource.addStudent(studentForInsert);
                adapter.notifyDataSetChanged();
                return true;
            case R.id.update_context:
                // Изменение имени и фамилии у студента
                City oldStudent = educationSource
                        .getCity()
                        .get((int) adapter.getMenuPosition());
                City studentForUpdate = new RandomStudent(getResources())
                        .rndUpdateStudent(oldStudent);
                educationSource.updateStudent(studentForUpdate);
                adapter.notifyItemChanged((int) adapter.getMenuPosition());
                return true;
            case R.id.remove_context:
                // Удалить запись из базы
                City studentForRemove = educationSource
                        .getCity()
                        .get((int) adapter.getMenuPosition());
                educationSource.removeCity(studentForRemove.id);
                adapter.notifyItemRemoved((int) adapter.getMenuPosition());
                return true;
        }
        return super.onContextItemSelected(item);
    }
}