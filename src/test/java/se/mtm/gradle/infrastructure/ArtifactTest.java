package se.mtm.gradle.infrastructure;

import org.junit.Test;

import java.io.File;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ArtifactTest {
    private final Artifact artifact = new Artifact(new File("nds-2.3.4-527.noarch.rpm"));

    @Test
    public void get_major_version() {
        Integer expected = 2;

        Integer actual = artifact.getMajorVersion();

        assertThat(actual, is(expected));
    }

    @Test
    public void get_minor_version() {
        Integer expected = 3;

        Integer actual = artifact.getMinorVersion();

        assertThat(actual, is(expected));
    }

    @Test
    public void get_patch_version() {
        Integer expected = 4;

        Integer actual = artifact.getPatchVersion();

        assertThat(actual, is(expected));
    }

    @Test
    public void get_release_number() {
        Integer expected = 527;

        Integer actual = artifact.getReleaseNumber();

        assertThat(actual, is(expected));
    }

}
