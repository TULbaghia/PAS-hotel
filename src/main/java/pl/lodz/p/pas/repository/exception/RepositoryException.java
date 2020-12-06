package pl.lodz.p.pas.repository.exception;

import java.util.ResourceBundle;

// TODO: ResourceBundleController
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
        ResourceBundle resourceBundle = ResourceBundle.getBundle("messages_en");
        if (resourceBundle.containsKey(super.getMessage())) {
            return resourceBundle.getString(super.getMessage());
        }
        return super.getMessage();
    }
}
