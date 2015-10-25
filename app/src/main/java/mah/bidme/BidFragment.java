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
import android.widget.Button;
import android.widget.ProgressBar;
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

import mah.bidme.CustomAdapter.ItemRecyclerAdapter;
import mah.bidme.model.Item;

/**
 * A simple {@link Fragment} subclass.
 */
public class BidFragment extends Fragment {
    /*private static String debug = "Debug";
    private int currBid;//Change to value of current bid.
    private int yourBid;//Change to value of current bid.
    private String itemName;*/
    private List<Item> listItem = new ArrayList<Item>();
    private Firebase mFirebase;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private static String LOG_TAG = "CardViewActivity";

    private TextView yourBidTextView;
    private ProgressBar progressBar;

    public BidFragment() {
        // Required empty public constructor
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get a reference to the right child in Firebase
        Utility.loggedInName = "Mario";
        mFirebase = Utility.myFirebaseRef.child("items");
        this.initListItem();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_bid, container, false);

        /*progressBar = (ProgressBar) v.findViewById(R.id.item_progressbar);
        progressBar.setIndeterminate(true);*/

        // Create the RecyclerView in order to display the Item cardview
        mRecyclerView = (RecyclerView) v.findViewById(R.id.item_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);


        mAdapter = new ItemRecyclerAdapter(listItem);
        mRecyclerView.setAdapter(mAdapter);
        //progressBar.setVisibility(View.GONE);

       /* final TextView itemName = (TextView) v.findViewById(R.id.item_name);
        final TextView currPrice = (TextView) v.findViewById(R.id.current_price);

        final TextView newPrice = (TextView) v.findViewById(R.id.your_price);*/

        //yourBidTextView = (TextView) v.findViewById(R.id.item_bid_text);
       /* Button addBidButton = (Button) v.findViewById(R.id.addBidButton);
        Button removeBidButton = (Button) v.findViewById(R.id.removeBidButton);
        Button checkBidButton = (Button) v.findViewById(R.id.checkBidButton);


        addBidButton.setOnClickListener(new BidItemListener());
        removeBidButton.setOnClickListener(new BidItemListener());
        checkBidButton.setOnClickListener(new BidItemListener());*/

        return v;
    }

    private void initListItem() {
        mFirebase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //Log.i(debug, "There are " + dataSnapshot.getChildrenCount() + " item posts");
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Item item = postSnapshot.getValue(Item.class);
                    //Log.i(debug, item.getTitle() + " - " + postSnapshot.getKey());
                    //progressBar.setVisibility(View.VISIBLE);
                    if(listItem.size() > 0){
                        //int positionItem = listItem.indexOf(item);

                        if(listItem.get(0).getId() == postSnapshot.getKey()){
                            listItem.set(0, item);
                        } else
                            listItem.add(item);
                    } else
                        listItem.add(item);

                    mAdapter.notifyDataSetChanged();
                }

            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });
    }

    /**
     * Private class that implements an OnClickListener that handles the two buttons
     */
  /*  private class BidItemListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.addBidButton:
                    addBidItem();
                    break;
                case R.id.removeBidButton:
                    removeBidItem();
                    break;
                case R.id.checkBidButton:
                    checkBidFirebase();
                    break;
            }
        }*/


    }
