package pl.lodz.p.pas.model.exception;

import pl.lodz.p.pas.controller.functional.ResourceBundleService;

import java.util.PropertyResourceBundle;

public class ReservationException extends ModelException {
    public ReservationException() {
        super();
    }

    public ReservationException(String message) {
        super(message);
    }

    public ReservationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReservationException(Throwable cause) {
        super(cause);
    }

    @Override
    public String getMessage() {
        return getLocalizedMessage();
    }

    @Override
    public String getLocalizedMessage() {
        PropertyResourceBundle resourceBundle = ResourceBundleService.getBundle();
        if (resourceBundle != null && resourceBundle.containsKey("ReservationException." + super.getMessage())) {
            return resourceBundle.getString("ReservationException." + super.getMessage());
        }
        return super.getMessage();
    }
}
