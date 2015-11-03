package mah.bidme;


import android.annotation.TargetApi;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {

    private static final String TAG = "LoginFragment";
    // UI references.
    private EditText mUserView;
    private EditText mPinCodeView;
    private ImageView mAppName;

    Firebase firebaseReferens;
    long fireBasePin, mEnteredPin;
    private Button mSignInButton;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        DrawerLayout drawerLayout = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.fragment_login, container, false);
        firebaseReferens = Utility.myFirebaseRef;
        mUserView = (EditText) v.findViewById(R.id.username);
        mPinCodeView = (EditText) v.findViewById(R.id.pincode);
        mAppName = (ImageView) v.findViewById(R.id.app_name);

        mAppName.setImageResource(R.mipmap.bidme);


        firebaseReferens.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                fireBasePin = (long) dataSnapshot.child("pincode").getValue();

                mSignInButton.setEnabled(true);
                Toast.makeText(getContext(), "You can now enter the auction room!", Toast.LENGTH_SHORT).show();
                Log.i(TAG, "Value of data changed " + "Pincode: " + fireBasePin);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Log.i(TAG, "Value of data changed" + firebaseError.getMessage());
            }
        });


        mSignInButton = (Button) v.findViewById(R.id.sign_in_button);
        mSignInButton.setEnabled(false);

        mSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int n = 100000000;
                Random random = new Random();
                Utility.id = random.nextInt((n) + 100000);
                Utility.loggedInName = mUserView.getText().toString();
                String pinCode = mPinCodeView.getText().toString();
                try {
                    mEnteredPin = Long.parseLong(pinCode);

                } catch (NumberFormatException e) {
                    Toast.makeText(getContext(), "Enter pincode!", Toast.LENGTH_SHORT).show();
                }

                if (TextUtils.isEmpty(pinCode) || mEnteredPin != fireBasePin) {
                    mPinCodeView.setError("Invalid pincode");
                    Log.i(TAG, "Not Logged in");
                } else {
                    Log.i(TAG, "You are logged in");
                    Map<String, Object> userInfo = new HashMap<String, Object>();
                    userInfo.put("username", mUserView.getText().toString());
                    Firebase postRef = firebaseReferens.child("users").push();
                    Utility.loggedInName = postRef.getKey();
                    userInfo.put("id", Utility.loggedInName);
                    postRef.setValue(userInfo);

                    // Create a Toast to tell the user that he/she is logged in
                    Toast.makeText(getContext(), "You are logged in", Toast.LENGTH_SHORT).show();

                    // Static method for removing the Keyboard
                    Utility.removeKeyboard(getActivity(), mPinCodeView);

                    FragmentManager fm = getFragmentManager();
                    fm.beginTransaction()
                            .replace(R.id.fragment_container, new BidFragment())
                            .commit();
                }
            }
        });
        return v;
    }
}


