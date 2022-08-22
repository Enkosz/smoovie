package it.unimib.smoovie.room.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "users")
public class LocalUser {

    @ColumnInfo(name = "user_id")
    private int userId;

    @ColumnInfo(name = "username")
    private String username;

    @ColumnInfo(name = "avatar_path")
    private String avatarPath;

    public LocalUser(int userId, String username, String avatarPath) {
        this.userId = userId;
        this.username = username;
        this.avatarPath = avatarPath;
    }

    public int getUserId() {return userId;}

    public String getUsername() {return username;}

    public String getAvatarPath() {return avatarPath;}
}
