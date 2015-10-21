package mah.bidme;


import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class BidFragment extends android.support.v4.app.Fragment {
    private static String debug = "Debug";
    private int currBid = 50;//Change to value of current bid.
    private int yourBid = 0;//Change to value of current bid.
    private String itemName;
    private Firebase mFirebase;

    public BidFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Get a reference to the right child in Firebase
        Constants.loggedInName = "Jesper Hansen";
        mFirebase = Constants.myFirebaseRef.child("Items");

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_bid, container, false);

        final TextView itemName = (TextView) v.findViewById(R.id.item_name);
        final TextView currPrice = (TextView) v.findViewById(R.id.current_price);

        final TextView newPrice = (TextView) v.findViewById(R.id.your_price);

        FloatingActionButton addButt = (FloatingActionButton) v.findViewById(R.id.addButton);
        FloatingActionButton subButt = (FloatingActionButton) v.findViewById(R.id.subButton);
        FloatingActionButton checkButt = (FloatingActionButton) v.findViewById(R.id.checkButton);

        mFirebase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
                Map<String, Object> item = new HashMap<String, Object>();
                for (DataSnapshot itemSnapshot: dataSnapshot.getChildren()) {
                    item = itemSnapshot.getValue(HashMap.class);
                    listItem.add((HashMap<String, Object>) item);
                    Log.i(debug, item.get("Price").toString());
                    //itemName.setText(item.get("Title").toString());
                }


                currPrice.setText(item.get("Price").toString());
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });

        //Click made to add 5
        addButt.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                addition();

                mFirebase.child("Bids").setValue(yourBid);
                newPrice.setText("" + yourBid + "");
                Log.i("Math:", "" + yourBid + "");
            }
        });

        //Click made to subtract 5
        subButt.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                subtraction();
                newPrice.setText("" + yourBid + "");
                Log.i("Math:", "" + yourBid + "");
            }
        });

        //Click made to place bid
        checkButt.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                check();
                Log.i("Math:", "Create bid!");
            }
        });

        return v;
    }

    private void addition(){
        yourBid = yourBid + 5;
    }
    private void subtraction(){
        //Subtract 5
        //Makes sure you are above current bid.
        if(currBid +5 <= yourBid) {
            yourBid = yourBid - 5;
        }else{
            Toast.makeText(getContext(), "You cant go lower than current bid!", Toast.LENGTH_SHORT).show();
        }
    }
    private void check(){

        //Create bid
        //Check if the bid is valid.
        if(currBid < yourBid){
            //Send bid to database
            Toast.makeText(getContext(), "Your bid was accepted!", Toast.LENGTH_SHORT).show();
            //add another 5 sec to the countdown.
        }else{
            Toast.makeText(getContext(), "This bid is lower or equal to current bid!", Toast.LENGTH_SHORT).show();
        }
    }
}
