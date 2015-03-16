package se.mtm.gradle.infrastructure;

import org.junit.Test;

import static junit.framework.TestCase.assertTrue;

public class ArtifactComparatorTest {
    private final ArtifactComparator artifactComparator = new ArtifactComparator();

    @Test
    public void compare_two_different_systems() {
        Artifact autoprod = new Artifact("autoprod-1.0.1-219.noarch.rpm");
        Artifact nds = new Artifact("nds-2.2.3-527.noarch.rpm");

        assertTrue("autoprod is smaller than nds", artifactComparator.compare(autoprod, nds) < 0);
        assertTrue("nds is greater than autoprod", artifactComparator.compare(nds, autoprod) > 0);
    }

    @Test
    public void compare_same_artifact_but_different_major_version() {
        Artifact smallAutoprod = new Artifact("autoprod-2.0.1-219.noarch.rpm");
        Artifact largeAutoprod = new Artifact("autoprod-10.0.1-218.noarch.rpm");

        assertTrue(smallAutoprod + " is smaller than  " + largeAutoprod, artifactComparator.compare(smallAutoprod, largeAutoprod) < 0);
        assertTrue(largeAutoprod + " is greater than " + smallAutoprod, artifactComparator.compare(largeAutoprod, smallAutoprod) > 0);
    }

    @Test
    public void compare_same_artifact_but_different_minor_version() {
        Artifact smallAutoprod = new Artifact("autoprod-2.3.1-219.noarch.rpm");
        Artifact largeAutoprod = new Artifact("autoprod-2.11.1-218.noarch.rpm");

        assertTrue(smallAutoprod + " is smaller than  " + largeAutoprod, artifactComparator.compare(smallAutoprod, largeAutoprod) < 0);
        assertTrue(largeAutoprod + " is greater than " + smallAutoprod, artifactComparator.compare(largeAutoprod, smallAutoprod) > 0);
    }

    @Test
    public void compare_same_artifact_but_different_patch_version() {
        Artifact smallAutoprod = new Artifact("autoprod-2.3.4-219.noarch.rpm");
        Artifact largeAutoprod = new Artifact("autoprod-2.3.20-218.noarch.rpm");

        assertTrue(smallAutoprod + " is smaller than  " + largeAutoprod, artifactComparator.compare(smallAutoprod, largeAutoprod) < 0);
        assertTrue(largeAutoprod + " is greater than " + smallAutoprod, artifactComparator.compare(largeAutoprod, smallAutoprod) > 0);
    }

    @Test
    public void compare_same_artifact_but_different_releases() {
        Artifact smallAutoprod = new Artifact("autoprod-2.3.4-4.noarch.rpm");
        Artifact largeAutoprod = new Artifact("autoprod-2.3.4-20.noarch.rpm");

        assertTrue(smallAutoprod + " is smaller than  " + largeAutoprod, artifactComparator.compare(smallAutoprod, largeAutoprod) < 0);
        assertTrue(largeAutoprod + " is greater than " + smallAutoprod, artifactComparator.compare(largeAutoprod, smallAutoprod) > 0);
    }
}
