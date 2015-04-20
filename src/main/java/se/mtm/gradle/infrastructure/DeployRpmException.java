package se.mtm.gradle.infrastructure;

class DeployRpmException extends RuntimeException {
    public DeployRpmException(Artifact artifact, String repository, String host) {
        super("Could not deploy <" + artifact + "> to <" + repository + "> on <" + host + ">");
    }

    public DeployRpmException(String currentDir, String candidateDir) {
        super("<" + candidateDir + "> is not a valid directory from <" + currentDir + ">");
    }

    public DeployRpmException(String message, Exception cause) {
        super(message, cause);
    }
}
