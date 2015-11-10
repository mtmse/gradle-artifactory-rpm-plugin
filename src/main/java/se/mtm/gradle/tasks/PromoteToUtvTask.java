package se.mtm.gradle.tasks;

import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;
import se.mtm.gradle.extensions.GradleArtifactoryRpmPluginDefaults;
import se.mtm.gradle.infrastructure.PromoteRpm;
import se.mtm.gradle.infrastructure.PurgeRpm;
import se.mtm.gradle.infrastructure.RecalculateYumIndex;

import java.io.IOException;

public class PromoteToUtvTask extends DefaultTask {
    private GradleArtifactoryRpmPluginDefaults extension = getProject().getExtensions().findByType(GradleArtifactoryRpmPluginDefaults.class);

    @TaskAction
    public void promote() throws IOException {
        String packageName = getPackageName();
        String src = extension.getStageRepo();
        String target = extension.getUtvRepo();
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
