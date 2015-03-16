package se.mtm.gradle.tasks;

import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;
import se.mtm.gradle.infrastructure.Artifact;
import se.mtm.gradle.infrastructure.FindRpms;
import se.mtm.gradle.infrastructure.PurgeRpm;
import se.mtm.gradle.infrastructure.RepositoryContent;

import java.io.IOException;
import java.util.Set;

public class PurgeOldRpmTask extends DefaultTask {
    @TaskAction
    public void purgeOldRpm() throws IOException {
        String repository = "mtm-utv";
        String artifactoryHost = "http://artifactory.mtm.se:8081/artifactory";
        RepositoryContent allRpms = FindRpms.in(repository, artifactoryHost);

        int generationsToKeep = 1;
        Set<Artifact> artifactsToPurge = PurgeRpm.getArtifactsToPurge(allRpms, generationsToKeep);

        for (Artifact artifact : artifactsToPurge) {
            PurgeRpm.purge(artifact, repository, artifactoryHost);
        }
    }
}
