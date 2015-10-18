package mah.bidme;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.firebase.client.Firebase;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Firebase.setAndroidContext(this);
        setContentView(R.layout.activity_main);

        if (findViewById(R.id.fragment_container) != null){
            if(savedInstanceState != null){
                return;
            }
        }

        MainMenuFragment mainMenuFragment = new MainMenuFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, mainMenuFragment).commit();

    }
}
