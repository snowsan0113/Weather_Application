package snowsan0113.weather_app.android.listener.weather_search;

import android.app.Activity;
import android.app.AlertDialog;
import android.location.Address;
import android.view.View;

import com.google.android.material.button.MaterialButton;
import com.google.gson.JsonObject;

import snowsan0113.weather_app.android.manager.file.JsonManager;
import snowsan0113.weather_app.android.util.DialogUtility;

public class WeatherSearchClickListener implements View.OnClickListener {

    private Activity activity;

    public WeatherSearchClickListener(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onClick(View view) {
        if ((view instanceof MaterialButton) && view.getTag() instanceof Address) {
            Address address = (Address) view.getTag();
            AlertDialog builder = new AlertDialog.Builder(activity)
                    .setMessage("登録しますか？")
                    .setPositiveButton("はい", (dialogInterface, i) -> {
                        JsonManager json = new JsonManager(activity, JsonManager.FileType.CONFIG);
                        JsonObject root_obj = json.getRawElement().getAsJsonObject();
                        JsonObject loc_obj = root_obj.getAsJsonObject("weather_location");
                        JsonObject new_loc = new JsonObject();
                        new_loc.addProperty("latitude", address.getLatitude());
                        new_loc.addProperty("longitude", address.getLongitude());
                        loc_obj.add(address.getAdminArea(), new_loc);
                        json.updateJson();
                        activity.finish();
                    })
                    .setNegativeButton("いいえ", (dialogInterface, i) -> {
                        dialogInterface.cancel();
                    }).create();
            builder.show();
        }
    }

}
