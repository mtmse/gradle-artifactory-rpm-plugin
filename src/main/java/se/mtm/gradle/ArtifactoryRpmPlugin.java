package se.mtm.gradle;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import se.mtm.gradle.extensions.GradleArtifactoryRpmPluginDefaults;
import se.mtm.gradle.tasks.*;

import javax.inject.Inject;

class ArtifactoryRpmPlugin implements Plugin<Project> {
    @Inject
    public ArtifactoryRpmPlugin() {
    }

    @Override
    public void apply(Project project) {
        project.getExtensions().create("artifactoryRpm", GradleArtifactoryRpmPluginDefaults.class);

        project.getTasks().create("promoteToStage", PromoteToStageTask.class);
        project.getTasks().create("promoteToUtv", PromoteToUtvTask.class);
        project.getTasks().create("promoteToTest", PromoteToTestTask.class);
        project.getTasks().create("promoteToProd", PromoteToProdTask.class);

        project.getTasks().create("deployRpm", DeployRpmTask.class);
        project.getTasks().create("promoteRpm", PromoteRpmTask.class);
        project.getTasks().create("purgeOldRpms", PurgeOldRpmTask.class);
    }
}
