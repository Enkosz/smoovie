package it.unimib.smoovie.room.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;
import it.unimib.smoovie.room.model.User;

@Dao
public interface UserDao {

    @Query("SELECT * FROM users WHERE username = :username")
    Maybe<User> getUserById(String username);

    @Insert
    Single<Long> insert(User user);
}
