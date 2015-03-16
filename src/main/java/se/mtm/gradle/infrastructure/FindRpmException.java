package se.mtm.gradle.infrastructure;

class FindRpmException extends RuntimeException {
    public FindRpmException(String message, String host) {
        super("Failed to find all RPMs in <" + message + "> on <" + host + ">");
    }
}
