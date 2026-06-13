package org.uade.structure.exception;

public class FullADTException extends RuntimeException {
    public FullADTException(String message) {
        super(message);
    }
    public FullADTException() { super("La estructura superó su capacidad máxima.");}
}
