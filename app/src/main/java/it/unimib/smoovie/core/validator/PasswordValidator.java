package it.unimib.smoovie.core.validator;

import it.unimib.smoovie.R;

public class PasswordValidator {

    private static final int MIN_LENGTH = 6;

    public static ValidationResult validate(String input) {
        if(input.length() < MIN_LENGTH)
            return new ValidationResult(false, R.string.text_validation_error_min_password_length);

        return new ValidationResult(true, R.string.text_validation_success);
    }
}
