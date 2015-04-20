package se.mtm.gradle;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import se.mtm.gradle.extensions.ArtifactoryRpmPluginDefaults;
import se.mtm.gradle.tasks.PromoteRpmTask;
import se.mtm.gradle.tasks.PurgeOldRpmTask;
import se.mtm.gradle.tasks.DeployRpmTask;

import javax.inject.Inject;

class ArtifactoryRpmPlugin implements Plugin<Project> {
    @Inject
    public ArtifactoryRpmPlugin() {
    }

    @Override
    public void apply(Project project) {
        project.getExtensions().create("artifactoryRpm", ArtifactoryRpmPluginDefaults.class);
        project.getTasks().create("deployRpm", DeployRpmTask.class);
        project.getTasks().create("promoteRpm", PromoteRpmTask.class);
        project.getTasks().create("purgeOldRpms", PurgeOldRpmTask.class);
    }
}
