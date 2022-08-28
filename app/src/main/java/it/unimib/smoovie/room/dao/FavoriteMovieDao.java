package it.unimib.smoovie.room.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;
import it.unimib.smoovie.room.model.FavoriteMovie;

@Dao
public interface FavoriteMovieDao {

    @Insert
    Completable insertAll(FavoriteMovie... favoriteMovies);

    @Query("DELETE FROM favoritemovie WHERE film_id = :id")
    Completable deleteFavoriteMovie(Long id);

    @Query("SELECT * FROM favoritemovie WHERE film_id = :id")
    Maybe<FavoriteMovie> getFavoriteMovieById(Long id);

    @Query("SELECT * FROM favoritemovie")
    Maybe<List<FavoriteMovie>> getAllFavoriteMovies();
}
