package snowsan0113.weather_app.android.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.RequiresPermission;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.tabs.TabLayout;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import snowsan0113.weather_app.android.R;
import snowsan0113.weather_app.android.listener.weather_home.WeatherHomeClickListener;
import snowsan0113.weather_app.android.listener.weather_home.WeatherHomeTabSelectListener;
import snowsan0113.weather_app.android.manager.file.JsonConfigManager;
import snowsan0113.weather_app.android.manager.file.JsonManager;

@SuppressLint("MissingPermission")
public class WeatherHomeActivity extends AppCompatActivity implements AppActivity {

    private static boolean first_flag = false;
    private static boolean isActive = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_weather_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.home_activity_root), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initActivity();
    }

    @Override
    protected void onResume() {
        super.onResume();
        isActive = true;
        if (!first_flag) {
            first_flag = true;
            return;
        }

        setTabLayout();
        setNowLocationTab();
    }

    @Override
    protected void onPause() {
        super.onPause();
        isActive = false;
    }

    public void initActivity() {
        setTabLayout();
        setNowLocationTab();

        //listener
        WeatherHomeClickListener click_listener = new WeatherHomeClickListener(this);
        MaterialButton menu_button = findViewById(R.id.menu_button);
        menu_button.setOnClickListener(click_listener);
        MaterialButton search_button = findViewById(R.id.search_button);
        search_button.setOnClickListener(click_listener);
        WeatherHomeTabSelectListener select_listener = new WeatherHomeTabSelectListener(this);
        TabLayout tabLayout = findViewById(R.id.weather_location_tabLayout);
        tabLayout.addOnTabSelectedListener(select_listener);
    }

    public void setTabLayout() {
        JsonConfigManager configManager = (JsonConfigManager) new JsonManager(this, JsonManager.FileType.CONFIG).getDeserializedInstance();
        Map<String, JsonConfigManager.WeatherLocation> weatherLocation_map = configManager.getWeatherLocationMap();

        //タブの初期化
        TabLayout tabLayout = findViewById(R.id.weather_location_tabLayout);
        for (int n = tabLayout.getTabCount() - 1; n >= 1; n--) {
            tabLayout.removeTabAt(n);
        }

        if (!weatherLocation_map.isEmpty()) {
            for (String key : weatherLocation_map.keySet()) {
                TabLayout.Tab newTab = tabLayout.newTab();
                newTab.setText(key);
                tabLayout.addTab(newTab);
            }
        }
    }

    private void setNowLocationTab() {
        TabLayout tabLayout = findViewById(R.id.weather_location_tabLayout);

        FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        TabLayout.Tab now_location_tab = tabLayout.getTabAt(0);
        final Address[] address = {null};
        fusedLocationClient.requestLocationUpdates(getLocationRequest(),  new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                for (Location location : locationResult.getLocations()) {
                    Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                    try {
                        List<Address> addresse_list = geocoder.getFromLocation(
                                location.getLatitude(),
                                location.getLongitude(),
                                1
                        );
                        address[0] = addresse_list.get(0);
                    } catch (IOException e) {
                        address[0] = null;
                    }

                }
            }
        }, Looper.getMainLooper());

        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if (!isActive) {
                    this.cancel();
                    return;
                }

                WeatherHomeActivity.this.runOnUiThread(() -> {
                    if (address[0] == null) {
                        now_location_tab.setText("失敗");
                    }
                    else {
                        now_location_tab.setText("現在位置\n" + address[0].getAdminArea());
                    }
                });
            }
        };
        timer.schedule(task, 1000*10L);
    }

    @RequiresPermission(allOf = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION})
    private LocationRequest getLocationRequest() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    1000);
        }

        return new LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 0)
                .setMaxUpdates(1)
                .build();
    }
}