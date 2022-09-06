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
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import io.reactivex.disposables.Disposable;
import it.unimib.smoovie.R;
import it.unimib.smoovie.core.validator.EmailValidator;
import it.unimib.smoovie.core.validator.PasswordValidator;
import it.unimib.smoovie.core.validator.ValidationResult;
import it.unimib.smoovie.firebase.AuthManager;
import it.unimib.smoovie.firebase.AuthenticationException;
import it.unimib.smoovie.utils.ProgressDisplay;

public class LoginFragment extends Fragment implements ProgressDisplay {

    private Button buttonLogin;
    private Button buttonRegister;

    private EditText editTextEmail;
    private EditText editTextPassword;
    private ConstraintLayout containerProgressBar;
    private ConstraintLayout containerLogin;

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
        containerProgressBar = view.findViewById(R.id.container_login_progressBar);
        containerLogin = view.findViewById(R.id.container_login);
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
            showProgress();
            String email = editTextEmail.getText().toString();
            String password = editTextPassword.getText().toString();

            ValidationResult emailValidationResult = EmailValidator.validate(email);
            if(!emailValidationResult.isSuccess()) {
                editTextEmail.setError(getString(emailValidationResult.getMessageId()));
                editTextEmail.requestFocus();
                hideProgress();
                return;
            }

            ValidationResult passwordValidationResult = PasswordValidator.validate(password);
            if(!passwordValidationResult.isSuccess()) {
                editTextPassword.setError(getString(passwordValidationResult.getMessageId()));
                editTextPassword.requestFocus();
                hideProgress();
                return;
            }

            disposableAuthenticateUser = authManager.authenticateUser(email, password)
                    .subscribe(() -> Navigation.findNavController(requireView())
                            .navigate(R.id.homeFragment), throwable -> {
                        hideProgress();
                        Toast.makeText(requireContext(), ((AuthenticationException) throwable).getErrorCode(), Toast.LENGTH_LONG)
                                .show();
                    });
        });

        buttonRegister.setOnClickListener(view -> Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_registerFragment));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if(disposableAuthenticateUser != null && !disposableAuthenticateUser.isDisposed())
            disposableAuthenticateUser.dispose();
    }

    @Override
    public void showProgress() {
        containerProgressBar.setVisibility(View.VISIBLE);
        containerLogin.setVisibility(View.INVISIBLE);
    }

    @Override
    public void hideProgress() {
        containerProgressBar.setVisibility(View.GONE);
        containerLogin.setVisibility(View.VISIBLE);
    }
}
