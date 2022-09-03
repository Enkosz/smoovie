package it.unimib.smoovie.firebase;

import android.app.Application;
import android.content.SharedPreferences;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import it.unimib.smoovie.repository.UserRepository;
import it.unimib.smoovie.room.model.User;

public class AuthManager {

    private final FirebaseAuth firebaseAuth;
    private final SharedPreferences preferences;
    private final SharedPreferences.Editor editor;
    private final UserRepository userRepository;

    private static AuthManager instance;

    private static final String PREFERENCE_NAME = "AndroidHivePref";
    private static final String IS_LOGGED_PREFERENCE = "IsLogged";
    private static final String USER_ID_PREFERENCE = "UserId";

    private AuthManager(Application application) {
        firebaseAuth = FirebaseAuth.getInstance();
        userRepository = UserRepository.getInstance(application);

        preferences = application.getSharedPreferences(PREFERENCE_NAME, 0);
        editor = preferences.edit();
    }

    public static AuthManager getInstance(Application application) {
        if (instance == null)
            instance = new AuthManager(application);

        return instance;
    }

    public Completable createUser(String email, String password) {
        Observable<FirebaseUser> firebaseUserObservable = Observable.create(emitter -> firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(task -> {
                    FirebaseUser user = firebaseAuth.getCurrentUser();

                    emitter.onNext(user);
                })
                .addOnFailureListener(exception -> {
                    exception.printStackTrace();
                    emitter.onError(exception);
                }));


        return Completable.fromObservable(firebaseUserObservable
                        .flatMap(firebaseUser -> {
                            User user = new User(firebaseUser.getUid(), firebaseUser.getEmail());

                            return userRepository.insertUser(user).toObservable();
                        })).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public void logout() {
        editor.clear();
        editor.commit();
    }

    public Observable<Boolean> authenticateUser(String email, String password) {
        Observable<FirebaseUser> firebaseUserObservable = Observable.create(emitter -> firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener(authResult -> emitter.onNext(authResult.getUser())).addOnFailureListener(exception -> {
                    exception.printStackTrace();
                    emitter.onError(exception);
                })
        );

        return firebaseUserObservable
                        .flatMap(firebaseUser -> userRepository.getUserByUsername(firebaseUser.getEmail()).toObservable())
                        .flatMap(user -> {
                            createLoginSession(user.getId());
                            return Observable.just(true);
                        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public boolean isLogged() {
        return preferences.getBoolean(IS_LOGGED_PREFERENCE, false);
    }

    public Maybe<User> getAuthenticatedUser() throws IllegalAccessException {
        if(!isLogged()) throw new IllegalAccessException("User not authenticated");

        return userRepository.getUserById(preferences.getLong(USER_ID_PREFERENCE, 0));
    }

    private void createLoginSession(Long userId) {
        editor.putBoolean(IS_LOGGED_PREFERENCE, true);
        editor.putLong(USER_ID_PREFERENCE, userId);

        editor.commit();
    }
}


