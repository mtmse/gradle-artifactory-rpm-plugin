package se.mtm.gradle.tasks;

import org.gradle.api.DefaultTask;
import org.gradle.api.logging.Logger;
import org.gradle.api.tasks.TaskAction;
import se.mtm.gradle.defaults.ArtifactoryRpmPluginDefaults;
import se.mtm.gradle.infrastructure.Artifact;
import se.mtm.gradle.infrastructure.PurgeRpm;
import se.mtm.gradle.infrastructure.UploadRpm;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class PromoteRpmTask extends DefaultTask {
    @TaskAction
    public void promoteRpm() throws IOException {
        Logger logger = getLogger();
        ArtifactoryRpmPluginDefaults extension = getProject().getExtensions().findByType(ArtifactoryRpmPluginDefaults.class);

        if (extension == null) {
            extension = new ArtifactoryRpmPluginDefaults();
        }

        String developmentRepo = extension.getStagingRepo();
        String promotionRepo = extension.getPromotionRepo();
        String artifactoryHost = extension.getRepositoryServerUrl();

        final String buildDir = getProject().getBuildDir().getCanonicalPath() + "/";
        String buildDirectory = buildDir + extension.getDistributionDir();
        List<File> rpms = UploadRpm.getAllRpms(buildDirectory);

        for (File rpm : rpms) {
            Artifact artifact = new Artifact(rpm);
            UploadRpm.to(artifact, promotionRepo, artifactoryHost);
            PurgeRpm.purge(artifact, developmentRepo, artifactoryHost);
            logger.lifecycle("Promoted " + artifact.getFileName() + " from " + developmentRepo + " to " + promotionRepo + " on " + artifactoryHost);
        }
    }
}
