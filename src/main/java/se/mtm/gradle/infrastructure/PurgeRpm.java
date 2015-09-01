package se.mtm.gradle.infrastructure;

import org.gradle.api.logging.Logger;

import java.util.*;

public class PurgeRpm {
    public static void purge(Artifact artifact, String repository, String artifactoryHost) {
        ArtifactoryClient.purgeOld(artifact, repository, artifactoryHost);
    }

    /**
     * Return a set of artifacts that should be purged. Do not purge all artifacts, leave the newest generations.
     *
     * @param content           the content in repository
     * @param generationsToKeep how many generations back should be saved?
     * @return a set of artifacts that could be purged
     */
    public static Set<Artifact> getArtifactsToPurge(RepositoryContent content, int generationsToKeep, Logger logger) {
        List<Artifact> artifacts = content.getArtifacts();

        Set<String> systems = getSystems(artifacts);

        Set<Artifact> artifactsToPurge = new HashSet<>();
        for (String systemName : systems) {
            List<Artifact> artifactsForSystem = selectArtifacts(artifacts, systemName, generationsToKeep, logger);
            artifactsToPurge.addAll(artifactsForSystem);
        }

        return artifactsToPurge;
    }

    private static Set<String> getSystems(List<Artifact> artifacts) {
        Set<String> systems = new HashSet<>();

        for (Artifact artifact : artifacts) {
            String systemName = artifact.getSystemName();
            systems.add(systemName);
        }

        return systems;
    }

    private static List<Artifact> selectArtifacts(List<Artifact> artifacts, String systemName, int generationsToKeep, Logger logger) {
        List<Artifact> artifactsToPurge = new LinkedList<>();

        List<Artifact> selectedArtifacts = new LinkedList<>();

        for (Artifact artifact : artifacts) {
            if (artifact.getSystemName().equals(systemName)) {
                selectedArtifacts.add(artifact);
            }
        }

        Comparator<Artifact> artifactComparer = new ArtifactComparator(logger);
        Collections.sort(selectedArtifacts, artifactComparer);

        for (int index = 0; index < selectedArtifacts.size() - generationsToKeep; index++) {
            Artifact artifact = selectedArtifacts.get(index);
            artifactsToPurge.add(artifact);
        }

        return artifactsToPurge;
    }
}
