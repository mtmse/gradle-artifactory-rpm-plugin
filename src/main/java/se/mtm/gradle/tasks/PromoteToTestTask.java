package se.mtm.gradle.tasks;

import org.gradle.api.tasks.TaskAction;

import java.io.IOException;

public class PromoteToTestTask extends PromoteTask {
    @TaskAction
    public void promote() throws IOException {
        String src = extension.getUtvRepo();
        String target = extension.getTestRepo();

        promote(src, target);
    }
}
