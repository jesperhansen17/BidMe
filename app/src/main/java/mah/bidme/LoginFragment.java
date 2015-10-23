package mah.bidme;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {

    private static final String TAG = "LoginFragment";
    // UI references.
    private EditText mUserView;
    private EditText mPinCodeView;
    private View mProgressView;
    private View mLoginFormView;


    Firebase firebaseReferens;
    long fireBasePin;
    String fireBaseUser;
    ArrayList<String> users = new ArrayList<String>();


    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.fragment_login, container, false);
        firebaseReferens = Constants.myFirebaseRef;
        mLoginFormView = v.findViewById(R.id.login_form);
        mProgressView = v.findViewById(R.id.login_progress);
        mUserView = (EditText) v.findViewById(R.id.username);
        mPinCodeView = (EditText) v.findViewById(R.id.pincode);


        firebaseReferens.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                fireBasePin = (long) dataSnapshot.child("pincode").getValue();

                Log.i(TAG, "Value of data changed " + "Pincode: " + fireBasePin);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Log.i(TAG, "Value of data changed" + firebaseError.getMessage());
            }
        });


        Button SignInButton = (Button) v.findViewById(R.id.sign_in_button);
        SignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int n = 100000000;
                Random random = new Random();
                Constants.id = random.nextInt((n) + 100000);
                Constants.loggedInName = mUserView.getText().toString();
                String pinCode = mPinCodeView.getText().toString();
                long longPinCode = Long.parseLong(pinCode);

                if (TextUtils.isEmpty(pinCode) || longPinCode != fireBasePin) {
                    mPinCodeView.setError("Invalid pincode");
                    Log.i(TAG, "Not Logged in");

                } else if (Constants.loggedInName.equals(fireBaseUser)) {
                    mUserView.setError("This username is already taken");
                    Log.i(TAG, "Wrong username");

                } else {
                    Log.i(TAG, "You are logged in");
                    Map<String, Object> userInfo = new HashMap<String, Object>();
                    userInfo.put("Username", mUserView.getText().toString());
                    firebaseReferens.child("Users").child(UUID.randomUUID().toString()).setValue(userInfo);

                    FragmentManager fm = getFragmentManager();
                    fm.beginTransaction()
                            .replace(R.id.fragment_container, new MainMenuFragment())
                            .addToBackStack("")
                            .commit();
                }
            }
        });
        return v;
    }
}


