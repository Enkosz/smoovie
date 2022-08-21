package it.unimib.smoovie.room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class FavoriteMovie {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "user_id")
    public String userId;

    @ColumnInfo(name = "film_id")
    public String filmId;

    public FavoriteMovie(int id, String userId, String filmId) {
        this.id = id;
        this.userId = userId;
        this.filmId = filmId;
    }
}
