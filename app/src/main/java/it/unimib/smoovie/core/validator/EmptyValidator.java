package it.unimib.smoovie.core.validator;

import it.unimib.smoovie.R;

public class EmptyValidator {

    public static ValidationResult validate(String input) {
        boolean isValid = !(input == null || input.isEmpty());

        return new ValidationResult(isValid, R.string.text_validation_error_empty_field);
    }
}
