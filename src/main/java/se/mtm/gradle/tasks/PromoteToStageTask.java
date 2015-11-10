package se.mtm.gradle.tasks;

import org.gradle.api.DefaultTask;
import org.gradle.api.logging.Logger;
import org.gradle.api.tasks.TaskAction;
import se.mtm.gradle.extensions.GradleArtifactoryRpmPluginDefaults;
import se.mtm.gradle.infrastructure.Artifact;
import se.mtm.gradle.infrastructure.PurgeRpm;
import se.mtm.gradle.infrastructure.RecalculateYumIndex;
import se.mtm.gradle.infrastructure.UploadRpm;

import java.io.File;
import java.io.IOException;

public class PromoteToStageTask extends DefaultTask {

    @TaskAction
    public void promote() throws IOException {
        GradleArtifactoryRpmPluginDefaults extension = getProject().getExtensions().findByType(GradleArtifactoryRpmPluginDefaults.class);
        Logger logger = getLogger();
        String packageName = extension.getPackageName(getProject());
        String repository = extension.getStageRepo();
        String host = extension.getRepositoryServerUrl();
        int generationsToKeep = extension.getGenerationsToKeep();

        upload(packageName, repository, host, logger);

        PurgeRpm.purge(repository, packageName, host, generationsToKeep, logger);

        RecalculateYumIndex.trigger(repository, host, logger);
    }

    private void upload(String packageName, String repository, String host, Logger logger) throws IOException {
        Artifact artifact = getArtifact(packageName);

        UploadRpm.to(artifact, repository, host, logger);
    }

    private Artifact getArtifact(String packageName) {
        File buildDir = getProject().getBuildDir();

        File rpm = UploadRpm.getLatestRpm(packageName, buildDir);
        return new Artifact(rpm);
    }
}
