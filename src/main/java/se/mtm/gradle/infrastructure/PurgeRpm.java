package se.mtm.gradle.infrastructure;

import java.io.IOException;
import java.util.Set;

public class PurgeRpm {
    @Deprecated
    public static void purge(Artifact artifact, String repository, String artifactoryHost) {
        ArtifactoryClient.purgeOld(artifact, repository, artifactoryHost);
    }

    public static void purge(String packageName, String repository, String artifactoryHost, int generationsToKeep) throws IOException {
        RepositoryContent repositoryContent = ArtifactoryClient.getRepositoryContent(repository, artifactoryHost);
        Set<Artifact> oldArtifacts = repositoryContent.getOldArtifacts(packageName, generationsToKeep);

        for (Artifact artifact : oldArtifacts) {
            ArtifactoryClient.purgeOld(artifact, repository, artifactoryHost);
        }
    }

    /**
     * Return a set of artifacts that should be purged.
     *
     * @param packageName       the package that should be considered. Do not purge packages you don't really own.
     * @param content           the content in repository
     * @param generationsToKeep how many generations back should be saved?
     * @return a set of artifacts that could be purged
     */
    @Deprecated
    public static Set<Artifact> getArtifactsToPurge(String packageName, RepositoryContent content, int generationsToKeep) {
        return content.getOldArtifacts(packageName, generationsToKeep);
    }
}
