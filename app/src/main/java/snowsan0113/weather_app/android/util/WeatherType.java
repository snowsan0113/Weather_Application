package snowsan0113.weather_app.android.util;

import snowsan0113.weather_app.android.R;

public enum WeatherType {
    SUNNY(R.drawable.mark_tenki_hare),
    MOON(R.drawable.mark_tenki_moon),
    CLOUDY(R.drawable.mark_tenki_kumori),
    RAINY(R.drawable.mark_tenki_umbrella),
    SNOW(R.drawable.tenki_snow),
    THUNDER(R.drawable.mark_tenkiu_kaminari),
    UNKNOWN(R.drawable.ic_empty_icon);

    private int icon_id;

    WeatherType(int icon_id) {
        this.icon_id = icon_id;
    }

    public int getIconID() {
        return icon_id;
    }

    public static WeatherType getConvertType(String name) {
        if (name.contains("晴")) {
            return SUNNY;
        }
        else if (name.contains("曇") || name.contains("雲")) {
            return CLOUDY;
        }
        else if (name.contains("雨")) {
            return RAINY;
        }
        else if (name.contains("雪")) {
            return SNOW;
        }
        else if (name.contains("雷")) {
            return THUNDER;
        }

        return UNKNOWN;
    }
}
