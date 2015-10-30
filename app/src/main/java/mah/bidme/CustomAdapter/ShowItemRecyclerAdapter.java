package mah.bidme.CustomAdapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import mah.bidme.model.Item;

/**
 * Created by Jesper Hansen on 2015-10-30.
 */
public class ShowItemRecyclerAdapter extends RecyclerView.Adapter<ShowItemRecyclerAdapter.ItemViewHolder> {
    private List<Item> mListItem;

    public ShowItemRecyclerAdapter() {
        // Empty Constructor
    }

    @Override
    public ShowItemRecyclerAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(ShowItemRecyclerAdapter.ItemViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public ShowItemRecyclerAdapter(List<Item> listItem) {
        this.mListItem = listItem;
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        ImageView vItemPicture;
        TextView vItemPrice;
        TextView vItemTitle;
        TextView vItemYourBid;
        Button vItemRemove;

        public ItemViewHolder(View itemView) {
            super(itemView);
        }
    }

}
