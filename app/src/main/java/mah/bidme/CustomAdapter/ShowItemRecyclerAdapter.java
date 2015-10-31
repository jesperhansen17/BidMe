package mah.bidme.CustomAdapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.client.Firebase;

import java.util.List;

import mah.bidme.R;
import mah.bidme.Utility;
import mah.bidme.model.Item;

/**
 * Created by Jesper Hansen on 2015-10-30.
 */
public class ShowItemRecyclerAdapter extends RecyclerView.Adapter<ShowItemRecyclerAdapter.ItemViewHolder> {
    private List<Item> mListItem;
    private Firebase mFirebase;

    public ShowItemRecyclerAdapter() {
        // Empty Constructor
    }

    public ShowItemRecyclerAdapter(List<Item> listItem) {
        this.mListItem = listItem;
    }


    @Override
    public ShowItemRecyclerAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View userItemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_show_item, parent, false);
        mFirebase = Utility.myFirebaseRef.child("items");

        return new ItemViewHolder(userItemView);
    }

    @Override
    public void onBindViewHolder(ShowItemRecyclerAdapter.ItemViewHolder holder, int position) {
        Item item = mListItem.get(position);

        holder.vItemPicture.setImageBitmap(Utility.getPhotoImage(item.getImage()));
        holder.vItemTitle.setText(item.getTitle());
        //holder.vItemPrice.setText(item.getStartedPrice());

        holder.vRemoveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Remove an Item here
            }
        });
    }

    @Override
    public int getItemCount() {
        return mListItem.size();
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        ImageView vItemPicture;
        TextView vItemPrice;
        TextView vItemTitle;
        Button vRemoveBtn;

        public ItemViewHolder(View itemView) {
            super(itemView);
            vItemPicture = (ImageView) itemView.findViewById(R.id.show_item_picture);
            vItemPrice = (TextView) itemView.findViewById(R.id.show_user_item_price);
            vItemTitle = (TextView) itemView.findViewById(R.id.show_user_item_name);
            vRemoveBtn = (Button) itemView.findViewById(R.id.show_user_item_remove);
        }
    }

}
