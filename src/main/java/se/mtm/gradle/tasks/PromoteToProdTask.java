package se.mtm.gradle.tasks;

import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;

import java.io.IOException;

public class PromoteToProdTask extends DefaultTask {
    @TaskAction
    public void promote() throws IOException {
    }
}
