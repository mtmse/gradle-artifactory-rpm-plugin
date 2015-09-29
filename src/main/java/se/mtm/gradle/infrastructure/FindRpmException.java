package se.mtm.gradle.infrastructure;

class FindRpmException extends RuntimeException {
    public FindRpmException(String repository, String host) {
        super("Failed to find all RPMs in <" + repository + "> on <" + host + ">");
    }
}
