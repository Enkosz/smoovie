package it.unimib.smoovie.firebase;

import android.app.Application;
import android.content.SharedPreferences;
import android.content.res.Resources;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import it.unimib.smoovie.R;

public class AuthManager {

    private final FirebaseAuth firebaseAuth;
    private final SharedPreferences preferences;
    private final SharedPreferences.Editor editor;

    private static AuthManager instance;

    private static final String PREFERENCE_NAME = "AndroidHivePref";
    private static final String IS_LOGGED_PREFERENCE = "IsLogged";
    private static final String USER_ID_PREFERENCE = "UserId";

    private AuthManager(Application application) {
        firebaseAuth = FirebaseAuth.getInstance();

        preferences = application.getSharedPreferences(PREFERENCE_NAME, 0);
        editor = preferences.edit();
    }

    public static AuthManager getInstance(Application application) {
        if (instance == null)
            instance = new AuthManager(application);

        return instance;
    }

    public Completable createUser(String email, String password) {
        Completable firebaseUserCompletable = Completable.create(emitter -> firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(task -> emitter.onComplete())
                .addOnFailureListener(exception -> emitter.onError(mapFirebaseAuthException(exception))));

        return firebaseUserCompletable
                .andThen(authenticateUser(email, password))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public void logout() {
        editor.clear();
        editor.commit();
        firebaseAuth.signOut();
    }

    public Completable authenticateUser(String email, String password) {
        Completable firebaseUserCompletableAuthenticate = Completable.create(emitter -> firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener(authResult -> {
                    createLoginSession(authResult.getUser().getUid());

                    emitter.onComplete();
                })
                .addOnFailureListener(exception -> emitter.onError(mapFirebaseAuthException(exception)))
        );

        return firebaseUserCompletableAuthenticate
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public FirebaseUser getAuthenticatedUser() {
        return firebaseAuth.getCurrentUser();
    }

    public boolean isLogged() {
        return preferences.getBoolean(IS_LOGGED_PREFERENCE, false);
    }

    public void createLoginSession(String userId) {
        editor.putBoolean(IS_LOGGED_PREFERENCE, true);
        editor.putString(USER_ID_PREFERENCE, userId);

        editor.commit();
    }

    private AuthenticationException mapFirebaseAuthException(Exception exception) {
        if(exception instanceof FirebaseAuthException)
            return new AuthenticationException(((FirebaseAuthException) exception).getErrorCode());

        return new AuthenticationException();
    }
}


