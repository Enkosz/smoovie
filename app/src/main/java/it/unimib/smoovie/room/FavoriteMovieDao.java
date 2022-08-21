package it.unimib.smoovie.room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface FavoriteMovieDao {

    @Insert
    void insertAll(FavoriteMovie... favoriteMovies);

    @Query("SELECT * FROM favoritemovie")
    List<FavoriteMovie> getAllFavoriteMovies();



}
