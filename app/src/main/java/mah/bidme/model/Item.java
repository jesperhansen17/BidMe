package mah.bidme.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Jesper Hansen on 2015-10-14.
 */
public class Item {
    private String id;
    private String title;
    private String description;
    private int currentPrice;
    private int startedPrice;
    private String idSeller;
    private String idBuyer;
    private String type;
    private boolean sold;
    private String image;
    private List<HashMap<String, Object>> listBid;

    public Item() {}

    public Item(String title, String description, int price, String idSeller, String type, Boolean sold, String image) {
        this.title = title;
        this.description = description;
        this.startedPrice = price;
        this.currentPrice = price;
        this.idSeller = idSeller;
        this.idBuyer = "";
        this.type = type;
        this.sold = sold;
        this.image = image;
        listBid = new ArrayList<HashMap<String, Object>>();
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getCurrentPrice() {
        return currentPrice;
    }

    public int getStartedPrice() {
        return startedPrice;
    }

    public String getIdSeller() {
        return idSeller;
    }

    public String getIdBuyer() {
        return idBuyer;
    }

    public String getType() {
        return type;
    }

    public boolean isSold() {
        return sold;
    }

    public Bitmap getImage() {
        byte[] imageAsByte = Base64.decode(image, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(imageAsByte, 0, imageAsByte.length);
    }

    public List<HashMap<String, Object>> getListBid() {
        return listBid;
    }
}
