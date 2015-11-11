package se.mtm.gradle;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import se.mtm.gradle.extensions.GradleArtifactoryRpmPluginDefaults;
import se.mtm.gradle.tasks.PromoteToProdTask;
import se.mtm.gradle.tasks.PromoteToStageTask;
import se.mtm.gradle.tasks.PromoteToTestTask;
import se.mtm.gradle.tasks.PromoteToUtvTask;

class ArtifactoryRpmPlugin implements Plugin<Project> {
    @Override
    public void apply(Project project) {
        project.getExtensions().create("artifactoryRpm", GradleArtifactoryRpmPluginDefaults.class);

        project.getTasks().create("promoteToStageRepo", PromoteToStageTask.class);
        project.getTasks().create("promoteToUtvRepo", PromoteToUtvTask.class);
        project.getTasks().create("promoteToTestRepo", PromoteToTestTask.class);
        project.getTasks().create("promoteToProdRepo", PromoteToProdTask.class);
    }
}
