package se.mtm.gradle.infrastructure;

public class ConfigurationException extends RuntimeException {
    public ConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }
}
