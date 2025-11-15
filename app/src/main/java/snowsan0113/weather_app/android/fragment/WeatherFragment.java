package snowsan0113.weather_app.android.fragment;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.button.MaterialButtonToggleGroup;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import snowsan0113.weather_app.android.R;
import snowsan0113.weather_app.android.activity.WeatherHomeActivity;
import snowsan0113.weather_app.android.api.OpenWeatherAPI;
import snowsan0113.weather_app.android.layout.FewDayAdapter;
import snowsan0113.weather_app.android.layout.WeatherLayout;
import snowsan0113.weather_app.android.layout.FewHourAdapter;
import snowsan0113.weather_app.android.listener.weather_fragment.WeatherFragmentButtonListener;
import snowsan0113.weather_app.android.util.WeatherType;

public class WeatherFragment extends Fragment implements AppFragment {

    private final Map<String, List<WeatherLayout>> fewHourMap = new HashMap<>();
    private final Map<String, List<WeatherLayout>> todayTomorrowMap = new HashMap<>();
    private final Map<String, List<WeatherLayout>> fewDayMap = new HashMap<>();
    private View root_view;

    public WeatherFragment() {
        // Required empty public constructor
    }

    public static WeatherFragment newInstance() {
        return new WeatherFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_weather, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        this.root_view = view;
        initFragment();
    }
    
    @Override
    public void initFragment() {
        setupFewHourLayout(0, 35.652799F, 139.745367F);
        
        //listener
        MaterialButtonToggleGroup buttonGroup = root_view.findViewById(R.id.weather_date_button_group);
        buttonGroup.addOnButtonCheckedListener(new WeatherFragmentButtonListener(this));
    }

    /**
     * 数時間天気のレイアウトを設定する関数
     * @param get_hour 何時間おきに取得するか（0以下の場合は、3時間おきになります。）
     * @param lat 取得したい緯度
     * @param lon 取得したい経度
     */
    public void setupFewHourLayout(int get_hour, float lat, float lon) {
        if (get_hour <= 0) {
            get_hour = 3; //デフォルトの3時間用
            Log.w(getClass().getSimpleName(), "Arguments less than 0 hours cannot be used. 3 hour will be used.");
        }

        List<WeatherLayout> fewHourList = new ArrayList<>();
        for (int n = 0; n < 24; n+=get_hour) { //24時間先まで追加
            WeatherLayout weatherLayout = new WeatherLayout(
                    LocalDateTime.now(),
                    WeatherType.UNKNOWN,
                    0.0f,
                    0.0f
            );
            fewHourList.add(weatherLayout);
        }

        int finalGet_hour = get_hour;
        new Thread(() -> { //ネットワークはメインスレッドできないので、別スレッドにする
            LocalDateTime localDateTime = LocalDateTime.now(ZoneId.systemDefault()); //現在時刻
            OpenWeatherAPI openWeatherAPI = OpenWeatherAPI.getInstance(getActivity(), lat, lon); //APIから取得

            int i = 0;
            for (OpenWeatherAPI.WeatherList weatherList : openWeatherAPI.getWeatherList()) {
                List<OpenWeatherAPI.WeatherList.Weather> weather = weatherList.getWeather();
                OpenWeatherAPI.WeatherList.Weather first_weather = weather.get(0);
                OpenWeatherAPI.WeatherList.Main main = weatherList.getMain();
                LocalDateTime api_weather_time = weatherList.getLocalDateTime();
                int diff_hour = Math.abs(api_weather_time.getHour() - localDateTime.getHour());
                int sub_hour = Math.min(diff_hour, 24 - diff_hour);

                if (sub_hour <= 1) {
                    if (i >= 24 / finalGet_hour) {
                        break;
                    }

                    WeatherLayout weatherLayout = fewHourList.get(i);
                    weatherLayout.setTime(api_weather_time);
                    weatherLayout.setWeatherIconID(WeatherType.getConvertType(first_weather.getDescription()).getIconID());
                    weatherLayout.setTempMax((float) main.getTempMax(false));
                    weatherLayout.setTempMin((float) main.getTempMin(false));
                    i++;
                    localDateTime = localDateTime.plusHours(finalGet_hour);
                }
            }


            getActivity().runOnUiThread(() -> {
                RecyclerView weatherView = root_view.findViewById(R.id.weather_recyclerView);
                weatherView.removeAllViews();
                weatherView.setLayoutManager(new LinearLayoutManager(root_view.getContext(), LinearLayoutManager.HORIZONTAL, false));
                weatherView.setAdapter(new FewHourAdapter(fewHourList));

                try {
                    List<Address> address = new Geocoder(getActivity()).getFromLocation(lat, lon, 5);
                    fewHourMap.put(address.get(0).getAdminArea(), fewHourList);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });

        }).start();
    }

    public void setupFewDayLayout(int get_day, float lat, float lon) {
        List<WeatherLayout> fewDayList = new ArrayList<>();
        for (int n = 0; n < 5; n+=get_day) {
            WeatherLayout weatherLayout = new WeatherLayout(
                    LocalDateTime.now(),
                    WeatherType.UNKNOWN,
                    0.0f,
                    0.0f
            );
            fewDayList.add(weatherLayout);
        }

        int finalGet_day = get_day;
        new Thread(() -> { //ネットワークはメインスレッドできないので、別スレッドにする
            LocalDateTime localDateTime = LocalDateTime.now(ZoneId.systemDefault()); //現在時刻
            OpenWeatherAPI openWeatherAPI = OpenWeatherAPI.getInstance(getActivity(), lat, lon); //APIから取得

            int i = 0;
            for (OpenWeatherAPI.WeatherList weatherList : openWeatherAPI.getWeatherList()) {
                if (i == 5) {
                    break;
                }

                List<OpenWeatherAPI.WeatherList.Weather> weather = weatherList.getWeather();
                OpenWeatherAPI.WeatherList.Weather first_weather = weather.get(0);
                OpenWeatherAPI.WeatherList.Main main = weatherList.getMain();
                LocalDateTime api_weather_time = weatherList.getLocalDateTime();

                WeatherLayout weatherLayout = fewDayList.get(i);

                if (api_weather_time.getDayOfMonth() == localDateTime.getDayOfMonth()) {
                    weatherLayout.setTime(api_weather_time);
                    weatherLayout.setWeatherIconID(WeatherType.getConvertType(first_weather.getDescription()).getIconID());
                    weatherLayout.setTempMax((float) main.getTempMax(false));
                    weatherLayout.setTempMin((float) main.getTempMin(false));
                    localDateTime = localDateTime.plusDays(finalGet_day);
                    i++;
                }

            }


            getActivity().runOnUiThread(() -> {
                RecyclerView weatherView = root_view.findViewById(R.id.weather_recyclerView);
                weatherView.removeAllViews();
                weatherView.setLayoutManager(new LinearLayoutManager(root_view.getContext(), LinearLayoutManager.HORIZONTAL, false));
                weatherView.setAdapter(new FewDayAdapter(fewDayList));

                try {
                    List<Address> address = new Geocoder(getActivity()).getFromLocation(lat, lon, 5);
                    fewDayMap.put(address.get(0).getAdminArea(), fewDayList);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });

        }).start();
    }
}