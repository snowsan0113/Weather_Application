package snowsan0113.weather_app.android.manager.file;

import com.google.gson.annotations.SerializedName;

public class JsonConfigManager implements JsonManager.AppDeserializedFile {

    private WeatherLocation weather_location;

    public JsonConfigManager() {}

    public WeatherLocation getWeatherLocation() {
        return weather_location;
    }

    public static class WeatherLocation {
        private float latitude; //緯度
        private float longitude; //経度

        public WeatherLocation() {}

        public float getLatitude() {
            return latitude;
        }

        public float getLongitude() {
            return longitude;
        }
    }
}
