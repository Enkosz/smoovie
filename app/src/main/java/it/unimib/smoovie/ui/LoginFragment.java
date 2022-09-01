package it.unimib.smoovie.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

public class LoginFragment extends Fragment {

    private Button buttonLogin;
    private Button buttonRegister;

    private EditText editTextEmail;
    private EditText editTextPassword;

    private Disposable disposableAuthenticateUser;
    private AuthManager authManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        buttonLogin = view.findViewById(R.id.button_login);
        buttonRegister = view.findViewById(R.id.button_register_new_user);
        editTextEmail = view.findViewById(R.id.editTextEmail_login);
        editTextPassword = view.findViewById(R.id.editTextPassword_login);
        authManager = AuthManager.getInstance(requireActivity().getApplication());

        setupUI();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        if (authManager.isLogged())
            Navigation.findNavController(requireView())
                    .navigate(R.id.homeFragment);
    }

    private void setupUI() {
        buttonLogin.setOnClickListener(v -> {
            String email = editTextEmail.getText().toString();
            String password = editTextPassword.getText().toString();

            disposableAuthenticateUser = authManager.authenticateUser(email, password)
                    .subscribe((authenticationCompleted) -> {
                        if(!authenticationCompleted) Toast.makeText(requireContext(), R.string.error_generic, Toast.LENGTH_LONG).show();
                        Navigation.findNavController(requireView())
                                .navigate(R.id.homeFragment);
                    });
        });

        buttonRegister.setOnClickListener(view -> Navigation.findNavController(view).navigate(R.id.registerFragment));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if(disposableAuthenticateUser != null && !disposableAuthenticateUser.isDisposed())
            disposableAuthenticateUser.dispose();
    }
}
