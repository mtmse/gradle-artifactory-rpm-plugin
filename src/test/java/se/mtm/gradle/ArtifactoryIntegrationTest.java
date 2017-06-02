package se.mtm.gradle;

import org.gradle.api.logging.Logger;
import org.junit.Before;
import org.junit.Test;
import se.mtm.gradle.infrastructure.*;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

public class ArtifactoryIntegrationTest {
    private Logger logger = mock(Logger.class);
    private String packageName = "rpm-to-artifactory-example";
    private final String src = "mtm-dev";
    private final String target = "mtm-staging";
    private final String host = "http://artifactory.mtm.se:8081/artifactory";

    @Before
    public void setUp() throws IOException {
        clearRepository(src);
        clearRepository(target);
    }

    @Test
    public void promote_rpm_from_dev_to_staging() throws IOException {
        String buildDirectory = "./src/test/resources/";

        Artifact oldest = new Artifact(new File(buildDirectory + packageName + "-1.0.0-1.noarch.rpm"));
        Artifact old = new Artifact(new File(buildDirectory + packageName + "-1.0.0-2.noarch.rpm"));
        Artifact latestlatest = new Artifact(new File(buildDirectory + packageName + "-1.0.0-3.noarch.rpm"));
        Artifact latest = new Artifact(new File(buildDirectory + packageName + "-1.0.0-4.noarch.rpm"));
        
        List<Artifact> artifacts = new LinkedList<>();
        artifacts.add(oldest);
        artifacts.add(old);
        artifacts.add(latestlatest);

        // Set stage with tree older packages..
        for (Artifact artifact : artifacts) {
            UploadRpm.to(artifact, src, host, logger);
            UploadRpm.to(artifact, target, host, logger);
        }

        PromoteRpm.promote(packageName, src, target, host);

        PurgeRpm.purge(packageName, target, host, 1, logger);

        RecalculateYumIndex.trigger(target, host, logger);

        RepositoryContent targetArtifacts = FindRpms.in(target, host);
        List<Artifact> allArtifacts = targetArtifacts.getArtifactsSorted(packageName);

        assertThat(allArtifacts.size(), is(1));
        assertTrue("Expected to find " + latest.getFileName(), allArtifacts.contains(latest));
    }

    private void clearRepository(String repository) throws IOException {
        PurgeRpm.purge(packageName, repository, host, 0, logger);
    }
}
