package pl.lodz.p.pas.model.exception;

import pl.lodz.p.pas.controller.functional.ResourceBundleService;

import java.util.PropertyResourceBundle;

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

    @Override
    public String getMessage() {
        return getLocalizedMessage();
    }

    @Override
    public String getLocalizedMessage() {
        PropertyResourceBundle resourceBundle = ResourceBundleService.getBundle();
        if (resourceBundle.containsKey("GuestTypeException." + super.getMessage())) {
            return resourceBundle.getString("GuestTypeException." + super.getMessage());
        }
        return super.getMessage();
    }
}
