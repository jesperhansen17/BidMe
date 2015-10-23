package mah.bidme.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by JesperHansen on 2015-10-14.
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

    public Item(String title, String description, int price, String idSeller, String type, Boolean sold, String image, String id) {
        this.title = title;
        this.description = description;
        this.startedPrice = price;
        this.currentPrice = price;
        this.idSeller = idSeller;
        this.idBuyer = "";
        this.type = type;
        this.sold = sold;
        this.image = image;
        this.id = id;
        listBid = new ArrayList<HashMap<String, Object>>();
    }

    public String getId() {
        return id;
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

    public String getImage() {
        return image;
    }

    public List<HashMap<String, Object>> getListBid() {
        return listBid;
    }
}
