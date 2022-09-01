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

    public UserViewModel(Application application) {
        super(application);

        authenticatedUser = new MutableLiveData<>();
        userRepository = UserRepository.getInstance(application);
    }
}
