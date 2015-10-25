package mah.bidme.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
    private List<HashMap<String, Object>> listBid;

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

    public String getImage() {
        return image;
    }

    public List<HashMap<String, Object>> getListBid() {
        return listBid;
    }
}
