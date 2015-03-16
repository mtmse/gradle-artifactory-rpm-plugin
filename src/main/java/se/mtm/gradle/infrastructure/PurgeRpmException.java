package se.mtm.gradle.infrastructure;


class PurgeRpmException extends RuntimeException {
    public PurgeRpmException(Artifact artifact, String repository, String host) {
        super("Failed to purge <" + artifact + "> from <" + repository + "> on <" + host + ">");
    }
}
