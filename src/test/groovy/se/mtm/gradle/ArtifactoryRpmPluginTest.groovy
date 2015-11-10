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

        assertTrue(project.tasks.promoteToStage instanceof PromoteToStageTask)
        assertTrue(project.tasks.promoteToUtv instanceof PromoteToUtvTask)
        assertTrue(project.tasks.promoteToTest instanceof PromoteToTestTask)
        assertTrue(project.tasks.promoteToProd instanceof PromoteToProdTask)
    }
}
