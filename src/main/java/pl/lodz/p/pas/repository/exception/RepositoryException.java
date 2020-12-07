package pl.lodz.p.pas.repository.exception;

import pl.lodz.p.pas.controller.functional.ResourceBundleService;

import java.util.PropertyResourceBundle;

public class RepositoryException extends RuntimeException {

    public RepositoryException() {
        super();
    }

    public RepositoryException(String message) {
        super(message);
    }

    public RepositoryException(String message, Throwable cause) {
        super(message, cause);
    }

    public RepositoryException(Throwable cause) {
        super(cause);
    }

    @Override
    public String getMessage() {
        return getLocalizedMessage();
    }

    @Override
    public String getLocalizedMessage() {
        PropertyResourceBundle resourceBundle = ResourceBundleService.getBundle();
        if (resourceBundle.containsKey("RepositoryException." + super.getMessage())) {
            return resourceBundle.getString("RepositoryException." + super.getMessage());
        }
        return super.getMessage();
    }
}
