package mah.bidme.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Jesper Hansen on 2015-10-14.
 */
public class Item {
    private String title;
    private String description;
    private int currentPrice;
    private int startedPrice;
    private String id;
    private String idSeller;
    private String idBuyer;
    private String type;
    private int timer;
    private boolean sold;
    private String image;
    private Map<String, Object> bids;
    private int timer;

    public Item() {}

    public Item(String title, String description, int price, String id, String idSeller, String type, int timer, Boolean sold, String image) {
        this.title = title;
        this.description = description;
        this.startedPrice = price;
        this.currentPrice = price;
        this.id = id;
        this.idSeller = idSeller;
        this.idBuyer = "";
        this.type = type;
        this.timer = timer;
        this.sold = sold;
        this.image = image;
        this.timer = 0;
        this.bids = new HashMap<String, Object>();
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

    public String getId() { return id; }

    public String getIdSeller() {
        return idSeller;
    }

    public String getIdBuyer() {
        return idBuyer;
    }

    public String getType() {
        return type;
    }

    public int getTimer() { return timer; }

    public boolean isSold() {
        return sold;
    }

    public Bitmap getImage() {
        byte[] imageAsByte = Base64.decode(image, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(imageAsByte, 0, imageAsByte.length);
    }

    public Map<String, Object> getBids() {
        return bids;
    }

    public int getTimer(){
        return timer;
    }
}
