package se.mtm.gradle.tasks;

import org.gradle.api.DefaultTask;
import org.gradle.api.logging.Logger;
import org.gradle.api.tasks.TaskAction;
import se.mtm.gradle.extensions.GradleArtifactoryRpmPluginDefaults;
import se.mtm.gradle.infrastructure.*;

import java.io.File;
import java.io.IOException;
import java.util.Set;

public class PromoteToStageTask extends DefaultTask {
    @TaskAction
    public void promote() throws IOException {
        GradleArtifactoryRpmPluginDefaults extension = getProject().getExtensions().findByType(GradleArtifactoryRpmPluginDefaults.class);

        if (extension == null) {
            extension = new GradleArtifactoryRpmPluginDefaults();
        }
        extension.setBuildDir(getProject().getBuildDir());

        String repository = extension.getStageRepo();
        String artifactoryHost = extension.getRepositoryServerUrl();

        String packageName = getProject().getName();
        Logger logger = getLogger();
        File rpm = getLatestRpm(packageName, extension, logger);

        uploadRpm(rpm, repository, artifactoryHost, logger);

        int generations = extension.getGenerationsToKeep();
        purge(packageName, repository, artifactoryHost, generations, logger);

        RecalculateYumIndex.trigger(repository, artifactoryHost);
    }

    private void uploadRpm(File rpm, String repository, String artifactoryHost, Logger logger) throws IOException {
        Artifact artifact = new Artifact(rpm);
        logger.lifecycle("Uploading " + artifact.getFileName() + " " + artifact.getSize() + " to " + repository + " on " + artifactoryHost);
        long start = System.currentTimeMillis();
        UploadRpm.to(artifact, repository, artifactoryHost);
        long stop = System.currentTimeMillis();
        long duration = stop - start;
        logger.lifecycle("Uploaded " + artifact.getFileName() + " to " + repository + " on " + artifactoryHost + " at " + artifact.getUploadSpeed(duration));
    }

    private void purge(String packageName, String repository, String artifactoryHost, int generations, Logger logger) throws IOException {
        RepositoryContent allRpms = FindRpms.in(repository, artifactoryHost);
        Set<Artifact> artifactsToPurge = PurgeRpm.getArtifactsToPurge(allRpms, generations, logger);

        for (Artifact artifact : artifactsToPurge) {
            if (artifact.getPackageName().equals(packageName)) {
                PurgeRpm.purge(artifact, repository, artifactoryHost);
            }
        }
    }

    private File getLatestRpm(String packageName, GradleArtifactoryRpmPluginDefaults extension, Logger logger) {
        String distributionDir = extension.getDistributionDir();

        return UploadRpm.getLatestRpm(packageName, distributionDir, logger);
    }
}
