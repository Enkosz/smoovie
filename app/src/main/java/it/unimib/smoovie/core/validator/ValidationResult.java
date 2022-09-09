package it.unimib.smoovie.core.validator;

public class ValidationResult {

    private final Boolean isSuccess;
    private final Integer messageId;

    public ValidationResult(Boolean isSuccess, Integer messageId) {
        this.isSuccess = isSuccess;
        this.messageId = messageId;
    }

    public Boolean isSuccess() {
        return isSuccess;
    }

    public Integer getMessageId() {
        return messageId;
    }
}
