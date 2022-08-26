package it.unimib.smoovie.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import it.unimib.smoovie.room.dao.FavoriteMovieDao;
import it.unimib.smoovie.room.dao.UserDao;
import it.unimib.smoovie.room.model.FavoriteMovie;
import it.unimib.smoovie.room.model.User;

@Database(entities = {FavoriteMovie.class, User.class}, version = 1)
public abstract class SmoovieDatabase extends RoomDatabase {

    private static volatile SmoovieDatabase instance;

    public abstract FavoriteMovieDao favoriteMovieDao();

    public abstract UserDao userDao();

    public static synchronized SmoovieDatabase getInstance(Context context) {
        if (instance == null)
            instance = Room.databaseBuilder(context, SmoovieDatabase.class, "smoovie-database").build();

        return instance;
    }
}
