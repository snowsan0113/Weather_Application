package snowsan0113.weather_app.android.activity;

import android.os.Bundle;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import snowsan0113.weather_app.android.R;
import snowsan0113.weather_app.android.listener.weather_search.WeatherSearchKeyListener;

public class WeatherSearchActivity extends AppCompatActivity implements AppActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_weather_search);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initActivity();
    }

    @Override
    public void initActivity() {
        EditText editText = findViewById(R.id.weather_search_editText);
        editText.setOnKeyListener(new WeatherSearchKeyListener(this));
    }
}