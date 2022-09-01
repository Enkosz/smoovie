package it.unimib.smoovie.ui;

import android.os.Bundle;
import android.util.Patterns;
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

import io.reactivex.disposables.Disposable;
import it.unimib.smoovie.R;
import it.unimib.smoovie.firebase.AuthManager;
import it.unimib.smoovie.viewmodel.UserViewModel;


public class RegisterFragment extends Fragment {

    private Button buttonLogin;
    private Button buttonRegister;
    private EditText editTextEmail, editTextPassword;

    private Disposable disposableCreateUser;
    private AuthManager authManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        buttonLogin = view.findViewById(R.id.button_login_existing_user);
        buttonRegister = view.findViewById(R.id.button_register);
        editTextEmail = view.findViewById(R.id.editTextEmail_register);
        editTextPassword = view.findViewById(R.id.editTextPassword_register);
        authManager = AuthManager.getInstance(requireActivity().getApplication());

        setupUI();
        return view;
    }

    private void setupUI() {
        buttonRegister.setOnClickListener(v -> {

            String email = editTextEmail.getText().toString().trim();
            String password = editTextPassword.getText().toString().trim();

            if(email.isEmpty()){ editTextEmail.setError("Email is required"); editTextEmail.requestFocus(); }
            if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){ editTextEmail.setError("Invalid Email"); editTextEmail.requestFocus(); }
            if(password.isEmpty()){ editTextPassword.setError("Password is required"); editTextPassword.requestFocus(); }

            disposableCreateUser = authManager.createUser(email, password)
                            .subscribe(() -> Navigation.findNavController(requireView())
                                    .navigate(R.id.loginFragment));
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if(disposableCreateUser != null && !disposableCreateUser.isDisposed())
            disposableCreateUser.dispose();
    }
}
