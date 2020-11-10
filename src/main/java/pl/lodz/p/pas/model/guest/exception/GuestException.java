package pl.lodz.p.pas.model.guest.exception;

public class GuestException extends Exception {
    public GuestException() {
        super();
    }

    public GuestException(String message) {
        super(message);
    }

    public GuestException(Throwable cause) {
        super(cause);
    }

    public GuestException(String message, Throwable cause) {
        super(message, cause);
    }
}
