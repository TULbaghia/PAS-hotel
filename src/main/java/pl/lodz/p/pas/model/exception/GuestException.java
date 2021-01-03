package pl.lodz.p.pas.model.exception;

import pl.lodz.p.pas.controller.functional.ResourceBundleService;

import java.util.PropertyResourceBundle;

public class GuestException extends ModelException {
    public GuestException() {
        super();
    }

    public GuestException(String message) {
        super(message);
    }

    public GuestException(String message, Throwable cause) {
        super(message, cause);
    }

    public GuestException(Throwable cause) {
        super(cause);
    }

    @Override
    public String getMessage() {
        return getLocalizedMessage();
    }

    @Override
    public String getLocalizedMessage() {
        PropertyResourceBundle resourceBundle = ResourceBundleService.getBundle();
        if (resourceBundle != null && resourceBundle.containsKey("GuestException." + super.getMessage())) {
            return resourceBundle.getString("GuestException." + super.getMessage());
        }
        return super.getMessage();
    }
}
