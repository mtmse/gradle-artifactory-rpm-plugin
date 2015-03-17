package se.mtm.gradle;

import org.junit.Before;
import org.junit.Test;
import se.mtm.gradle.infrastructure.*;

import java.io.File;
import java.io.IOException;

import static junit.framework.TestCase.assertTrue;

public class ArtifactoryIntegrationTest {
    private final String repository = "mtm-dev";
    private final String artifactoryHost = "http://artifactory.mtm.se:8081/artifactory";

    @Before
    public void setUp() throws IOException {
        clearRepository();
    }

    @Test
    public void check_that_no_artifact_is_uploaded_upload_an_artifact_see_that_it_is_uploaded_remove_it_and_see_that_it_is_gone() throws IOException {
        String buildDirectory = "./src/test/resources/build/distributions/";
        Artifact artifact = new Artifact(new File(buildDirectory + "rpm-to-artifactory-example-1.0.0-1.noarch.rpm"));

        RepositoryContent artifacts = FindRpms.in(repository, artifactoryHost);
        assertTrue("No artifacts should have been found", artifacts.getFiles().isEmpty());

        UploadRpm.to(artifact, repository, artifactoryHost);

        artifacts = FindRpms.in(repository, artifactoryHost);
        assertTrue("One artifact should have been found", artifacts.getFiles().size() == 1);

        PurgeRpm.purge(artifact, repository, artifactoryHost);

        artifacts = FindRpms.in(repository, artifactoryHost);
        assertTrue("No artifacts should have been found", artifacts.getFiles().isEmpty());
    }

    private void clearRepository() throws IOException {
        RepositoryContent artifacts = FindRpms.in(repository, artifactoryHost);
        for (String artifact : artifacts.getFiles()) {
            PurgeRpm.purge(new Artifact(new File(artifact)), repository, artifactoryHost);
        }
    }
}
