package mah.bidme;

/**
 * Created by marioabouraad on 2015-10-14.
 */
public class User {

    private String userName;
    private long id;

    /*public User () {

    }*/
   public User(String userName, long id) {
        this.id = id;
        this.userName= userName;

    }

    public String getUserName() {
        return userName;
    }


    public long getID() {
        return id;
    }

}
