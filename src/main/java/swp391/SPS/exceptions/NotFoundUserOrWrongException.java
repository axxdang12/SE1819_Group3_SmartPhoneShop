package swp391.SPS.exceptions;

public class NotFoundUserOrWrongException extends Exception{
    private static final long serialVersionUID = 1L;

    public NotFoundUserOrWrongException(String message) {
        super(message);
    }
}
