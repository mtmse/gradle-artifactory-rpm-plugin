package se.mtm.gradle.tasks;

import org.gradle.api.tasks.TaskAction;

import java.io.IOException;

public class PromoteToProdTask extends PromoteTask {
    @TaskAction
    public void promote() throws IOException {
        String src = extension.getTestRepo();
        String target = extension.getProdRepo();

        promote(src, target);
    }
}
