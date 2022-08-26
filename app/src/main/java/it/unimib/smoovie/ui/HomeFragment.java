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
import it.unimib.smoovie.viewmodel.UserViewModel;

public class HomeFragment extends Fragment {

    private UserViewModel userViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);

        setupViewModel();
        setupUI();
        return view;
    }

    private void setupViewModel() {
        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
    }

    private void setupUI() {
        userViewModel.getAuthenticatedUser()
            .observe(getViewLifecycleOwner(), user -> {
                if(user == null) {
                    Navigation.findNavController(requireView())
                            .navigate(R.id.loginFragment);
                }
            });
    }
}
