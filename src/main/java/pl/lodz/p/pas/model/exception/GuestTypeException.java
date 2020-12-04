package pl.lodz.p.pas.model.exception;

public class GuestTypeException extends GuestException {
    public GuestTypeException() {
        super();
    }

    public GuestTypeException(String message) {
        super(message);
    }

    public GuestTypeException(String message, Throwable cause) {
        super(message, cause);
    }

    public GuestTypeException(Throwable cause) {
        super(cause);
    }
}
