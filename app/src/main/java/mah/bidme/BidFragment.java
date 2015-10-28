package mah.bidme;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
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
    private List<Item> listItem;
    private Firebase mFirebase;

    private RecyclerView mRecyclerView;
    private ItemRecyclerAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private static String LOG_TAG = "CardViewActivity";

    private ProgressBar progressBar;

    public BidFragment() {
        // Required empty public constructor
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFirebase = Utility.myFirebaseRef.child("items");
        listItem = new ArrayList<Item>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_bid, container, false);

        setUpToolbar(v);

        progressBar = (ProgressBar) v.findViewById(R.id.item_progressbar);
        progressBar.setVisibility(v.VISIBLE);
        progressBar.setIndeterminate(true);

        // Create the RecyclerView in order to display the Item cardview
        mRecyclerView = (RecyclerView) v.findViewById(R.id.item_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        this.initListItem();
        mAdapter = new ItemRecyclerAdapter(listItem);
        mRecyclerView.setAdapter(mAdapter);

        return v;
    }

    /**
     * We retrieve the data from Firebase and save into a List<Item> in order to send to the Adapter
     */
    private void initListItem() {
        mFirebase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Item item = dataSnapshot.getValue(Item.class);

                if (item.getUpForSale()) {
                    listItem.add(item);
                }

                mAdapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Item item = dataSnapshot.getValue(Item.class);

                if (item.getUpForSale()) {
                    if (!listItem.isEmpty()) {
                        listItem.set(0, item);
                    } else {
                        listItem.add(item);
                    }
                } else {
                    listItem.remove(0);
                }

                mAdapter.notifyDataSetChanged();
                Log.i("BidFragment", "Currentprice is updated");
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });


        /*mFirebase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Item item = postSnapshot.getValue(Item.class);
                    //progressBar.setVisibility(View.VISIBLE);

                    if (listItem.contains(item)) {
                        mAdapter.updatePrice(item.getCurrentPrice());
                        Log.i("BidFragment", "Item exist");
                    }
                    listItem.add(item);
                    //mAdapter.swapList((ArrayList<Item>) listItem);
                    mAdapter.notifyDataSetChanged();
                }
                progressBar.setVisibility(View.GONE);
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });*/
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

    /**
     * Method for setting up the custom Toolbar for AddItemFragment
     */
    private void setUpToolbar(View view) {
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbarBid);
        //toolbar.inflateMenu(R.menu.add_item_menu);
        toolbar.setTitle("Bid");
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorTextIcons));
    }

    }
