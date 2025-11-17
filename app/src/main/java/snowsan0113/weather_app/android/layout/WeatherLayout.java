package snowsan0113.weather_app.android.layout;

import org.jetbrains.annotations.Nullable;

import java.time.LocalDateTime;

import snowsan0113.weather_app.android.util.WeatherType;

public class WeatherLayout {
    private LocalDateTime time;
    private int weatherIcon;
    private String weather;
    private float tempMax;
    private float tempMin;

    public WeatherLayout(LocalDateTime time, WeatherType type, float tempMax, float tempMin) {
        this(time, type.getIconID(), tempMax, tempMin);
    }

    public WeatherLayout(LocalDateTime time, int weatherIcon, float tempMax, float tempMin) {
        this(time, weatherIcon, "", tempMax, tempMin);
    }

    public WeatherLayout(LocalDateTime time, int weatherIcon, String weather, float tempMax, float tempMin) {
        this.time = time;
        this.weatherIcon = weatherIcon;
        this.weather = weather;
        this.tempMax = tempMax;
        this.tempMin = tempMin;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public int getWeatherIconID() {
        return weatherIcon;
    }

    public void setWeatherIconID(int id) {
        this.weatherIcon = id;
    }

    public float getTempMax() {
        return tempMax;
    }

    public void setTempMax(float max) {
        this.tempMax = max;
    }

    public float getTempMin() {
        return tempMin;
    }

    public void setTempMin(float min) {
        this.tempMin = min;
    }

    @Nullable
    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }
}
