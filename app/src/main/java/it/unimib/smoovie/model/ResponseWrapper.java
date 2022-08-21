package it.unimib.smoovie.model;

import java.util.ArrayList;
import java.util.List;

public class ResponseWrapper<T> {

    private final T response;
    private final List<Error> errors;

    public ResponseWrapper(T response) {
        this.response = response;
        this.errors = new ArrayList<>();
    }

    public ResponseWrapper(List<Error> errors) {
        this.response = null;
        this.errors = errors;
    }

    public T getResponse() {
        return response;
    }

    public List<Error> getErrors() {
        return errors;
    }

    public boolean hasErrors() {
        return !this.errors.isEmpty();
    }
}
