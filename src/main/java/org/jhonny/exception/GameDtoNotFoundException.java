package org.jhonny.exception;

public class GameDtoNotFoundException extends RuntimeException {
    public GameDtoNotFoundException(String message) {
        super(message);
    }
}
