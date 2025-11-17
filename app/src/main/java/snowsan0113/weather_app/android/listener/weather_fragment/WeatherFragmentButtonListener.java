package snowsan0113.weather_app.android.listener.weather_fragment;

import android.app.Activity;
import android.graphics.Color;
import android.location.Geocoder;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;

import snowsan0113.weather_app.android.R;
import snowsan0113.weather_app.android.fragment.WeatherFragment;
import snowsan0113.weather_app.android.manager.file.JsonConfigManager;
import snowsan0113.weather_app.android.manager.file.JsonManager;

public class WeatherFragmentButtonListener implements MaterialButtonToggleGroup.OnButtonCheckedListener {

    private WeatherFragment weatherFragment;

    public WeatherFragmentButtonListener(WeatherFragment weatherFragment) {
        this.weatherFragment = weatherFragment;
    }

    @Override
    public void onButtonChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {
        if (group.getId() == R.id.weather_date_button_group) {
            for (int n = 0; n < group.getChildCount(); n++) {
                group.getChildAt(n).setBackgroundColor(Color.parseColor("#c1c3cc"));
            }

            MaterialButton button = weatherFragment.getView().findViewById(checkedId);
            button.setBackgroundColor(Color.parseColor("#408EE9"));


            FragmentActivity activity = weatherFragment.getActivity();
            LayoutInflater layoutInflater = LayoutInflater.from(activity);
            View home_view = layoutInflater.inflate(R.layout.activity_weather_home, null);

            JsonConfigManager configManager = (JsonConfigManager) new JsonManager(activity, JsonManager.FileType.CONFIG).getDeserializedInstance();
            Map<String, JsonConfigManager.WeatherLocation> weatherLocation_map = configManager.getWeatherLocationMap();
            TabLayout tabLayout = home_view.findViewById(R.id.weather_location_tabLayout);
            JsonConfigManager.WeatherLocation weatherLocation = new ArrayList<>(weatherLocation_map.values()).get(tabLayout.getTabCount() - 1);
            if (checkedId == R.id.fewhour_button) {
                weatherFragment.setupFewHourLayout(3, weatherLocation.getLatitude(), weatherLocation.getLongitude());
            }
            else if (checkedId == R.id.todaytomorrow_button) {
                weatherFragment.setupTodayTomorrowLayout(1, weatherLocation.getLatitude(), weatherLocation.getLongitude());
            }
            else if (checkedId == R.id.fewday_button) {
                weatherFragment.setupFewDayLayout(1, weatherLocation.getLatitude(), weatherLocation.getLongitude());
            }
        }
    }

}
