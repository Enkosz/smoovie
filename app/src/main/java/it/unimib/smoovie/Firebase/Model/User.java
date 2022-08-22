package it.unimib.smoovie.Firebase.Model;

public class User {

    public String email, username, avatarPath, userId;

    public User(){}

    public User(String email, String username, String avatarPath, String userId){
        this.email = email;
        this.username = username;
        this.avatarPath = avatarPath;
        this.userId = userId;
    }

}
