package com.example.weathercast.menu;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
<<<<<<< Updated upstream
=======
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
>>>>>>> Stashed changes
import android.os.Bundle;
import android.os.IBinder;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
<<<<<<< Updated upstream
import android.widget.TextView;

import com.example.weathercast.R;
=======
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.weathercast.R;
import com.example.weathercast.menu.data.WeatherRequest;
import com.example.weathercast.menu.page.main.MainFragment;
import com.example.weathercast.menu.reciever.LowBatteryReciever;
import com.example.weathercast.menu.reciever.NetworkReceiver;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
>>>>>>> Stashed changes
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.SearchView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
<<<<<<< Updated upstream
import com.example.weathercast.menu.MyService.ServiceBinder;
=======

import com.google.firebase.BuildConfig;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
>>>>>>> Stashed changes

import java.util.ArrayList;
import java.util.List;

<<<<<<< Updated upstream
public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager sensorManagerTemp;
    private Sensor temperature;
    private TextView mainTemp;
    private boolean isBound = false;
    private ServiceBinder boundService;
=======
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements StartFragment  {
    private NetworkReceiver wiFiStateChange = new NetworkReceiver();
    private LowBatteryReciever powerConnected = new LowBatteryReciever();
>>>>>>> Stashed changes

    private static final String TAG = "WEATHER";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);

<<<<<<< Updated upstream
        RecyclerView weatherList = findViewById(R.id.recycler_list);
        weatherList.setAdapter(new weatherListAdapter());
        weatherList.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        weatherList.setLayoutManager(layoutManager);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(this,LinearLayoutManager.VERTICAL);
        weatherList.addItemDecoration(itemDecoration);
        Toolbar toolbar = initToolbar();
        initFab();
        initDrawer(toolbar);
        initList();
        initSensor();
        Intent intent = new Intent(MainActivity.this, MyService.class);
        bindService(intent, boundServiceConnection, BIND_AUTO_CREATE);
        mainTemp.setText(boundService.getCityWeather());
        if (isBound){
            unbindService(boundServiceConnection);
        }
    }

    private void initSensor(){
        sensorManagerTemp = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        temperature = sensorManagerTemp.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        mainTemp=findViewById(R.id.temperatureSensor);
    }

    private Toolbar initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        return toolbar;
    }

    private void initDrawer(Toolbar toolbar) {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener((NavigationView.OnNavigationItemSelectedListener) this);
    }

    private void initFab() {
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                PopupMenu menu = new PopupMenu(MainActivity.this, view);
                getMenuInflater().inflate(R.menu.popup_menu, menu.getMenu());
                menu.getMenu().findItem(R.id.update_popup).setVisible(false);
                menu.getMenu().add(0, 123456, 12, "Menu item added");
                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @SuppressLint("DefaultLocale")
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int id = item.getItemId();
                        switch (id) {
                            case 123456:
                                Snackbar.make(view, "Menu item added - clicked", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                                return true;
                        }
                        return true;
                    }
                });
                menu.show();
            }

        });
=======
        registerBroadcastReceivers();
        initNotificationChannel();
        startFragment(new MainFragment());
>>>>>>> Stashed changes
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(wiFiStateChange);
        unregisterReceiver(powerConnected);
    }

    @Override
    public void onBackPressed() {
        Fragment activeFragment = getSupportFragmentManager().findFragmentById(R.id.container_for_fragment);
        // Проверяем, что активный фрагмент является фрагментов класса MainScreenFragment
        if (activeFragment!= null && activeFragment.getClass() == MainFragment.class) {
            // Вызываем у активного фрагмента метод onBackPressed
            if (!((MainFragment) activeFragment).onBackPressed()) {
                finish();
            }
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void startFragment(Fragment fragment) {
        String backStateName = fragment.getClass().getName();

        if (fragment.getClass().equals(MainFragment.class)) {
            getSupportFragmentManager().popBackStackImmediate(MainFragment.class.getName(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container_for_fragment, fragment);
        fragmentTransaction.addToBackStack(backStateName);
        fragmentTransaction.commit();
    }

    private void initNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            int importance = NotificationManager.IMPORTANCE_LOW;
            NotificationChannel channel = new NotificationChannel("2", "name", importance);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void registerBroadcastReceivers() {
        registerReceiver(wiFiStateChange, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
        registerReceiver(powerConnected, new IntentFilter(Intent.ACTION_POWER_CONNECTED));
    }

    @Override
<<<<<<< Updated upstream
    protected void onResume() {
        // Register a listener for the sensor.
        super.onResume();
        sensorManagerTemp.registerListener(this, temperature, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        // Be sure to unregister the sensor when the activity pauses.
        super.onPause();
        sensorManagerTemp.unregisterListener(this);
=======
    public void onPointerCaptureChanged(boolean hasCapture) {

>>>>>>> Stashed changes
    }
}

