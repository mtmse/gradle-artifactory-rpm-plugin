package se.mtm.gradle.infrastructure;

class RecalculateYumIndexException extends RuntimeException {
    public RecalculateYumIndexException(String repository, String host) {
        super("Failed to recalculate yum index in <" + repository + "> on <" + host + ">");
    }
}
