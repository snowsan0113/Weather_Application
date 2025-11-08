package snowsan0113.weather_app.android.util;

import android.app.Activity;

public class DialogUtility {

    private static DialogUtility instance;
    private Activity activity;

    public DialogUtility(Activity activity) {
        this.activity = activity;
    }



    public static DialogUtility getInstance(Activity activity) {
        if (instance == null) instance = new DialogUtility(activity);
        return instance;
    }
}
