package se.mtm.gradle.tasks;

import org.gradle.api.DefaultTask;
import org.gradle.api.logging.Logger;
import org.gradle.api.tasks.TaskAction;
import se.mtm.gradle.extensions.ArtifactoryRpmPluginDefaults;
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
        extension.setBuildDir(getProject().getBuildDir());

        String stagingRepo = extension.getStagingRepo();
        String promotionRepo = extension.getPromotionRepo();
        String artifactoryHost = extension.getRepositoryServerUrl();

        String distributionDir = extension.getDistributionDir();
        List<File> rpms = UploadRpm.getAllRpms(distributionDir);

        for (File rpm : rpms) {
            Artifact artifact = new Artifact(rpm);
            logger.lifecycle("Promoting " + artifact.getFileName() + " " + artifact.getSize() + " from " + stagingRepo + " to " + promotionRepo + " on " + artifactoryHost);
            UploadRpm.to(artifact, promotionRepo, artifactoryHost);
            PurgeRpm.purge(artifact, stagingRepo, artifactoryHost, logger);
            logger.lifecycle("Promoted " + artifact.getFileName() + " from " + stagingRepo + " to " + promotionRepo + " on " + artifactoryHost);
        }
    }
}
