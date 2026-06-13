package org.uade.structure.exception;

public class MissingValueException extends RuntimeException {
    public MissingValueException(String message) {
        super(message);
    }
    public MissingValueException() { super("El valor buscado no existe en la estructura."); }
}
