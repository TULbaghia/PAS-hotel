package pl.lodz.p.pas.model.apartment.exception;

public class FiveStarApartmentException extends ApartmentException {

    public FiveStarApartmentException() {
        super();
    }

    public FiveStarApartmentException(String message) {
        super(message);
    }

    public FiveStarApartmentException(Throwable cause) {
        super(cause);
    }

    public FiveStarApartmentException(String message, Throwable cause) {
        super(message, cause);
    }

}
