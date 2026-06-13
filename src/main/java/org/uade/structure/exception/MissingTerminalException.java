package org.uade.structure.exception;

public class MissingTerminalException extends RuntimeException {
    public MissingTerminalException(String message) { super(message); }
    public MissingTerminalException() { super("No se encontró el terminal requerido."); }
}
