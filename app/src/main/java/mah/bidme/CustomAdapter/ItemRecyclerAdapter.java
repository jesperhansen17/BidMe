package mah.bidme.CustomAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mah.bidme.R;
import mah.bidme.Utility;
import mah.bidme.model.Item;

/**
 * Created by Kha on 24/10/15.
 */
public class ItemRecyclerAdapter extends RecyclerView.Adapter<ItemRecyclerAdapter.ItemViewHolder> {

    private List<Item> itemList;
    private static String debug = "Debug";
    private int currBid;//Change to value of current bid.
    private int yourBid;//Change to value of current bid.
    //private String itemName;
    //private List<Item> listItem = new ArrayList<Item>();
    private Firebase mFirebase;
    private Context mContext;

    public ItemRecyclerAdapter(List<Item> itemList) {
        this.itemList = itemList;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.card_view_item_row, parent, false);

        Utility.loggedInName = "Kevin";
        mFirebase = Utility.myFirebaseRef.child("items");
        mContext = parent.getContext();

        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder holder, int position) {
        final Item item = itemList.get(position);
        holder.vItemPicture.setImageBitmap(item.getImage());
        holder.vItemTitle.setText(item.getTitle());
        holder.vItemPrice.setText(Integer.toString(item.getCurrentPrice()) + " SEK");
        //holder.vItemBid.setText(item.getListBid());

        currBid = item.getCurrentPrice();
        yourBid = currBid;

        holder.vItemAddBid.setOnClickListener(new View.OnClickListener()      {
            @Override
            public void onClick(View v) {
                yourBid = yourBid + 5;
                holder.vItemYourBid.setText("" + yourBid + "");
                Log.i("Math:", "" + yourBid + "");
            }
        });

        holder.vItemRemoveBid.setOnClickListener(new View.OnClickListener()      {
            @Override
            public void onClick(View v) {
                if (currBid + 5 <= yourBid) {
                    yourBid = yourBid - 5;
                    holder.vItemYourBid.setText("" + yourBid + "");
                    Log.i("Math:", "" + yourBid + "");
                } else {
                    Toast.makeText(mContext, "You cant go lower than current bid!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        holder.vItemConfirmBid.setOnClickListener(new View.OnClickListener()      {
            @Override
            public void onClick(View v) {
                if (currBid < yourBid) {
                    //Send bid to database
                    Map<String, Object> bid = new HashMap<String, Object>();
                    bid.put(Utility.loggedInName, yourBid);
                    //Log.i(debug, listItem.get(0).toString());
                    mFirebase.child(item.getId() + "/currentPrice").setValue(yourBid);
                    mFirebase.child(item.getId() +"/bids").updateChildren(bid);
                    holder.vItemPrice.setText(Integer.toString(yourBid));
                    //notifyItemChanged(itemList.indexOf(item));
                    Log.i("Math:", "Create bid!");
                    Toast.makeText(mContext, "Your bid was accepted!", Toast.LENGTH_SHORT).show();
                    //add another 5 sec to the countdown.
                } else {
                    Toast.makeText(mContext, "This bid is lower or equal to current bid!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public void swapList(ArrayList<Item> listItem){
        listItem.clear();
        listItem.addAll(listItem);
        notifyDataSetChanged();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        protected ImageView vItemPicture;
        protected TextView vItemTitle;
        protected EditText vItemPrice;
        protected TextView vItemBid;
        protected TextView vItemYourBid;
        protected Button vItemAddBid;
        protected Button vItemRemoveBid;
        protected Button vItemConfirmBid;

        public ItemViewHolder(View itemView) {
            super(itemView);
            vItemPicture = (ImageView) itemView.findViewById(R.id.item_picture);
            vItemTitle = (TextView) itemView.findViewById(R.id.item_name);
            vItemPrice = (EditText) itemView.findViewById(R.id.item_price_text);
            vItemBid = (TextView) itemView.findViewById(R.id.item_bid_title);
            vItemYourBid = (TextView) itemView.findViewById(R.id.item_bid_text);
            vItemAddBid = (Button) itemView.findViewById(R.id.addBidButton);
            vItemRemoveBid = (Button) itemView.findViewById(R.id.removeBidButton);
            vItemConfirmBid = (Button) itemView.findViewById(R.id.checkBidButton);

        }
    }

    /**
     * Private class that implements an OnClickListener that handles the two buttons
     */
/*    private class BidItemListener implements View.OnClickListener {
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
        }
    }*/

   /* private void addBidItem() {
        yourBid = yourBid + 5;
        Log.i("Math:", "" + yourBid + "");
    }*/

   /* private void removeBidItem() {
        //Subtract 5
        //Makes sure you are above current bid.
        if (currBid + 5 <= yourBid) {
            yourBid = yourBid - 5;
            Log.i("Math:", "" + yourBid + "");
        } else {
            Toast.makeText(mContext, "You cant go lower than current bid!", Toast.LENGTH_SHORT).show();
        }
    }*/

  /*  private void checkBidFirebase() {

        //Create bid
        //Check if the bid is valid.
        if (currBid < yourBid) {
            //Send bid to database
            Map<String, Object> bid = new HashMap<String, Object>();
            bid.put(Utility.loggedInName, yourBid);
            //Log.i(debug, listItem.get(0).toString());
           *//* mFirebase.child(listItem.get(0).get("Title").toString() +"/Currentprice").setValue(yourBid);
            mFirebase.child(listItem.get(0).get("Title").toString() +"/Bids").updateChildren(bid);*//*
            Log.i("Math:", "Create bid!");
            Toast.makeText(mContext, "Your bid was accepted!", Toast.LENGTH_SHORT).show();
            //add another 5 sec to the countdown.
        } else {
            Toast.makeText(mContext, "This bid is lower or equal to current bid!", Toast.LENGTH_SHORT).show();
        }
    }*/
}
