package snowsan0113.weather_app.android.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import snowsan0113.weather_app.android.R;
import snowsan0113.weather_app.android.layout.WeatherLayout;
import snowsan0113.weather_app.android.layout.FewHourAdapter;

public class WeatherFragment extends Fragment {

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
        List<WeatherLayout> fewHourList = new ArrayList<>();
        for (int n = 0; n < 24; n++) {
            fewHourList.add(new WeatherLayout(String.valueOf(n), R.drawable.mark_tenki_hare, 30.0f, 20.0f));
        }
        RecyclerView weatherView = view.findViewById(R.id.weather_recyclerView);
        weatherView.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false));
        weatherView.setAdapter(new FewHourAdapter(fewHourList));
    }
}