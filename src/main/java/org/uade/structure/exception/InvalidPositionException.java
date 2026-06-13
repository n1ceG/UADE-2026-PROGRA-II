package org.uade.structure.exception;

public class InvalidPositionException extends RuntimeException {
    public InvalidPositionException(String message) {
        super(message);
    }
    public InvalidPositionException() {super("La posición es inválida.");}
}
