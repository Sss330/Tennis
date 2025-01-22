package exception;

public class PlayerNotSaveException extends RuntimeException {
    public PlayerNotSaveException(String message) {
        super(message);
    }
}
