package it.unimib.smoovie.ui;

import android.content.res.Resources;
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
import it.unimib.smoovie.core.validator.EmptyValidator;
import it.unimib.smoovie.core.validator.PasswordValidator;
import it.unimib.smoovie.core.validator.ValidationResult;
import it.unimib.smoovie.firebase.AuthManager;
import it.unimib.smoovie.firebase.AuthenticationException;
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

            disposableCreateUser = authManager.createUser(email, password)
                            .subscribe(() -> Navigation.findNavController(requireView())
                                    .navigate(R.id.homeFragment), throwable -> {
                                hideProgress();
                                Toast.makeText(requireContext(), ((AuthenticationException) throwable).getErrorCode(), Toast.LENGTH_LONG)
                                        .show();
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
