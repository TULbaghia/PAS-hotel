package pl.lodz.p.pas.model.apartment.exception;

public class ApartmentException extends Exception {
    public ApartmentException() {
        super();
    }

    public ApartmentException(String message) {
        super(message);
    }

    public ApartmentException(Throwable cause) {
        super(cause);
    }

    public ApartmentException(String message, Throwable cause) {
        super(message, cause);
    }
}
