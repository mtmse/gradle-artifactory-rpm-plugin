package se.mtm.gradle.infrastructure;

public class PromoteRpmException extends RuntimeException {
    public PromoteRpmException(String artifactName, String srcRepository, String targetRepository, String artifactoryHost) {
        super("Failed to promote <" + artifactName + "> from <" + srcRepository + "> to <" + targetRepository + "> on <" + artifactoryHost + ">");
    }
}
