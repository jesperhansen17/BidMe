package mah.bidme;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import mah.bidme.CustomAdapter.ItemRecyclerAdapter;
import mah.bidme.model.Item;

/**
 * A simple {@link Fragment} subclass.
 */
public class BidFragment extends Fragment {
    private static String debug = "Debug";
    private int currBid;//Change to value of current bid.
    private int yourBid;//Change to value of current bid.
    private String itemName;
    private List<Item> listItem = new ArrayList<Item>();
    private Firebase mFirebase;

    public BidFragment() {
        // Required empty public constructor
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get a reference to the right child in Firebase
        Constants.loggedInName = "Mario";
        mFirebase = Constants.myFirebaseRef.child("items");
        this.initListItem();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_bid, container, false);

        // Create the RecyclerView in order to display the Item cardview
        RecyclerView recList = (RecyclerView) v.findViewById(R.id.item_recycler_view);
        recList.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);


        ItemRecyclerAdapter itemRecyclerAdapter = new ItemRecyclerAdapter(listItem);
        recList.setAdapter(itemRecyclerAdapter);

       /* final TextView itemName = (TextView) v.findViewById(R.id.item_name);
        final TextView currPrice = (TextView) v.findViewById(R.id.current_price);

        final TextView newPrice = (TextView) v.findViewById(R.id.your_price);*/

        FloatingActionButton addButt = (FloatingActionButton) v.findViewById(R.id.addButton);
        FloatingActionButton subButt = (FloatingActionButton) v.findViewById(R.id.subButton);
        FloatingActionButton checkButt = (FloatingActionButton) v.findViewById(R.id.checkButton);

       /* //Click made to add 5
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
        }); */

        return v;
    }

    private void initListItem(){
        mFirebase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Map<String, Object> item = new HashMap<String, Object>();

                //Log.i(debug, "There are " + dataSnapshot.getChildrenCount() + " item posts");
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    Item item = postSnapshot.getValue(Item.class);
                    //Log.i(debug, item.getTitle() + " - " + postSnapshot.getKey());
                    listItem.add(item);
                    
                }

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });
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
    /*private void check(){

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
    }*/
}
