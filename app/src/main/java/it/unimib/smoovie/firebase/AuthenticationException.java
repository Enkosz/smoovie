package it.unimib.smoovie.firebase;

import java.util.Map;

import it.unimib.smoovie.R;

public class AuthenticationException extends RuntimeException {

    private static final Map<String, Integer> ERROR_MAP = Map.ofEntries(
            Map.entry("ERROR_INVALID_CUSTOM_TOKEN", R.string.error_login_custom_token),
            Map.entry("ERROR_CUSTOM_TOKEN_MISMATCH", R.string.error_login_custom_token_mismatch),
            Map.entry("ERROR_INVALID_CREDENTIAL", R.string.error_login_credential_malformed_or_expired),
            Map.entry("ERROR_INVALID_EMAIL", R.string.error_login_invalid_email),
            Map.entry("ERROR_WRONG_PASSWORD", R.string.error_login_wrong_password),
            Map.entry("ERROR_USER_MISMATCH", R.string.error_login_user_mismatch),
            Map.entry("ERROR_REQUIRES_RECENT_LOGIN", R.string.error_login_requires_recent_login),
            Map.entry("ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL", R.string.error_login_accounts_exits_with_different_credential),
            Map.entry("ERROR_EMAIL_ALREADY_IN_USE", R.string.error_login_email_already_in_use),
            Map.entry("ERROR_CREDENTIAL_ALREADY_IN_USE", R.string.error_login_credential_already_in_use),
            Map.entry("ERROR_USER_DISABLED", R.string.error_login_user_disabled),
            Map.entry("ERROR_USER_TOKEN_EXPIRED", R.string.error_login_user_token_expired),
            Map.entry("ERROR_USER_NOT_FOUND", R.string.error_login_user_not_found),
            Map.entry("ERROR_INVALID_USER_TOKEN", R.string.error_login_invalid_user_token),
            Map.entry("ERROR_OPERATION_NOT_ALLOWED", R.string.error_login_operation_not_allowed),
            Map.entry("ERROR_WEAK_PASSWORD", R.string.error_login_password_is_weak),
            Map.entry("ERROR_GENERIC", R.string.error_generic)
    );

    private final Integer errorCode;

    public AuthenticationException(String errorCode) {
        super();
        this.errorCode = ERROR_MAP.getOrDefault(errorCode, R.string.error_generic);
    }

    public AuthenticationException() {
        this.errorCode = ERROR_MAP.get("ERROR_GENERIC");
    }

    public Integer getErrorCode() {
        return errorCode;
    }
}
