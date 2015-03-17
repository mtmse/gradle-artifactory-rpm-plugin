package se.mtm.gradle.tasks;

import org.gradle.api.DefaultTask;
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
        ArtifactoryRpmPluginDefaults extension = getProject().getExtensions().findByType(ArtifactoryRpmPluginDefaults.class);

        if (extension == null) {
            extension = new ArtifactoryRpmPluginDefaults();
        }

        String buildDirectory = extension.getBuildDirectory();
        String repository = extension.getRepositoryName();
        String artifactoryHost = extension.getRepositoryServerUrl();

        List<File> rpms = UploadRpm.getAllRpms(buildDirectory);

        for (File rpm : rpms) {
            Artifact artifact = new Artifact(rpm);
            UploadRpm.to(artifact, repository, artifactoryHost);
        }
    }
}
