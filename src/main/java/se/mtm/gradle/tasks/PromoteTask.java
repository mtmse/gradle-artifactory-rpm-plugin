package se.mtm.gradle.tasks;

import org.gradle.api.DefaultTask;
import org.gradle.api.logging.Logger;
import se.mtm.gradle.extensions.GradleArtifactoryRpmPluginDefaults;
import se.mtm.gradle.infrastructure.PromoteRpm;
import se.mtm.gradle.infrastructure.PurgeRpm;
import se.mtm.gradle.infrastructure.RecalculateYumIndex;

import java.io.IOException;

public class PromoteTask extends DefaultTask {
    protected GradleArtifactoryRpmPluginDefaults extension = getProject().getExtensions().findByType(GradleArtifactoryRpmPluginDefaults.class);

    protected void promote(String src, String target) throws IOException {
        Logger logger = getLogger();
        String packageName = extension.getPackageName(getProject());
        String host = extension.getRepositoryServerUrl();
        int generationsToKeep = extension.getGenerationsToKeep();

        logger.lifecycle("Promote " + packageName + " from " + src + " to " + target + " on " + host);
        PromoteRpm.promote(packageName, src, target, host);
        logger.lifecycle("Promoted " + packageName + " from " + src + " to " + target + " on " + host);

        PurgeRpm.purge(packageName, target, host, generationsToKeep, logger);
        RecalculateYumIndex.trigger(target, host, logger);
    }
}
