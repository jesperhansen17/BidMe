package mah.bidme;


import android.annotation.TargetApi;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.ArrayList;
import java.util.List;

import mah.bidme.CustomAdapter.ItemRecyclerAdapter;
import mah.bidme.CustomAdapter.ShowItemRecyclerAdapter;
import mah.bidme.model.Item;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShowItems extends Fragment {
    private List<Item> mListItem;
    private Firebase mFirebase;
    private RecyclerView mRecyclerView;
    private ShowItemRecyclerAdapter mShowItemRecyclerAdapter;
    private RecyclerView.LayoutManager mRecyclerLayoutManager;

    public ShowItems() {
        // Required empty public constructor
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFirebase = Utility.myFirebaseRef.child("items");
        mListItem = new ArrayList<Item>();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_show_items, container, false);

        // Sets up the Toolbar
        setupToolbar(view);

        // Download Items from Firebase to an ArrayList
        connectToFirebase();

        // Connect to all XML items
        mRecyclerView = (RecyclerView) view.findViewById(R.id.show_item_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mRecyclerLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mRecyclerLayoutManager);

        // Specify an adapter
        mShowItemRecyclerAdapter = new ShowItemRecyclerAdapter(mListItem);
        mRecyclerView.setAdapter(mShowItemRecyclerAdapter);
        return view;
    }

    private void connectToFirebase() {
        mFirebase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Item item = dataSnapshot.getValue(Item.class);

                // Add only the users Items to the List for showing in the GUI
                if (item.getIdSeller().equals(Utility.loggedInName)) {
                    mListItem.add(item);
                }

                mShowItemRecyclerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Item item = dataSnapshot.getValue(Item.class);

                for (int i = 0; i < mListItem.size(); i++) {
                    if (mListItem.get(i).getId().equals(item.getId())) {
                        mListItem.remove(i);
                        mShowItemRecyclerAdapter.notifyDataSetChanged();
                        Log.i("ShowItems", dataSnapshot.child("title").getValue() + " is removed.");
                    }
                }
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
     * Method for setting up the custom Toolbar
     * @param view The view that holds all GUI element
     */
    @TargetApi(21)
    private void setupToolbar(View view) {
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbarShowItems);

        final DrawerLayout drawerLayout = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);

        toolbar.setTitle("Show added items");
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorTextIcons));

        toolbar.setElevation(10);

        toolbar.setNavigationIcon(R.drawable.ic_menu_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open the Navigation drawer from the left
                drawerLayout.openDrawer(Gravity.LEFT);
            }
        });
    }


}
