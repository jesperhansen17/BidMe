package mah.bidme;

import android.app.Activity;
import android.content.Context;
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

    // An static Utility method for removing the input Keyboard from the screen
    public static void removeKeyboard(Activity activity, View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);

    }
}
