package se.mtm.gradle.tasks;

import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;

import java.io.IOException;

public class PromoteToUtvTask extends DefaultTask {
    @TaskAction
    public void promote() throws IOException {


        // todo Fortsätt här
        // copy latest package from the stage repo
        // https://www.jfrog.com/confluence/display/RTF/Artifactory+REST+API#ArtifactoryRESTAPI-CopyItem
        // copy purge old packages
        // recalculate yum index
    }
}
