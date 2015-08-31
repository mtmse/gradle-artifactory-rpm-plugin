package se.mtm.gradle.infrastructure;

import org.junit.Test;

import java.io.File;
import java.net.URL;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ArtifactTest {
    @Test
    public void get_artifact_size() {
        String expected = "3 KB";

        URL testRpm = getClass().getResource("/rpm-to-artifactory-example-1.0.0-1.noarch.rpm");
        Artifact artifact = new Artifact(new File(testRpm.getFile()));

        String actual = artifact.getSize();

        assertThat(actual, is(expected));
    }

}
