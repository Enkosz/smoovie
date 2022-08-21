package it.unimib.smoovie.room.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class FavoriteMovie {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "user_id")
    private final String userId;

    @ColumnInfo(name = "film_id")
    private final Long filmId;

    @Ignore
    public FavoriteMovie(String userId, Long filmId) {
        this.userId = userId;
        this.filmId = filmId;
    }

    public FavoriteMovie(int id, String userId, Long filmId) {
        this.id = id;
        this.userId = userId;
        this.filmId = filmId;
    }

    public int getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public Long getFilmId() {
        return filmId;
    }
}
