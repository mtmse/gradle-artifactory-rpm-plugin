package se.mtm.gradle.tasks;

import org.gradle.api.DefaultTask;
import org.gradle.api.logging.Logger;
import org.gradle.api.tasks.TaskAction;
import se.mtm.gradle.extensions.ArtifactoryRpmPluginDefaults;
import se.mtm.gradle.infrastructure.Artifact;
import se.mtm.gradle.infrastructure.FindRpms;
import se.mtm.gradle.infrastructure.PurgeRpm;
import se.mtm.gradle.infrastructure.RepositoryContent;

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

        for (String repository : repositories) {
            Set<Artifact> artifactsToPurge = findArtifacts(repository, artifactoryHost, generationsToKeep);
            purge(artifactsToPurge, artifactoryHost, repository);
        }
    }

    private Set<Artifact> findArtifacts(String repository, String artifactoryHost, int generationsToKeep) throws IOException {
        RepositoryContent allRpms = FindRpms.in(repository, artifactoryHost);
        return PurgeRpm.getArtifactsToPurge(allRpms, generationsToKeep);
    }

    private void purge(Set<Artifact> artifactsToPurge, String artifactoryHost, String repository) {
        Logger logger = getLogger();
        for (Artifact artifact : artifactsToPurge) {
            PurgeRpm.purge(artifact, repository, artifactoryHost, logger);
            logger.lifecycle("Purged " + artifact.getFileName() + " from " + repository + " on " + artifactoryHost);
        }
    }
}
