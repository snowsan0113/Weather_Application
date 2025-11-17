package snowsan0113.weather_app.android.layout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

import snowsan0113.weather_app.android.R;

public class TodayTomorrowAdapter extends RecyclerView.Adapter<TodayTomorrowAdapter.TodayTomorrowHolder> {

    private List<WeatherLayout> todaytomorrowList;

    public TodayTomorrowAdapter(List<WeatherLayout> fewhourList) {
        this.todaytomorrowList = fewhourList;
    }

    @NonNull
    @Override
    public TodayTomorrowAdapter.TodayTomorrowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_todaytomorrow, parent, false);
        return new TodayTomorrowAdapter.TodayTomorrowHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TodayTomorrowAdapter.TodayTomorrowHolder holder, int position) {
        WeatherLayout weatherLayout = todaytomorrowList.get(position);
        holder.timeText.setText(String.valueOf(weatherLayout.getTime().getDayOfMonth() + "(" + weatherLayout.getTime().getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.JAPAN) + ")"));
        holder.weatherText.setText(weatherLayout.getWeather());
        holder.weatherIcon.setImageResource(weatherLayout.getWeatherIconID());
        holder.tempMaxText.setText(weatherLayout.getTempMax() + "℃");
        holder.tempMinText.setText(weatherLayout.getTempMin() + "℃");
    }

    @Override
    public int getItemCount() {
        return todaytomorrowList.size();
    }

    public static class TodayTomorrowHolder extends RecyclerView.ViewHolder {
        private final TextView timeText;
        private final TextView weatherText;
        private final TextView tempMaxText, tempMinText;
        private final ImageView weatherIcon;

        public TodayTomorrowHolder(@NonNull View itemView) {
            super(itemView);
            this.timeText = itemView.findViewById(R.id.todaytomorrow_dateText);
            this.weatherText = itemView.findViewById(R.id.todaytomorrow_weatherText);
            this.weatherIcon = itemView.findViewById(R.id.todaytomorrow_weatherIcon);
            this.tempMaxText = itemView.findViewById(R.id.todaytomorrow_maxTemp);
            this.tempMinText = itemView.findViewById(R.id.todaytomorrow_minTemp);
        }
    }

}
