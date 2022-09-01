package it.unimib.smoovie.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.google.firebase.auth.FirebaseAuth;

import io.reactivex.disposables.Disposable;
import it.unimib.smoovie.R;
import it.unimib.smoovie.firebase.AuthManager;
import it.unimib.smoovie.viewmodel.UserViewModel;

public class HomeFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        AuthManager authManager = AuthManager.getInstance(requireActivity().getApplication());

        if(!authManager.isLogged())
            Navigation.findNavController(requireView())
                    .navigate(R.id.loginFragment);
    }
}
