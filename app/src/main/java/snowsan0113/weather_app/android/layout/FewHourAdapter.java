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

public class FewHourAdapter extends RecyclerView.Adapter<FewHourAdapter.FewHourHolder> {

    private List<WeatherLayout> fewhourList;

    public FewHourAdapter(List<WeatherLayout> fewhourList) {
        this.fewhourList = fewhourList;
    }

    @NonNull
    @Override
    public FewHourHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_fewhour, parent, false);
        return new FewHourHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FewHourHolder holder, int position) {
        WeatherLayout weatherLayout = fewhourList.get(position);
        holder.timeText.setText(weatherLayout.getTime());
        holder.weatherIcon.setImageResource(weatherLayout.getWeatherIconID());
        holder.tempMaxText.setText(weatherLayout.getTempMax() + "℃");
        holder.tempMinText.setText(weatherLayout.getTempMin() + "℃");
    }

    @Override
    public int getItemCount() {
        return fewhourList.size();
    }

    public static class FewHourHolder extends RecyclerView.ViewHolder {
        private final TextView timeText;
        private final TextView tempMaxText, tempMinText;
        private final ImageView weatherIcon;

        public FewHourHolder(@NonNull View itemView) {
            super(itemView);
            this.timeText = itemView.findViewById(R.id.fewhour_datetext);
            this.weatherIcon = itemView.findViewById(R.id.fewhour_weatherIcon);
            this.tempMaxText = itemView.findViewById(R.id.fewhour_maxTemp);
            this.tempMinText = itemView.findViewById(R.id.fewhour_minTemp);
        }
    }
}
