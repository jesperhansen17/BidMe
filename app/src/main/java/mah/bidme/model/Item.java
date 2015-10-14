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

    public Item(int id, String title, String description, int price, int idSeller) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.idSeller = idSeller;
        listBid = new ArrayList<User>();
    }



}
