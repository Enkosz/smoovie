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
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import it.unimib.smoovie.Firebase.Auth.AuthClass;
import it.unimib.smoovie.R;



public class RegisterFragment extends Fragment{

    private FirebaseAuth mAuth;
    private Button buttonLogin;
    private Button buttonRegister;
    private EditText editTextEmail, editTextUsername, editTextPassword;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        buttonLogin = view.findViewById(R.id.button_login_existing_user);
        buttonRegister = view.findViewById(R.id.button_register);
        editTextEmail = view.findViewById(R.id.editTextEmail_register);
        editTextUsername = view.findViewById(R.id.editTextUsername_register);
        editTextPassword = view.findViewById(R.id.editTextPassword_register);

        setupUI();
        return view;
    }

    private void setupUI() {
        mAuth = FirebaseAuth.getInstance();

        buttonRegister.setOnClickListener(v -> {

            String email = editTextEmail.getText().toString().trim();
            String username = editTextUsername.getText().toString().trim();
            String password = editTextPassword.getText().toString().trim();

            if(email.isEmpty()){ editTextEmail.setError("Email is required"); editTextEmail.requestFocus(); }
            if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){ editTextEmail.setError("Invalid Email"); editTextEmail.requestFocus(); }
            if(username.isEmpty()){ editTextUsername.setError("Username is required"); editTextUsername.requestFocus(); }
            if(password.isEmpty()){ editTextPassword.setError("Password is required"); editTextPassword.requestFocus(); }

            AuthClass auth = new AuthClass();
            auth.start();

            auth.createUser(email, username, password);

            //Intent intent = new Intent(requireContext(), ApplicationActivity.class);
            //startActivity(intent);
        });

        buttonLogin.setOnClickListener(view -> {
            Navigation.findNavController(view).navigate(R.id.action_registerFragment_to_loginFragment);
        });



    }


}
