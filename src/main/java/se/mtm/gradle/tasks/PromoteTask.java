package se.mtm.gradle.tasks;

import org.gradle.api.DefaultTask;
import se.mtm.gradle.extensions.GradleArtifactoryRpmPluginDefaults;
import se.mtm.gradle.infrastructure.PromoteRpm;
import se.mtm.gradle.infrastructure.PurgeRpm;
import se.mtm.gradle.infrastructure.RecalculateYumIndex;

import java.io.IOException;

public class PromoteTask extends DefaultTask {
    protected GradleArtifactoryRpmPluginDefaults extension = getProject().getExtensions().findByType(GradleArtifactoryRpmPluginDefaults.class);

    protected void promote(String src, String target) throws IOException {
        String packageName = getPackageName();
        String host = extension.getRepositoryServerUrl();
        int generationsToKeep = extension.getGenerationsToKeep();

        PromoteRpm.promote(packageName, src, target, host);
        PurgeRpm.purge(packageName, target, host, generationsToKeep);
        RecalculateYumIndex.trigger(target, host);
    }

    private String getPackageName() {
        String packageName = extension.getPackageName();

        if (packageName == null) {
            packageName = getProject().getName();
        }

        return packageName;
    }
}
