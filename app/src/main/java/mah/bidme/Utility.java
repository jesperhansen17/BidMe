package mah.bidme;

import android.app.Activity;
import android.content.Context;
import android.os.Vibrator;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import com.firebase.client.Firebase;

/**
 * Created by Kha on 13/10/15.
 */
public class Utility {
    //Since this is static it will be instansiated  at startup of the App
    //Use static for variables that you want to reach from anywhere in the app...
    public static int id;
    public static String loggedInName;
    public static Firebase myFirebaseRef = new Firebase("https://biddme.firebaseio.com/");

    /**
     * Static method for removing the keyboard
     * @param activity
     * @param view
     */
    public static void removeKeyboard(Activity activity, View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * Static method for vibrate the phone
     * @param activity Current activity
     * @param duration Duration of the vibration
     */
    public static void vibratePhone(Activity activity, int duration) {
        Vibrator vibrator = (Vibrator) activity.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(duration);
    }
}
