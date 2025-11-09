package snowsan0113.weather_app.android.listener.weather_search;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import snowsan0113.weather_app.android.R;

@SuppressLint({"NewApi", "LocalSuppress"})
public class WeatherSearchKeyListener implements View.OnKeyListener {

    private Activity activity;
    
    public WeatherSearchKeyListener(Activity activity) {
        this.activity = activity;
    }

    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent) {
        if (view.getId() == R.id.weather_search_editText) {
            if (keyEvent.getAction() == KeyEvent.ACTION_DOWN && keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                EditText edit_text = activity.findViewById(R.id.weather_search_editText);
                String text = edit_text.getText().toString();
                if (text.isEmpty()) {
                    Toast toast = new Toast(activity);
                    toast.setText("文字を入れてください");
                    toast.show();
                    return false;
                }

                Geocoder geocoder = new Geocoder(activity);
                try {
                    List<Address> address_list = geocoder.getFromLocationName(edit_text.getText().toString(), 20);
                    for (int n = 0; n < address_list.size(); n++) {
                        Address address = address_list.get(n);
                        createLocationButton(address);
                    }
                } catch (IOException e) {

                }
            }
        }
        return false;
    }
    
    private void createLocationButton(Address address) {
        LinearLayout result_layout = activity.findViewById(R.id.weather_search_resultLayout);
        result_layout.removeAllViews();

        FrameLayout frame = new FrameLayout(activity);
        LinearLayout.LayoutParams linear_pram = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        linear_pram.setMargins(0, 5, 0, 3);
        frame.setLayoutParams(linear_pram);
        frame.setBackgroundColor(Color.parseColor("#FFFFFF"));
        
        MaterialButton materialButton = new MaterialButton(activity);
        materialButton.setTag(address);
        materialButton.setOnClickListener(new WeatherSearchClickListener(activity));
        materialButton.setBackgroundColor(Color.WHITE);
        materialButton.setRippleColor(ColorStateList.valueOf(Color.LTGRAY));
        materialButton.setStateListAnimator(null);
        materialButton.setCornerRadius(0);
        frame.addView(materialButton);

        EditText edit_text = activity.findViewById(R.id.weather_search_editText);
        TextView name_text = new TextView(activity);
        name_text.setTextSize(30);
        name_text.setText(edit_text.getText().toString());
        TextView location_text = new TextView(activity);
        location_text.setTextSize(20);
        location_text.setText("（緯度" + address.getLatitude() + ",経度：" + address.getLongitude() + ")");

        LinearLayout linearLayout = new LinearLayout(activity);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.addView(name_text);
        linearLayout.addView(location_text);
        frame.addView(linearLayout);

        result_layout.addView(frame);
    }

}
