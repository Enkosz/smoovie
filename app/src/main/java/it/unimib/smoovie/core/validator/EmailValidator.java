package it.unimib.smoovie.core.validator;

import android.util.Patterns;

import it.unimib.smoovie.R;

public class EmailValidator {

    public static ValidationResult validate(String input) {
        if(!Patterns.EMAIL_ADDRESS.matcher(input).matches())
            return new ValidationResult(false, R.string.text_validation_error_email);

        return new ValidationResult(true, R.string.text_validation_success);
    }
}
