package it.unimib.smoovie.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.google.firebase.auth.FirebaseAuth;

import io.reactivex.disposables.Disposable;
import it.unimib.smoovie.R;
import it.unimib.smoovie.viewmodel.UserViewModel;

public class LoginFragment extends Fragment {

    private Button buttonLogin;
    private Button buttonRegister;
    private FirebaseAuth firebaseAuth;
    private UserViewModel userViewModel;

    private EditText editTextEmail;
    private EditText editTextPassword;

    private Disposable disposableAuthenticateUser;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        buttonLogin = view.findViewById(R.id.button_login);
        buttonRegister = view.findViewById(R.id.button_register_new_user);
        editTextEmail = view.findViewById(R.id.editTextEmail_login);
        editTextPassword = view.findViewById(R.id.editTextPassword_login);

        setupViewModel();
        setupUI();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        userViewModel.getAuthenticatedUser()
                .observe(getViewLifecycleOwner(), user -> {
                    // If we already have the authenticated user we can redirect to the home view
                    if (user != null) {
                        System.out.println("User authenticated: " + user);
                        Navigation.findNavController(requireView())
                                .navigate(R.id.homeFragment);
                    }
                });
    }

    private void setupViewModel() {
        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
    }

    private void setupUI() {
        buttonLogin.setOnClickListener(v -> disposableAuthenticateUser = userViewModel.authenticateUser(editTextEmail.getText().toString(), editTextPassword.getText().toString())
                .subscribe(() -> Navigation.findNavController(requireView())
                        .navigate(R.id.homeFragment)));

        buttonRegister.setOnClickListener(view -> Navigation.findNavController(view).navigate(R.id.registerFragment));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if(disposableAuthenticateUser != null && !disposableAuthenticateUser.isDisposed())
            disposableAuthenticateUser.dispose();
    }
}
