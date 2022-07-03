package it.unimib.smoovie.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.TextView;

import it.unimib.smoovie.R;
import it.unimib.smoovie.viewmodel.MovieViewModel;

public class MainActivity extends AppCompatActivity {

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.setupUI();
        this.setupViewModels();
    }

    private void setupUI() {
        mTextView = findViewById(R.id.textView);
    }

    private void setupViewModels() {
        MovieViewModel model = new ViewModelProvider(this)
                .get(MovieViewModel.class);

        model.getMovies().observe(this, movies -> {
            mTextView.setText(movies.get(0).id.toString());
        });
    }
}