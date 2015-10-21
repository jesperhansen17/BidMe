package mah.bidme;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
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
public class BidFragment extends Fragment {
    private static String debug = "Debug";
    private int currBid;//Change to value of current bid.
    private int yourBid;//Change to value of current bid.
    private String itemName;
    private List<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
    private Firebase mFirebase;

    public BidFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Get a reference to the right child in Firebase
        Constants.loggedInName = "Mario";
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
                Map<String, Object> item = new HashMap<String, Object>();
                for (DataSnapshot itemSnapshot: dataSnapshot.getChildren()) {
                    item = itemSnapshot.getValue(HashMap.class);
                    listItem.add((HashMap<String, Object>) item);
                    Log.i(debug, item.get("Price").toString());
                   /* while (item.get("Sold") == false){
                        itemName.setText(item.get("Title").toString());
                        currPrice.setText(item.get("Price").toString());
                    }*/
                    //listItem.add((HashMap<String, Object>) item);

                    itemName.setText(listItem.get(0).get("Title").toString());
                    currPrice.setText(listItem.get(0).get("Currentprice").toString());
                    currBid = Integer.parseInt(listItem.get(0).get("Price").toString());
                    yourBid = Integer.parseInt(listItem.get(0).get("Currentprice").toString());
                }
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
                newPrice.setText("" + yourBid + "");
                Log.i("Math:", "" + yourBid + "");
            }
        });

        //Click made to subtract 5
        subButt.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                subtraction();
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
            Map<String, Object> bid = new HashMap<String, Object>();
            bid.put(Constants.loggedInName, yourBid);
            //Log.i(debug, listItem.get(0).toString());
            mFirebase.child(listItem.get(0).get("Title").toString() +"/Currentprice").setValue(yourBid);
            mFirebase.child(listItem.get(0).get("Title").toString() +"/Bids").updateChildren(bid);
            Toast.makeText(getContext(), "Your bid was accepted!", Toast.LENGTH_SHORT).show();
            //add another 5 sec to the countdown.
        } else{
            Toast.makeText(getContext(), "This bid is lower or equal to current bid!", Toast.LENGTH_SHORT).show();
        }
    }
}
