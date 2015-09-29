package se.mtm.gradle.tasks;

import org.gradle.api.DefaultTask;
import org.gradle.api.logging.Logger;
import org.gradle.api.tasks.TaskAction;
import se.mtm.gradle.extensions.ArtifactoryRpmPluginDefaults;
import se.mtm.gradle.infrastructure.*;

import java.io.IOException;
import java.util.Set;

public class PurgeOldRpmTask extends DefaultTask {
    @TaskAction
    public void purgeOldRpm() throws IOException {
        ArtifactoryRpmPluginDefaults extension = getProject().getExtensions().findByType(ArtifactoryRpmPluginDefaults.class);

        if (extension == null) {
            extension = new ArtifactoryRpmPluginDefaults();
        }

        String[] repositories = extension.getPurgeRepos();
        String artifactoryHost = extension.getRepositoryServerUrl();
        int generationsToKeep = extension.getGenerationsToKeep();
        Logger logger = getLogger();

        for (String repository : repositories) {
            Set<Artifact> artifactsToPurge = findArtifacts(repository, artifactoryHost, generationsToKeep, logger);
            purge(artifactsToPurge, artifactoryHost, repository, logger);
            RecalculateYumIndex.trigger(repository, artifactoryHost);
        }
    }

    private Set<Artifact> findArtifacts(String repository, String artifactoryHost, int generationsToKeep, Logger logger) throws IOException {
        RepositoryContent allRpms = FindRpms.in(repository, artifactoryHost);
        return PurgeRpm.getArtifactsToPurge(allRpms, generationsToKeep, logger);
    }

    private void purge(Set<Artifact> artifactsToPurge, String artifactoryHost, String repository, Logger logger) {
        for (Artifact artifact : artifactsToPurge) {
            PurgeRpm.purge(artifact, repository, artifactoryHost);
            logger.lifecycle("Purged " + artifact.getFileName() + " from " + repository + " on " + artifactoryHost);
        }
    }
}
