package org.uade.structure.exception;

public class InvalidVertexException extends RuntimeException {
    public InvalidVertexException(String message) {
        super(message);
    }
    public InvalidVertexException() {super("El vértice es inválido o no existe.");}
}
