package pl.lodz.p.pas.model.guest.exception;

public class SpecialGuestTypeException extends GuestException{
    public SpecialGuestTypeException() {
        super();
    }

    public SpecialGuestTypeException(String message) {
        super(message);
    }

    public SpecialGuestTypeException(Throwable cause) {
        super(cause);
    }

    public SpecialGuestTypeException(String message, Throwable cause) {
        super(message, cause);
    }
}
