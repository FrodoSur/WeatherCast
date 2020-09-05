package com.example.weathercast.menu;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.SensorEventListener;
import android.os.Build;
import android.os.Bundle;
import com.example.weathercast.R;
import com.example.weathercast.menu.page.main.MainFragment;
import com.example.weathercast.menu.reciever.LowBatteryReciever;
import com.example.weathercast.menu.reciever.NetworkReceiver;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
public class MainActivity extends AppCompatActivity implements StartFragment {

    private NetworkReceiver wiFiStateChange = new NetworkReceiver();
    private LowBatteryReciever powerConnected = new LowBatteryReciever();

    private static final String TAG = "WEATHER";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);

        registerBroadcastReceivers();
        initNotificationChannel();
        startFragment(new MainFragment());
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

}

