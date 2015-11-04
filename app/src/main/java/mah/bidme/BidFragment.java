package mah.bidme;


import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
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
    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private AlertDialog.Builder mBuilder;

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

        mBuilder = new AlertDialog.Builder(this.getContext());

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

                if (item.getUpForSale())
                    listItem.add(item);

                mAdapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Item item = dataSnapshot.getValue(Item.class);


                if (item.getUpForSale())
                    if (!listItem.isEmpty()){
                        listItem.set(0, item);
                    } else
                        listItem.add(item);

                if(item.isSold()){
                    if (item.getIdBuyer() == Utility.loggedInName){
                        mBuilder.setTitle("Congratulations!").setMessage("You won the auction for the item : \n\n"
                                + "Title: " + item.getTitle()
                                + "\nFinal price: " + item.getCurrentPrice());
                    } else{
                        mBuilder.setTitle("Oops...").setMessage("You missed the item : \n\n"
                                + "Title: " + item.getTitle()
                                + "\nFinal price: " + item.getCurrentPrice());
                    }

                    mBuilder.setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.dismiss();
                                }
                            });
                    AlertDialog alert = mBuilder.create();
                    alert.show();
                }

                /*Utility.vibratePhone(getActivity(), 100);*/
                mAdapter.notifyDataSetChanged();
/*
                Log.i("BidFragment", "Currentprice is updated");
*/
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
    }

    /**
     * Method for setting up the custom Toolbar for AddItemFragment
     */
    @TargetApi(21)
    private void setUpToolbar(View view) {
        toolbar= (Toolbar) view.findViewById(R.id.toolbarBid);

        final DrawerLayout drawerLayout = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);

        toolbar.setTitle("Bid");
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorTextIcons));

        toolbar.setElevation(21);

        toolbar.setNavigationIcon(R.drawable.ic_menu_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(Gravity.LEFT);
            }
        });
    }
}
