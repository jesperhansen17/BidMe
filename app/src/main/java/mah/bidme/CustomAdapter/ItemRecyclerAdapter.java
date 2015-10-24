package mah.bidme.CustomAdapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import mah.bidme.R;
import mah.bidme.model.Item;

/**
 * Created by Kha on 24/10/15.
 */
public class ItemRecyclerAdapter extends RecyclerView.Adapter<ItemRecyclerAdapter.ItemViewHolder> {

    private List<Item> itemList;

    public ItemRecyclerAdapter(List<Item> itemList) {
        this.itemList = itemList;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.card_view_item_row, parent, false);

        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        Item item = itemList.get(position);
        holder.vItemPicture.setImageBitmap(item.getImage());
        holder.vItemTitle.setText(item.getTitle());
        holder.vItemPrice.setText(Integer.toString(item.getCurrentPrice()) + " SEK");
        //holder.vItemBid.setText(item.getListBid());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        protected ImageView vItemPicture;
        protected TextView vItemTitle;
        protected TextView vItemPrice;
        protected TextView vItemBid;

        public ItemViewHolder(View itemView) {
            super(itemView);
            vItemPicture = (ImageView) itemView.findViewById(R.id.item_picture);
            vItemTitle = (TextView) itemView.findViewById(R.id.item_name);
            vItemPrice = (TextView) itemView.findViewById(R.id.item_price_text);
            vItemBid = (TextView) itemView.findViewById(R.id.item_bid_text);
        }
    }
}
