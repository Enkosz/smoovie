package it.unimib.smoovie.firebase;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class AuthManager {

    private final FirebaseAuth firebaseAuth;
    private static AuthManager instance;

    private AuthManager() {
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public static AuthManager getInstance() {
        if (instance == null)
            instance = new AuthManager();

        return instance;
    }

    public Observable<FirebaseUser> createUser(String email, String password) {
        Observable<FirebaseUser> firebaseUserObservable = Observable.create(emitter -> firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(task -> {
                    FirebaseUser user = firebaseAuth.getCurrentUser();

                    emitter.onNext(user);
                })
                .addOnFailureListener(exception -> {
                    exception.printStackTrace();
                    emitter.onError(exception);
                }));

        return firebaseUserObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<FirebaseUser> authenticateUser(String email, String password) {
        Observable<FirebaseUser> firebaseUserObservable = Observable.create(emitter -> firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener(authResult -> {
                    emitter.onNext(authResult.getUser());
                }).addOnFailureListener(exception -> {
                    exception.printStackTrace();
                    emitter.onError(exception);
                })
        );

        return firebaseUserObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}


