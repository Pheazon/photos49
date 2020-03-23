package sample;

public class User {
    String name;
    String username;
    String password;
    // perhaps add album information here or make another class, will see later...

    public User(String name, String username, String password) {
        this.name = name;
        this.username = username;
        this.password = password;

    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String toString(){
        return "name is " + name + "username is " + username + "password is " + password;
    }
}
