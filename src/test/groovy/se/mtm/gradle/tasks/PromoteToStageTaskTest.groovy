package se.mtm.gradle.tasks

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.junit.Test

import static org.junit.Assert.assertTrue

class PromoteToStageTaskTest {
    @Test
    public void should_be_able_to_add_task_to_project() {
        Project project = ProjectBuilder.builder().build()

        def task = project.task('promoteToStageRepo', type: PromoteToStageTask)

        assertTrue(task instanceof PromoteToStageTask)
    }
}
