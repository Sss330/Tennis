package exceptions;


//todo поменять имя на что то конкретное
public class AlreadyExistException extends RuntimeException {

    public AlreadyExistException(String message) {
        super(message);
    }
}
