package se.mtm.gradle.tasks;

import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;
import se.mtm.gradle.infrastructure.Artifact;

import java.io.IOException;

public class UploadRpmTask extends DefaultTask {
    @TaskAction
    public void uploadRpm() throws IOException {
        String buildDirectory = "";
        Artifact artifact = new Artifact("");
        String repository = "mtm-utv";
        String artifactoryHost = "http://artifactory.mtm.se:8081/artifactory";

        // todo enable
        // UploadRpm.to(buildDirectory, artifact, repository, artifactoryHost);
    }
}
