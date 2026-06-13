package org.uade.structure.exception;

public class ExistingValueException extends RuntimeException {
    public ExistingValueException(String message) {
        super(message);
    }
    public ExistingValueException() { super("El valor ya existe en la estructura."); }
}
