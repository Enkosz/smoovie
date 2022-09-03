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
    private final Long userId;

    @ColumnInfo(name = "film_id")
    private final Long filmId;

    @ColumnInfo(name = "film_title")
    private final String filmTitle;

    @ColumnInfo(name = "film_poster_path")
    private final String filmPosterPath;

    @Ignore
    public FavoriteMovie(Long userId, Long filmId, String filmTitle, String filmPosterPath) {
        this.userId = userId;
        this.filmId = filmId;
        this.filmTitle = filmTitle;
        this.filmPosterPath = filmPosterPath;
    }

    public FavoriteMovie(int id, Long userId, Long filmId, String filmTitle, String filmPosterPath) {
        this.id = id;
        this.userId = userId;
        this.filmId = filmId;
        this.filmTitle = filmTitle;
        this.filmPosterPath = filmPosterPath;
    }

    public int getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getFilmId() {
        return filmId;
    }

    public String getFilmTitle() {
        return filmTitle;
    }

    public String getFilmPosterPath() {
        return filmPosterPath;
    }
}
