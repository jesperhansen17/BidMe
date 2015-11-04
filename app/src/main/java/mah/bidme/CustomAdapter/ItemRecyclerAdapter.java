package mah.bidme.CustomAdapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;

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
    private int currentPrice;
    private int yourBid;
    private Firebase mFirebase;
    private Context mContext;

    /**
     * Class constructor
     * @param itemList
     */
    public ItemRecyclerAdapter(List<Item> itemList) {
        this.itemList = itemList;
    }

    /**
     * Inflate the view with the layout xml and connect the fragment with firebase database
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.card_view_item_row, parent, false);

        mFirebase = Utility.myFirebaseRef.child("items");
        mContext = parent.getContext();

        return new ItemViewHolder(itemView);
    }

    /**
     * For each item available in the list, we set the picture, title, price and bid
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(final ItemViewHolder holder, int position) {
        final Item item = itemList.get(position);
        holder.vItemPicture.setImageBitmap(Utility.getPhotoImage(item.getImage()));
        holder.vItemTitle.setText(item.getTitle());
        holder.vItemPrice.setText(Integer.toString(item.getCurrentPrice()) + " SEK");
        holder.vItemYourBid.setText("Bid");

        if(getItemCount() == 0){
            Toast.makeText(mContext, "No items on sale!", Toast.LENGTH_SHORT).show();
        }

        currentPrice = item.getCurrentPrice();
        yourBid = currentPrice;

        holder.vItemAddBid.setOnClickListener(new View.OnClickListener()      {
            @Override
            public void onClick(View v) {
                yourBid = yourBid + 5;
                holder.vItemYourBid.setText(Integer.toString(yourBid));
                Log.i("Math:", "" + yourBid + "");
            }
        });

        holder.vItemRemoveBid.setOnClickListener(new View.OnClickListener()      {
            @Override
            public void onClick(View v) {
                if (currentPrice + 5 <= yourBid) {
                    yourBid = yourBid - 5;
                    holder.vItemYourBid.setText(Integer.toString(yourBid));
                    Log.i("Math:", "" + yourBid + "");
                } else {
                    Toast.makeText(mContext, "You cant go lower than current bid!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        holder.vItemConfirmBid.setOnClickListener(new View.OnClickListener()      {
            @Override
            public void onClick(View v) {
                if (currentPrice < yourBid) {
                    Map<String, Object> bid = new HashMap<String, Object>();
                    bid.put(Utility.loggedInName, yourBid);
                    mFirebase.child(item.getId() + "/currentPrice").setValue(yourBid);
                    mFirebase.child(item.getId() +"/bids").updateChildren(bid);
                    holder.vItemPrice.setText(Integer.toString(item.getCurrentPrice()) + " SEK");
                    yourBid = item.getCurrentPrice();
                    Toast.makeText(mContext, "Your bid was accepted!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mContext, "This bid is lower or equal to current bid!", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    /**
     * Get the number of the item list
     * @return int size
     */
    @Override
    public int getItemCount() {
        return itemList.size();
    }

   /* public void updatePrice(int currentPrice) {
        this.currentPrice = currentPrice;
    }*/

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        protected ImageView vItemPicture;
        protected TextView vItemTitle;
        protected TextView vItemPrice;
        protected TextView vItemYourBid;
        protected Button vItemAddBid;
        protected Button vItemRemoveBid;
        protected Button vItemConfirmBid;

        public ItemViewHolder(View itemView) {
            super(itemView);
            vItemPicture = (ImageView) itemView.findViewById(R.id.item_picture);
            vItemTitle = (TextView) itemView.findViewById(R.id.item_name);
            vItemPrice = (TextView) itemView.findViewById(R.id.item_price_text);
            vItemYourBid = (TextView) itemView.findViewById(R.id.item_bid_text);
            vItemAddBid = (Button) itemView.findViewById(R.id.addBidButton);
            vItemRemoveBid = (Button) itemView.findViewById(R.id.removeBidButton);
            vItemConfirmBid = (Button) itemView.findViewById(R.id.checkBidButton);

        }
    }
}
