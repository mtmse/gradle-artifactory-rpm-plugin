package se.mtm.gradle;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import se.mtm.gradle.defaults.ArtifactoryRpmPluginDefaults;
import se.mtm.gradle.tasks.PurgeOldRpmTask;
import se.mtm.gradle.tasks.UploadRpmTask;

import javax.inject.Inject;

class ArtifactoryRpmPlugin implements Plugin<Project> {
    @Inject
    public ArtifactoryRpmPlugin() {
    }

    @Override
    public void apply(Project project) {
        project.getExtensions().create("artifactoryRpm", ArtifactoryRpmPluginDefaults.class);
        project.getTasks().create("deployRpm", UploadRpmTask.class);
        project.getTasks().create("purgeOldRpms", PurgeOldRpmTask.class);
    }
}
