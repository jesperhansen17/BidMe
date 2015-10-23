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
    private List<HashMap<String, Object>> listBid;

    public Item(String title, String description, int price, String idSeller) {
        this.title = title;
        this.description = description;
        this.startedPrice = price;
        this.currentPrice = price;
        this.idSeller = idSeller;
        listBid = new ArrayList<HashMap<String, Object>>();
    }

    /**
     * Get the winner of the bidding
     * @return User
     */
    public HashMap<String, Object> getWinner(){
        return listBid.get(listBid.size() - 1);
    }

    /**
     * Get the winner of the bidding
     * @return User
     */
    public void setWinner(String idWinner){
        this.idBuyer = idWinner;
    }

    /**
     * Return the bidding historical
     * @return List<User>
     */
    public List<HashMap<String, Object>> getHistoricalBids(){
        return listBid;
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

    public int getStartedPrice() {
        return startedPrice;
    }

    public int getCurrentPrice() {
        return startedPrice;
    }

    public String getIdBuyer() {
        return idBuyer;
    }

    public String getIdSeller() {
        return idSeller;
    }

}