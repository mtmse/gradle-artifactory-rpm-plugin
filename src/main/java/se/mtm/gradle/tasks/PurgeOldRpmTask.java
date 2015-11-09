package se.mtm.gradle.tasks;

import org.gradle.api.DefaultTask;
import org.gradle.api.logging.Logger;
import org.gradle.api.tasks.TaskAction;
import se.mtm.gradle.extensions.GradleArtifactoryRpmPluginDefaults;
import se.mtm.gradle.infrastructure.*;

import java.io.IOException;
import java.util.Set;

public class PurgeOldRpmTask extends DefaultTask {
    @TaskAction
    public void purgeOldRpm() throws IOException {
        GradleArtifactoryRpmPluginDefaults extension = getProject().getExtensions().findByType(GradleArtifactoryRpmPluginDefaults.class);

        if (extension == null) {
            extension = new GradleArtifactoryRpmPluginDefaults();
        }

        String packageName = getProject().getName();
        String[] repositories = extension.getPurgeRepos();
        String artifactoryHost = extension.getRepositoryServerUrl();
        int generationsToKeep = extension.getGenerationsToKeep();

        for (String repository : repositories) {
            Set<Artifact> artifactsToPurge = findArtifacts(packageName, repository, artifactoryHost, generationsToKeep);
            purge(artifactsToPurge, artifactoryHost, repository, generationsToKeep);
            RecalculateYumIndex.trigger(repository, artifactoryHost);
        }
    }

    private Set<Artifact> findArtifacts(String rpmName, String repository, String artifactoryHost, int generationsToKeep) throws IOException {
        RepositoryContent allRpms = FindRpms.in(repository, artifactoryHost);
        return PurgeRpm.getArtifactsToPurge(rpmName, allRpms, generationsToKeep);
    }

    private void purge(Set<Artifact> artifactsToPurge, String artifactoryHost, String repository, int generationToKeep) {
        Logger logger = getLogger();
        for (Artifact artifact : artifactsToPurge) {
            PurgeRpm.purge(artifact, repository, artifactoryHost);
            logger.lifecycle("Purged " + artifact.getFileName() + " from " + repository + " on " + artifactoryHost);
        }
    }
}
