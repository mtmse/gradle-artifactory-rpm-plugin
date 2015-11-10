package se.mtm.gradle.infrastructure;

import org.junit.Test;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class RepositoryContentTest {

    @Test
    public void find_the_latest() {
        String oldest = "rpm-to-artifactory-example-1.0.0-1.noarch.rpm";
        String old = "rpm-to-artifactory-example-1.0.0-2.noarch.rpm";
        String latest = "rpm-to-artifactory-example-1.0.0-3.noarch.rpm";

        List<String> files = new LinkedList<>();
        files.add(oldest);
        files.add(old);
        files.add(latest);
        RepositoryContent repositoryContent = new RepositoryContent("", "", files);

        Artifact anyRpm = new Artifact(new File(old));

        Artifact expected = new Artifact(new File(latest));

        Artifact actual = repositoryContent.getLatest("rpm-to-artifactory-example");

        assertThat(actual.getFileName(), is(expected.getFileName()));
    }
}
