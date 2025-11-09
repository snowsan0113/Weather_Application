package snowsan0113.weather_app.android.listener.weather_home;

import android.app.Activity;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;

import snowsan0113.weather_app.android.R;
import snowsan0113.weather_app.android.activity.WeatherSearchActivity;

public class WeatherHomeClickListener implements View.OnClickListener {

    private Activity activity;

    public WeatherHomeClickListener(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.menu_button) {

        }
        else if (view.getId() == R.id.search_button) {
            Intent intent = new Intent(activity, WeatherSearchActivity.class);
            activity.startActivity(intent);
        }
    }

}
