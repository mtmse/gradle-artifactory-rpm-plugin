package se.mtm.gradle

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.junit.Test
import se.mtm.gradle.tasks.PromoteToProdTask
import se.mtm.gradle.tasks.PromoteToStageTask
import se.mtm.gradle.tasks.PromoteToTestTask
import se.mtm.gradle.tasks.PromoteToUtvTask

import static org.junit.Assert.assertTrue

class ArtifactoryRpmPluginTest {
    @Test
    public void should_find_tasks_on_plugin() {
        Project project = ProjectBuilder.builder().build()

        project.getPlugins().apply 'se.mtm.artifactory-rpm'

        assertTrue(project.tasks.promoteToStageRepo instanceof PromoteToStageTask)
        assertTrue(project.tasks.promoteToUtvRepo instanceof PromoteToUtvTask)
        assertTrue(project.tasks.promoteToTestRepo instanceof PromoteToTestTask)
        assertTrue(project.tasks.promoteToProdRepo instanceof PromoteToProdTask)
    }
}
