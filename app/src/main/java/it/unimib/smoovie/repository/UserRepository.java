package it.unimib.smoovie.repository;

import android.app.Application;

import io.reactivex.Maybe;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import it.unimib.smoovie.room.SmoovieDatabase;
import it.unimib.smoovie.room.model.User;

public class UserRepository {

    private static UserRepository instance;

    private final SmoovieDatabase smoovieDatabase;

    private UserRepository(Application application) {
        this.smoovieDatabase = SmoovieDatabase.getInstance(application);
    }

    public static UserRepository getInstance(Application application) {
        if (instance == null)
            instance = new UserRepository(application);

        return instance;
    }

    public Maybe<User> getUserByUsername(String username) {
        return smoovieDatabase.userDao()
                .getUserByUsername(username)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Maybe<User> getUserById(Long id) {
        return smoovieDatabase.userDao()
                .getUserById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<Long> insertUser(User user) {
        return smoovieDatabase.userDao()
                .insert(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
