package se.mtm.gradle.infrastructure;

class UploadRpmException extends RuntimeException {
    public UploadRpmException(Artifact artifact, String repository, String host) {
        super("Could not deploy <" + artifact + "> to <" + repository + "> on <" + host + ">");
    }

    public UploadRpmException(String currentDir, String candidateDir) {
        super("<" + candidateDir + " is not a valid directory from <" + currentDir + ">");
    }

    public UploadRpmException(String message, Exception cause) {
        super(message, cause);
    }
}
