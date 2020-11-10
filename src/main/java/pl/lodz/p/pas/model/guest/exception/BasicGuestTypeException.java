package pl.lodz.p.pas.model.guest.exception;

public class BasicGuestTypeException extends GuestException {
    public BasicGuestTypeException() {
        super();
    }

    public BasicGuestTypeException(String message) {
        super(message);
    }

    public BasicGuestTypeException(Throwable cause) {
        super(cause);
    }

    public BasicGuestTypeException(String message, Throwable cause) {
        super(message, cause);
    }
}
