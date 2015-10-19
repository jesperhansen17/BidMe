package mah.bidme.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JesperHansen on 2015-10-14.
 */
public class Item {
    private int id;
    private String title;
    private String description;
    private int price;
    private int idSeller;
    private int idBuyer;
    private List<User> listBid;

    public Item(String title, String description, int price, int idSeller) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.idSeller = idSeller;
        listBid = new ArrayList<User>();
    }

    /**
     * Get the winner of the bidding
     * @return User
     */
    public User getWinner(){
        return listBid.get(listBid.size() - 1);
    }

    /**
     * Return the bidding historical
     * @return List<User>
     */
    public List<User> getHistoricalBids(){
        return listBid;
    }


    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getPrice() {
        return price;
    }

    public int getIdBuyer() {
        return idBuyer;
    }

    public int getIdSeller() {
        return idSeller;
    }

}
