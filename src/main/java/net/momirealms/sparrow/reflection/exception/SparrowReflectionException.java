package net.momirealms.sparrow.reflection.exception;

public class SparrowReflectionException extends RuntimeException {

    public SparrowReflectionException(String message) {
        super(message);
    }

    public SparrowReflectionException(String message, Throwable cause) {
        super(message, cause);
    }

    public SparrowReflectionException(Throwable cause) {
        super(cause);
    }
}
