package by.artem.je.jdbc.exception;

import by.artem.je.jdbc.validator.Error;
import lombok.Getter;

import java.util.List;

public class ValidationException extends Throwable {

    @Getter
    private final List<Error> errors;
    public ValidationException(List<Error> errors) {
        this.errors = errors;
    }
}
