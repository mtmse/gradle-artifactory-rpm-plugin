package se.mtm.gradle.tasks;

import org.gradle.api.tasks.TaskAction;

import java.io.IOException;

public class PromoteToProdTask extends PromoteTask {
    @TaskAction
    public void promote() throws IOException {
        String src = extension.getStageRepo();
        String target = extension.getUtvRepo();

        promote(src, target);
    }
}
