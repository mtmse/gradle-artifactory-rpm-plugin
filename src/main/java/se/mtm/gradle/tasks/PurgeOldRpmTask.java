package se.mtm.gradle.tasks;

import org.gradle.api.DefaultTask;
import org.gradle.api.logging.Logger;
import org.gradle.api.tasks.TaskAction;
import se.mtm.gradle.defaults.ArtifactoryRpmPluginDefaults;
import se.mtm.gradle.infrastructure.Artifact;
import se.mtm.gradle.infrastructure.FindRpms;
import se.mtm.gradle.infrastructure.PurgeRpm;
import se.mtm.gradle.infrastructure.RepositoryContent;

import java.io.IOException;
import java.util.Set;

public class PurgeOldRpmTask extends DefaultTask {
    @TaskAction
    public void purgeOldRpm() throws IOException {
        Logger logger = getLogger();
        ArtifactoryRpmPluginDefaults extension = getProject().getExtensions().findByType(ArtifactoryRpmPluginDefaults.class);

        if (extension == null) {
            extension = new ArtifactoryRpmPluginDefaults();
        }

        String repository = extension.getDevelopmentRepo();
        String artifactoryHost = extension.getRepositoryServerUrl();
        int generationsToKeep = extension.getGenerationsToKeep();

        RepositoryContent allRpms = FindRpms.in(repository, artifactoryHost);

        Set<Artifact> artifactsToPurge = PurgeRpm.getArtifactsToPurge(allRpms, generationsToKeep);

        for (Artifact artifact : artifactsToPurge) {
            PurgeRpm.purge(artifact, repository, artifactoryHost);
            logger.lifecycle("Purged " + artifact.getFileName() + " from " + repository + " on " + artifactoryHost);
        }
    }
}
