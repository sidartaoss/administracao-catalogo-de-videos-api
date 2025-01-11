package com.fullcycle.admin.catalogo.domain.validation;

import java.util.List;

public interface ValidationHandler {

    ValidationHandler append(Error anError);

    ValidationHandler append(ValidationHandler handler);

    ValidationHandler validate(Validation handler);

    List<Error> errors();

    default boolean hasErrors() {
        return errors() != null && !errors().isEmpty();
    }

    interface Validation {
        void validate();
    }
}
