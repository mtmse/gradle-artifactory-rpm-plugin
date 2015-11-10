package se.mtm.gradle.infrastructure;

import org.gradle.api.logging.Logger;

import java.io.IOException;

public class RecalculateYumIndex {
    public static void trigger(String repository, String artifactoryHost, Logger logger) throws IOException {
        logger.quiet("Recalculate yum index for " + repository + " on " + artifactoryHost);
        ArtifactoryClient.triggerIndexRecalculation(repository, artifactoryHost);
        logger.quiet("Recalculated yum index for " + repository + " on " + artifactoryHost);
    }
}
