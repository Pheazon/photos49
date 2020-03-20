package sample;

public class User {
    String username;
    String password;
    // perhaps add album information here or make another class, will see later...

    public User(String username, String password) {
        this.username = username;
        this.password = password;

    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String toString(){
        return "username is " + username + "password is " + password;
    }
}
