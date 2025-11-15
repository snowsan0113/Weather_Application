package snowsan0113.weather_app.android.layout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import snowsan0113.weather_app.android.R;

public class FewDayAdapter extends RecyclerView.Adapter<FewDayAdapter.FewDayHolder> {

    private List<WeatherLayout> fewdayList;

    public FewDayAdapter(List<WeatherLayout> fewdayList) {
        this.fewdayList = fewdayList;
    }

    @NonNull
    @Override
    public FewDayAdapter.FewDayHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_fewday, parent, false);
        return new FewDayAdapter.FewDayHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FewDayAdapter.FewDayHolder holder, int position) {
        WeatherLayout weatherLayout = fewdayList.get(position);
        holder.timeText.setText(weatherLayout.getTime().getDayOfMonth() + "日");
        holder.weatherIcon.setImageResource(weatherLayout.getWeatherIconID());
        holder.tempMaxText.setText(weatherLayout.getTempMax() + "℃");
        holder.tempMinText.setText(weatherLayout.getTempMin() + "℃");
    }

    @Override
    public int getItemCount() {
        return fewdayList.size();
    }

    public static class FewDayHolder extends RecyclerView.ViewHolder {
        private final TextView timeText;
        private final TextView tempMaxText, tempMinText;
        private final ImageView weatherIcon;

        public FewDayHolder(@NonNull View itemView) {
            super(itemView);
            this.timeText = itemView.findViewById(R.id.fewday_datetext);
            this.weatherIcon = itemView.findViewById(R.id.fewday_weatherIcon);
            this.tempMaxText = itemView.findViewById(R.id.fewday_maxTemp);
            this.tempMinText = itemView.findViewById(R.id.fewday_minTemp);
        }
    }

}
