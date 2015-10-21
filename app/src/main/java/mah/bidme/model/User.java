package mah.bidme.model;

import java.util.ArrayList;

public class User {
    private String id;
    private String name;
    private ArrayList<Item> listItem;

    public User(String name) {
        this.name = name;
    }

    /**
     * Place a bid on the choosen item
     * @param item
     */
    public void bidOnItem(Item item){

    }

   /* *//**
     * Return the last bid placed by the user
     * @return int
     *//*
    public int getLastBid(){
        return listBid.get(listBid.size() - 1);
    }*/

/*    *//**
     * Create an item into the Firebase database
     * @param item
     *//*
    public void createItem(Item item){

    }

    *//**
     * Modify an existing item in the database
     * @param item
     *//*
    public void editItem(Item item){

    }

    *//**
     * Delete an item from the database
     * @param item
     *//*
    public void deleteItem(Item item){

    }*/

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
