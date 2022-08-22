package it.unimib.smoovie.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseUser;

import it.unimib.smoovie.R;

public class LoginFragment extends Fragment {

    private Button buttonLogin;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        buttonLogin = view.findViewById(R.id.button_login);

        setupUI();
        return view;
    }

    private void setupUI() {
        buttonLogin.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), ApplicationActivity.class);

            startActivity(intent);
        });
    }
}
