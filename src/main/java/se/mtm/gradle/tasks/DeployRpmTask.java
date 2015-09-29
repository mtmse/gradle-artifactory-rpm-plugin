package se.mtm.gradle.tasks;

import org.gradle.api.DefaultTask;
import org.gradle.api.logging.Logger;
import org.gradle.api.tasks.TaskAction;
import se.mtm.gradle.extensions.ArtifactoryRpmPluginDefaults;
import se.mtm.gradle.infrastructure.Artifact;
import se.mtm.gradle.infrastructure.RecalculateYumIndex;
import se.mtm.gradle.infrastructure.UploadRpm;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class DeployRpmTask extends DefaultTask {
    @TaskAction
    public void uploadRpm() throws IOException {
        Logger logger = getLogger();
        ArtifactoryRpmPluginDefaults extension = getProject().getExtensions().findByType(ArtifactoryRpmPluginDefaults.class);

        if (extension == null) {
            extension = new ArtifactoryRpmPluginDefaults();
        }
        extension.setBuildDir(getProject().getBuildDir());

        String distributionDir = extension.getDistributionDir();
        String repository = extension.getStagingRepo();
        String artifactoryHost = extension.getRepositoryServerUrl();

        List<File> rpms = UploadRpm.getAllRpms(distributionDir);

        for (File rpm : rpms) {
            Artifact artifact = new Artifact(rpm);
            logger.lifecycle("Uploading " + artifact.getFileName() + " " + artifact.getSize() + " to " + repository + " on " + artifactoryHost);
            long start = System.currentTimeMillis();
            UploadRpm.to(artifact, repository, artifactoryHost);
            long stop = System.currentTimeMillis();
            long duration = stop - start;
            logger.lifecycle("Uploaded " + artifact.getFileName() + " to " + repository + " on " + artifactoryHost + " at " + artifact.getUploadSpeed(duration));
        }

        RecalculateYumIndex.trigger(repository, artifactoryHost);
    }
}
