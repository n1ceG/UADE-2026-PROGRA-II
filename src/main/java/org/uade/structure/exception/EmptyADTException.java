package org.uade.structure.exception;

public class EmptyADTException extends RuntimeException {
    public EmptyADTException(String message) {
        super(message);
    }
    public EmptyADTException() {super("La estructura está vacía.");}
}
