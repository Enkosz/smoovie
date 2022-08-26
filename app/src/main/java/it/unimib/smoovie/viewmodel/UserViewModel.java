package it.unimib.smoovie.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import io.reactivex.Completable;
import io.reactivex.disposables.Disposable;
import it.unimib.smoovie.firebase.AuthManager;
import it.unimib.smoovie.repository.UserRepository;
import it.unimib.smoovie.room.model.User;

public class UserViewModel extends AndroidViewModel {

    private final MutableLiveData<User> authenticatedUser;
    private final UserRepository userRepository;
    private final AuthManager authManager;

    private Disposable disposableUser;
    private Disposable disposableCreateUser;

    public UserViewModel(Application application) {
        super(application);

        authenticatedUser = new MutableLiveData<>();
        userRepository = UserRepository.getInstance(application);
        authManager = AuthManager.getInstance();
    }

    public LiveData<User> getAuthenticatedUser() {
        if(authenticatedUser.getValue() == null) authenticatedUser.postValue(null);

        return authenticatedUser;
    }

    public Completable authenticateUser(String email, String password) {
        return Completable.fromObservable(authManager.authenticateUser(email, password)
                .flatMap(firebaseUser -> userRepository.getUserByUsername(firebaseUser.getEmail()).toObservable())
                .flatMap(user -> {
                    authenticatedUser.postValue(user);
                    return Completable.complete().toObservable();
                }));
    }

    public Completable logout() {
        // TODO
        return null;
    }

    public Completable createAuthenticatedUser(String email, String password) {
        return Completable.fromObservable(authManager.createUser(email, password)
                .flatMap(firebaseUser -> {
                    User user = new User(firebaseUser.getUid(), firebaseUser.getEmail());

                    return userRepository.insertUser(user).toObservable();
                }));
    }

    @Override
    protected void onCleared() {
        super.onCleared();

        if(disposableUser != null && !disposableUser.isDisposed())
            disposableUser.dispose();
        if(disposableCreateUser != null && !disposableCreateUser.isDisposed())
            disposableCreateUser.dispose();
    }
}
