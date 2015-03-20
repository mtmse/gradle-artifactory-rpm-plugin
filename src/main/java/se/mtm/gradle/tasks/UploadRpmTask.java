package se.mtm.gradle.tasks;

import org.gradle.api.DefaultTask;
import org.gradle.api.logging.Logger;
import org.gradle.api.tasks.TaskAction;
import se.mtm.gradle.defaults.ArtifactoryRpmPluginDefaults;
import se.mtm.gradle.infrastructure.Artifact;
import se.mtm.gradle.infrastructure.UploadRpm;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class UploadRpmTask extends DefaultTask {
    @TaskAction
    public void uploadRpm() throws IOException {
        Logger logger = getLogger();
        ArtifactoryRpmPluginDefaults extension = getProject().getExtensions().findByType(ArtifactoryRpmPluginDefaults.class);

        if (extension == null) {
            extension = new ArtifactoryRpmPluginDefaults();
        }

        final String buildDir = getProject().getBuildDir().getCanonicalPath() + "/";
        String buildDirectory = buildDir +  extension.getDistributionDir();
        String repository = extension.getStagingRepo();
        String artifactoryHost = extension.getRepositoryServerUrl();

        List<File> rpms = UploadRpm.getAllRpms(buildDirectory);

        for (File rpm : rpms) {
            Artifact artifact = new Artifact(rpm);
            UploadRpm.to(artifact, repository, artifactoryHost);
            logger.lifecycle("Uploaded " + artifact.getFileName() + " to " + repository + " on " + artifactoryHost);
        }
    }
}
