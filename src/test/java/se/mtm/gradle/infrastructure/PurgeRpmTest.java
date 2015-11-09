package se.mtm.gradle.infrastructure;

import org.junit.Test;

import java.io.File;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class PurgeRpmTest {
    @Test
    public void find_old_rpms_to_purge_two_artifacts_only_different_releases() {
        Set<Artifact> expectedArtifacts = new HashSet<>();
        expectedArtifacts.add(new Artifact(new File("autoprod-1.0.1-219.noarch.rpm")));

        RepositoryContent content = getTinyRepositoryContent();
        int generationsToKeep = 1;

        Set<Artifact> actualArtifacts = PurgeRpm.getArtifactsToPurge("autoprod", content, generationsToKeep);

        assertThat(actualArtifacts, is(expectedArtifacts));
    }

    private RepositoryContent getTinyRepositoryContent() {
        String repoUri = "http://artifactory.mtm.se:8081/artifactory/mtm-utv";
        String sourcePattern = "mtm-utv:*.rpm";
        List<String> files = new LinkedList<>();

        files.add("autoprod-1.0.1-219.noarch.rpm");
        files.add("autoprod-1.0.1-223.noarch.rpm");
        files.add("filibuster-1.0.1-223.noarch.rpm");

        return new RepositoryContent(repoUri, sourcePattern, files);
    }

    @Test
    public void find_old_rpms_to_purge_two_different_systems() {
        Set<Artifact> expectedArtifacts = new HashSet<>();
        expectedArtifacts.add(new Artifact(new File("autoprod-1.0.1-219.noarch.rpm")));

        RepositoryContent content = getTwoArtifactTypeRepositoryContent();
        int generationsToKeep = 1;

        Set<Artifact> actualArtifacts = PurgeRpm.getArtifactsToPurge("autoprod", content, generationsToKeep);

        assertThat(actualArtifacts, is(expectedArtifacts));
    }

    private RepositoryContent getTwoArtifactTypeRepositoryContent() {
        String repoUri = "http://artifactory.mtm.se:8081/artifactory/mtm-utv";
        String sourcePattern = "mtm-utv:*.rpm";
        List<String> files = new LinkedList<>();

        files.add("autoprod-1.0.1-219.noarch.rpm");
        files.add("autoprod-1.0.1-223.noarch.rpm");
        files.add("nds-1.1.1-450.noarch.rpm");
        files.add("nds-1.1.1-451.noarch.rpm");

        return new RepositoryContent(repoUri, sourcePattern, files);
    }
}
