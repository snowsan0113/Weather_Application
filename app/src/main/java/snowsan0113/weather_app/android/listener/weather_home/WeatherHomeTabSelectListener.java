package snowsan0113.weather_app.android.listener.weather_home;

import android.app.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.tabs.TabLayout;

import java.util.Map;

import snowsan0113.weather_app.android.R;
import snowsan0113.weather_app.android.activity.WeatherHomeActivity;
import snowsan0113.weather_app.android.fragment.WeatherFragment;
import snowsan0113.weather_app.android.manager.file.JsonConfigManager;
import snowsan0113.weather_app.android.manager.file.JsonManager;

public class WeatherHomeTabSelectListener implements TabLayout.OnTabSelectedListener {

    private final WeatherHomeActivity activity;
    private final JsonConfigManager configManager;

    public WeatherHomeTabSelectListener(WeatherHomeActivity activity) {
        this.activity = activity;
        this.configManager = (JsonConfigManager) new JsonManager(activity, JsonManager.FileType.CONFIG).getDeserializedInstance();
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        if (tab.getPosition() >= 1) {
            Map<String, JsonConfigManager.WeatherLocation> locMap = configManager.getWeatherLocationMap();
            JsonConfigManager.WeatherLocation weatherLocation = locMap.get(tab.getText().toString());

            FragmentManager fragmentManager = activity.getSupportFragmentManager();
            WeatherFragment weatherFragment = (WeatherFragment) fragmentManager.findFragmentById(R.id.weather_fragment_view);
            weatherFragment.setupFewHourLayout(3, weatherLocation.getLatitude(), weatherLocation.getLongitude());
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
