package se.mtm.gradle.infrastructure;

import java.io.IOException;

public class RecalculateYumIndex {
    public static void trigger(String repository, String artifactoryHost) throws IOException {
        ArtifactoryClient.triggerIndexRecalculation(repository, artifactoryHost);
    }
}
