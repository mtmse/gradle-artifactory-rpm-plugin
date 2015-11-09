package se.mtm.gradle;

import org.junit.Before;
import org.junit.Test;
import se.mtm.gradle.infrastructure.*;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import static junit.framework.TestCase.assertTrue;

public class ArtifactoryIntegrationTest {
    private String packageName = "rpm-to-artifactory-example";
    private final String srcRepository = "mtm-dev";
    private final String targetRepository = "mtm-staging";
    private final String artifactoryHost = "http://artifactory.mtm.se:8081/artifactory";

    @Before
    public void setUp() throws IOException {
        clearRepository(srcRepository);
        clearRepository(targetRepository);
    }

    @Test
    public void check_that_no_artifact_is_uploaded_upload_an_artifact_see_that_it_is_uploaded_remove_it_and_see_that_it_is_gone() throws IOException {
        String buildDirectory = "./src/test/resources/";
        Artifact artifact = new Artifact(new File(buildDirectory + "rpm-to-artifactory-example-1.0.0-1.noarch.rpm"));

        RepositoryContent artifacts = FindRpms.in(srcRepository, artifactoryHost);
        assertTrue("No artifacts should have been found", artifacts.getFiles().isEmpty());

        UploadRpm.to(artifact, srcRepository, artifactoryHost);

        artifacts = FindRpms.in(srcRepository, artifactoryHost);
        assertTrue("One artifact should have been found", artifacts.getFiles().size() == 1);

        RecalculateYumIndex.trigger(srcRepository, artifactoryHost);

        PurgeRpm.purge(artifact, srcRepository, artifactoryHost);

        artifacts = FindRpms.in(srcRepository, artifactoryHost);
        assertTrue("No artifacts should have been found", artifacts.getFiles().isEmpty());
    }

    @Test
    public void promote_rpm_from_stage_to_utv() throws IOException {
        String buildDirectory = "./src/test/resources/";


        Artifact oldest = new Artifact(new File(buildDirectory + packageName + "-1.0.0-1.noarch.rpm"));
        Artifact old = new Artifact(new File(buildDirectory + packageName + "-1.0.0-2.noarch.rpm"));
        Artifact latest = new Artifact(new File(buildDirectory + packageName + "-1.0.0-3.noarch.rpm"));

        List<Artifact> artifacts2 = new LinkedList<>();
        artifacts2.add(oldest);
        artifacts2.add(old);
        artifacts2.add(latest);

        RepositoryContent artifacts = FindRpms.in(srcRepository, artifactoryHost);
        assertTrue("No artifacts should have been found", artifacts.getFiles().isEmpty());

        for (Artifact artifact : artifacts2) {
            UploadRpm.to(artifact, srcRepository, artifactoryHost);
        }

        PromoteRpm.promote(packageName, srcRepository, targetRepository, artifactoryHost);

    }

    private void clearRepository(String srcRepository) throws IOException {
        RepositoryContent artifacts = FindRpms.in(srcRepository, artifactoryHost);
        Set<Artifact> srcArtifacts = PurgeRpm.getArtifactsToPurge(packageName, artifacts, 0);
        for (Artifact artifact : srcArtifacts) {
            PurgeRpm.purge(artifact, srcRepository, artifactoryHost);
        }
    }
}
