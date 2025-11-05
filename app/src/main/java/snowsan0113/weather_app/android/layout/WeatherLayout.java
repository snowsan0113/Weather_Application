package snowsan0113.weather_app.android.layout;

public class WeatherLayout {
    private final String time;
    private final int weatherIcon;
    private final float tempMax;
    private final float tempMin;

    public WeatherLayout(String time, int weatherIcon, float tempMax, float tempMin) {
        this.time = time;
        this.weatherIcon = weatherIcon;
        this.tempMax = tempMax;
        this.tempMin = tempMin;
    }

    public String getTime() {
        return time;
    }

    public int getWeatherIconID() {
        return weatherIcon;
    }

    public float getTempMax() {
        return tempMax;
    }

    public float getTempMin() {
        return tempMin;
    }
}
