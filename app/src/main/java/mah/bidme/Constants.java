package mah.bidme;

import com.firebase.client.Firebase;

/**
 * Created by Kha on 13/10/15.
 */
public class Constants {
    //Since this is static it will be instansiated  at startup of the App
    //Use static for variables that you want to reach from anywhere in the app...
    public static long id;
    public static String loggedInName;
    public static Firebase myFirebaseRef = new Firebase("https://biddme.firebaseio.com/");


}
