package io.gofannon.jarmaker;

public class InvalidAttributeNameException extends RuntimeException {

    private String attributeName;

    public InvalidAttributeNameException(String attributeName) {
        this.attributeName=attributeName;
    }

    public InvalidAttributeNameException(String attributeName, String message) {
        super(message);
        this.attributeName=attributeName;
    }

    public InvalidAttributeNameException(String attributeName, String message, Throwable cause) {
        super(message, cause);
        this.attributeName=attributeName;
    }

    public InvalidAttributeNameException(String attributeName, Throwable cause) {
        super(cause);
        this.attributeName=attributeName;
    }

    public String getAttributeName() {
        return attributeName;
    }
}
