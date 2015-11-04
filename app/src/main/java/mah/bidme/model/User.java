package mah.bidme.model;

import java.util.ArrayList;

public class User {
    private String id;
    private String name;
    private ArrayList<Item> listItem;

    public User(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
