package se.mtm.gradle;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import se.mtm.gradle.defaults.ArtifactoryRpmPluginDefaults;
import se.mtm.gradle.tasks.PurgeOldRpmTask;
import se.mtm.gradle.tasks.UploadRpmTask;

class ArtifactoryRpmPlugin implements Plugin<Project> {
    @Override
    public void apply(Project project) {
        project.getExtensions().create("artifactoryRpm", ArtifactoryRpmPluginDefaults.class);
        project.getTasks().create("to", UploadRpmTask.class);
        project.getTasks().create("purgeOldRpm", PurgeOldRpmTask.class);
    }
}
