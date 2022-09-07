package it.unimib.smoovie.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import it.unimib.smoovie.utils.Constants;

public abstract class BaseViewModel extends AndroidViewModel {

    public BaseViewModel(@NonNull Application application) {
        super(application);
    }

    protected String getCurrentLocale() {
        return getApplication()
                .getSharedPreferences("MySharedPref", Context.MODE_PRIVATE)
                .getString(Constants.SHARED_PREFERENCE_LANGUAGE, Constants.LANGUAGE_ENGLISH);
    }
}
