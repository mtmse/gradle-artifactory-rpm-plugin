package se.mtm.gradle.infrastructure;

import org.gradle.api.logging.Logger;

import java.io.IOException;
import java.util.Set;

public class PurgeRpm {
    public static void purge(String packageName, String repository, String artifactoryHost, int generationsToKeep, Logger logger) throws IOException {
        logger.quiet("Purge old " + packageName + " packages in " + repository + " on " + artifactoryHost + " keeping " + generationsToKeep + " generations");

        RepositoryContent repositoryContent = ArtifactoryClient.getRepositoryContent(repository, artifactoryHost);
        Set<Artifact> oldArtifacts = repositoryContent.getOldArtifacts(packageName, generationsToKeep);

        for (Artifact artifact : oldArtifacts) {
            ArtifactoryClient.purgeOld(artifact, repository, artifactoryHost);
        }

        logger.quiet("Purged old " + packageName + " packages in " + repository + " on " + artifactoryHost + " keeping " + generationsToKeep + " generations");
    }
}
