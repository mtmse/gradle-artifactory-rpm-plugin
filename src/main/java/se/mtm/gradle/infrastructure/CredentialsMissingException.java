package se.mtm.gradle.infrastructure;

public class CredentialsMissingException extends RuntimeException {
    public CredentialsMissingException(String message) {
        super(message);
    }
}
