package it.unimib.smoovie.ui;

import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import io.reactivex.disposables.Disposable;
import it.unimib.smoovie.R;
import it.unimib.smoovie.firebase.AuthManager;
import it.unimib.smoovie.utils.ProgressDisplay;


public class RegisterFragment extends Fragment implements ProgressDisplay {

    private Button buttonLogin;
    private Button buttonRegister;
    private EditText editTextEmail, editTextPassword;

    private ConstraintLayout containerProgressBar;
    private ConstraintLayout containerRegister;

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
        containerProgressBar = view.findViewById(R.id.container_register_progressBar);
        containerRegister = view.findViewById(R.id.container_register);
        authManager = AuthManager.getInstance(requireActivity().getApplication());

        setupUI();
        return view;
    }

    private void setupUI() {
        buttonRegister.setOnClickListener(v -> {
            showProgress();
            String email = editTextEmail.getText().toString().trim();
            String password = editTextPassword.getText().toString().trim();

            if(email.isEmpty()){ editTextEmail.setError("Email is required"); editTextEmail.requestFocus(); }
            if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){ editTextEmail.setError("Invalid Email"); editTextEmail.requestFocus(); }
            if(password.isEmpty()){ editTextPassword.setError("Password is required"); editTextPassword.requestFocus(); }

            disposableCreateUser = authManager.createUser(email, password)
                            .subscribe(() -> {
                                hideProgress();
                                Navigation.findNavController(requireView())
                                        .navigate(R.id.homeFragment);
                            });
        });

        buttonLogin.setOnClickListener(view -> Navigation.findNavController(view).navigate(R.id.loginFragment));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if(disposableCreateUser != null && !disposableCreateUser.isDisposed())
            disposableCreateUser.dispose();
    }

    @Override
    public void showProgress() {
        containerProgressBar.setVisibility(View.VISIBLE);
        containerRegister.setVisibility(View.INVISIBLE);
    }

    @Override
    public void hideProgress() {
        containerProgressBar.setVisibility(View.GONE);
        containerRegister.setVisibility(View.VISIBLE);
    }
}
