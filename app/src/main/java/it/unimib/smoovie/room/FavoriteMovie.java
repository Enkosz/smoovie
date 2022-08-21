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




    public FavoriteMovie(String userId, String filmId, String posterPath){
        this.userId = userId;
        this.filmId = filmId;

    }

}
