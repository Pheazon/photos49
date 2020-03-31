package sample;

import java.util.ArrayList;

public class User {

    String username;
    //ArrayList<>albums;
    // perhaps add album information here or make another class, will see later...

    public User(String username) {
        this.username = username;

    }



    public void setUsername(String username) {
        this.username = username;
    }



    public String toString()
    {
        return "username is " + username ;
    }
}
