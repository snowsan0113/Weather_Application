package snowsan0113.weather_app.android.listener.weather_fragment;

import android.app.Activity;
import android.graphics.Color;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonToggleGroup;

import snowsan0113.weather_app.android.R;
import snowsan0113.weather_app.android.fragment.WeatherFragment;

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

            if (checkedId == R.id.fewhour_button) {

            }
            else if (checkedId == R.id.todaytomorrow_button) {

            }
            else if (checkedId == R.id.fewday_button) {

            }
        }
    }

}
