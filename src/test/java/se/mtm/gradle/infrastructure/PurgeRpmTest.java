package se.mtm.gradle.infrastructure;

import org.junit.Test;

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
        expectedArtifacts.add(new Artifact("autoprod-1.0.1-219.noarch.rpm"));

        RepositoryContent content = getTinyRepositoryContent();
        int generationsToKeep = 1;

        Set<Artifact> actualArtifacts = PurgeRpm.getArtifactsToPurge(content, generationsToKeep);

        assertThat(actualArtifacts, is(expectedArtifacts));
    }

    private RepositoryContent getTinyRepositoryContent() {
        String repoUri = "http://artifactory.mtm.se:8081/artifactory/mtm-utv";
        String sourcePattern = "mtm-utv:*.rpm";
        List<String> files = new LinkedList<>();

        files.add("autoprod-1.0.1-219.noarch.rpm");
        files.add("autoprod-1.0.1-223.noarch.rpm");

        return new RepositoryContent(repoUri, sourcePattern, files);
    }

    @Test
    public void find_old_rpms_to_purge_two_different_systems() {
        Set<Artifact> expectedArtifacts = new HashSet<>();
        expectedArtifacts.add(new Artifact("autoprod-1.0.1-219.noarch.rpm"));
        expectedArtifacts.add(new Artifact("nds-1.1.1-450.noarch.rpm"));

        RepositoryContent content = getTwoArtifactTypeRepositoryContent();
        int generationsToKeep = 1;

        Set<Artifact> actualArtifacts = PurgeRpm.getArtifactsToPurge(content, generationsToKeep);

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

    @Test
    public void find_old_rpms_to_purge_three_different_systems() {
        Set<Artifact> expectedArtifacts = new HashSet<>();
        expectedArtifacts.add(new Artifact("autoprod-1.0.1-219.noarch.rpm"));
        expectedArtifacts.add(new Artifact("autoprod-1.0.1-223.noarch.rpm"));
        expectedArtifacts.add(new Artifact("autoprod-1.0.1-228.noarch.rpm"));
        expectedArtifacts.add(new Artifact("autoprod-1.0.1-231.noarch.rpm"));
        expectedArtifacts.add(new Artifact("autoprod-1.1.1-224.noarch.rpm"));
        expectedArtifacts.add(new Artifact("autoprod-1.1.1-226.noarch.rpm"));
        expectedArtifacts.add(new Artifact("autoprod-1.1.1-227.noarch.rpm"));
        expectedArtifacts.add(new Artifact("autoprod-1.1.2-228.noarch.rpm"));
        expectedArtifacts.add(new Artifact("autoprod-1.1.2-229.noarch.rpm"));
        expectedArtifacts.add(new Artifact("autoprod-1.1.2-230.noarch.rpm"));
        expectedArtifacts.add(new Artifact("autoprod-1.1.2-231.noarch.rpm"));
        expectedArtifacts.add(new Artifact("autoprod-1.1.2-232.noarch.rpm"));
        expectedArtifacts.add(new Artifact("nds-1.0.1-222.noarch.rpm"));
        expectedArtifacts.add(new Artifact("nds-1.0.1-223.noarch.rpm"));
        expectedArtifacts.add(new Artifact("nds-1.0.1-224.noarch.rpm"));
        expectedArtifacts.add(new Artifact("nds-1.0.1-225.noarch.rpm"));
        expectedArtifacts.add(new Artifact("services-2.0.1-301.noarch.rpm"));
        expectedArtifacts.add(new Artifact("services-2.0.1-302.noarch.rpm"));
        expectedArtifacts.add(new Artifact("services-2.0.1-303.noarch.rpm"));
        expectedArtifacts.add(new Artifact("services-2.0.1-304.noarch.rpm"));
        expectedArtifacts.add(new Artifact("services-2.1.1-1.noarch.rpm"));
        expectedArtifacts.add(new Artifact("services-2.1.1-2.noarch.rpm"));
        expectedArtifacts.add(new Artifact("services-2.1.1-3.noarch.rpm"));
        expectedArtifacts.add(new Artifact("services-2.1.1-4.noarch.rpm"));
        expectedArtifacts.add(new Artifact("services-2.1.2-5.noarch.rpm"));
        expectedArtifacts.add(new Artifact("services-2.1.2-6.noarch.rpm"));
        expectedArtifacts.add(new Artifact("services-2.1.2-7.noarch.rpm"));
        expectedArtifacts.add(new Artifact("services-2.1.2-8.noarch.rpm"));

        RepositoryContent content = getLargeRepositoryContent();
        int generationsToKeep = 1;

        Set<Artifact> actualArtifacts = PurgeRpm.getArtifactsToPurge(content, generationsToKeep);

        assertThat(actualArtifacts, is(expectedArtifacts));
    }


    private RepositoryContent getLargeRepositoryContent() {
        String repoUri = "http://artifactory.mtm.se:8081/artifactory/mtm-utv";
        String sourcePattern = "mtm-utv:*.rpm";
        List<String> files = new LinkedList<>();

        files.add("autoprod-1.0.1-219.noarch.rpm");
        files.add("autoprod-1.0.1-223.noarch.rpm");
        files.add("autoprod-1.0.1-228.noarch.rpm");
        files.add("autoprod-1.0.1-231.noarch.rpm");
        files.add("autoprod-1.1.1-224.noarch.rpm");
        files.add("autoprod-1.1.1-226.noarch.rpm");
        files.add("autoprod-1.1.1-227.noarch.rpm");
        files.add("autoprod-1.1.2-228.noarch.rpm");
        files.add("autoprod-1.1.2-229.noarch.rpm");
        files.add("autoprod-1.1.2-230.noarch.rpm");
        files.add("autoprod-1.1.2-231.noarch.rpm");
        files.add("autoprod-1.1.2-232.noarch.rpm");
        files.add("autoprod-1.1.2-234.noarch.rpm");
        files.add("nds-1.0.1-222.noarch.rpm");
        files.add("nds-1.0.1-223.noarch.rpm");
        files.add("nds-1.0.1-224.noarch.rpm");
        files.add("nds-1.0.1-225.noarch.rpm");
        files.add("nds-1.0.1-226.noarch.rpm");
        files.add("services-2.0.1-301.noarch.rpm");
        files.add("services-2.0.1-302.noarch.rpm");
        files.add("services-2.0.1-303.noarch.rpm");
        files.add("services-2.0.1-304.noarch.rpm");
        files.add("services-2.1.1-1.noarch.rpm");
        files.add("services-2.1.1-2.noarch.rpm");
        files.add("services-2.1.1-3.noarch.rpm");
        files.add("services-2.1.1-4.noarch.rpm");
        files.add("services-2.1.2-5.noarch.rpm");
        files.add("services-2.1.2-6.noarch.rpm");
        files.add("services-2.1.2-7.noarch.rpm");
        files.add("services-2.1.2-8.noarch.rpm");
        files.add("services-2.1.2-9.noarch.rpm");

        return new RepositoryContent(repoUri, sourcePattern, files);
    }
}
