package se.mtm.gradle.infrastructure;

import java.util.Set;

public class PurgeRpm {
    public static void purge(Artifact artifact, String repository, String artifactoryHost) {
        ArtifactoryClient.purgeOld(artifact, repository, artifactoryHost);
    }

    /**
     * Return a set of artifacts that should be purged.
     *
     * @param packageName       the package that should be considered. Do not purge packages you don't really own.
     * @param content           the content in repository
     * @param generationsToKeep how many generations back should be saved?
     * @return a set of artifacts that could be purged
     */
    public static Set<Artifact> getArtifactsToPurge(String packageName, RepositoryContent content, int generationsToKeep) {
        return content.getOldArtifacts(packageName, generationsToKeep);
    }
}
