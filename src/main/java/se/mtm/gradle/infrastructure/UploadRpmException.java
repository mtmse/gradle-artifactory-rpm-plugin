package se.mtm.gradle.infrastructure;

class UploadRpmException extends RuntimeException {
    public UploadRpmException(Artifact artifact, String repository, String host) {
        super("Could not deploy <" + artifact + "> to <" + repository + "> on <" + host + ">");
    }
}
