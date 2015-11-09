package se.mtm.gradle.infrastructure;

import java.io.IOException;

public class PromoteRpm {
    public static void promote(String packageName, String srcRepository, String targetRepository, String artifactoryHost) throws IOException {
        RepositoryContent content = ArtifactoryClient.getRepositoryContent(srcRepository, artifactoryHost);
        Artifact latest = content.getLatest(packageName);
        String artifactName = latest.getFileName();
        ArtifactoryClient.copyArtifact(artifactName, srcRepository, targetRepository, artifactoryHost);
    }
}
