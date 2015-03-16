package se.mtm.gradle

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.junit.Test
import se.mtm.gradle.tasks.PurgeOldRpmTask
import se.mtm.gradle.tasks.UploadRpmTask

import static org.junit.Assert.assertTrue

class ArtifactoryRpmPluginTest {
    @Test
    public void should_find_tasks_on_plugin() {
        Project project = ProjectBuilder.builder().build()

        project.getPlugins().apply 'se.mtm.artifactory-rpm'

        assertTrue(project.tasks.to instanceof UploadRpmTask)
        assertTrue(project.tasks.purgeOldRpm instanceof PurgeOldRpmTask)
    }
}
