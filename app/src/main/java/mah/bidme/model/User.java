package mah.bidme.model;

import java.util.ArrayList;

public class User {
    private int id;
    private String name;
    private String mail;
    private ArrayList<Integer> listBid;
    private ArrayList<Item> listItem;

    public User(String name, String mail) {
        this.name = name;
        this.mail = mail;
        this.listBid = new ArrayList<Integer>();
        this.listItem = new ArrayList<Item>();
    }

    /**
     * Place a bid on the choosen item
     * @param item
     */
    public void bidOnItem(Item item){

    }

    /**
     * Return the last bid placed by the user
     * @return int
     */
    public int getLastBid(){
        return listBid.get(listBid.size() - 1);
    }

    /**
     * Create an item into the Firebase database
     * @param item
     */
    public void createItem(Item item){

    }

    /**
     * Modify an existing item in the database
     * @param item
     */
    public void editItem(Item item){

    }

    /**
     * Delete an item from the database
     * @param item
     */
    public void deleteItem(Item item){

    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getMail() {
        return mail;
    }

    public ArrayList<Integer> getListBid() {
        return listBid;
    }

    public ArrayList<Item> getListItem() {
        return listItem;
    }
}
