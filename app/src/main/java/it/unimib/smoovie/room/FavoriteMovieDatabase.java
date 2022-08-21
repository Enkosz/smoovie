package it.unimib.smoovie.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {FavoriteMovie.class}, version = 1)
public abstract class FavoriteMovieDatabase extends RoomDatabase {
        public abstract FavoriteMovieDao favoriteMovieDao();
}
