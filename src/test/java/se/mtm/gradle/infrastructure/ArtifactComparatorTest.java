package se.mtm.gradle.infrastructure;

import org.junit.Test;

import java.io.File;

import static junit.framework.TestCase.assertTrue;

public class ArtifactComparatorTest {
    private final ArtifactComparator artifactComparator = new ArtifactComparator();

    @Test
    public void compare_two_different_systems() {
        Artifact autoprod = new Artifact(new File("autoprod-1.0.1-219.noarch.rpm"));
        Artifact nds = new Artifact(new File("nds-2.2.3-527.noarch.rpm"));

        assertTrue("autoprod is smaller than nds", artifactComparator.compare(autoprod, nds) < 0);
        assertTrue("nds is greater than autoprod", artifactComparator.compare(nds, autoprod) > 0);
    }

    @Test
    public void compare_same_artifact_but_different_major_version() {
        Artifact smallAutoprod = new Artifact(new File("autoprod-2.0.1-219.noarch.rpm"));
        Artifact largeAutoprod = new Artifact(new File("autoprod-10.0.1-218.noarch.rpm"));

        assertTrue(smallAutoprod + " is smaller than  " + largeAutoprod, artifactComparator.compare(smallAutoprod, largeAutoprod) < 0);
        assertTrue(largeAutoprod + " is greater than " + smallAutoprod, artifactComparator.compare(largeAutoprod, smallAutoprod) > 0);
    }

    @Test
    public void compare_same_artifact_but_different_minor_version() {
        Artifact smallAutoprod = new Artifact(new File("autoprod-2.3.1-219.noarch.rpm"));
        Artifact largeAutoprod = new Artifact(new File("autoprod-2.11.1-218.noarch.rpm"));

        assertTrue(smallAutoprod + " is smaller than  " + largeAutoprod, artifactComparator.compare(smallAutoprod, largeAutoprod) < 0);
        assertTrue(largeAutoprod + " is greater than " + smallAutoprod, artifactComparator.compare(largeAutoprod, smallAutoprod) > 0);
    }

    @Test
    public void compare_same_artifact_but_different_patch_version() {
        Artifact smallAutoprod = new Artifact(new File("autoprod-2.3.4-219.noarch.rpm"));
        Artifact largeAutoprod = new Artifact(new File("autoprod-2.3.20-218.noarch.rpm"));

        assertTrue(smallAutoprod + " is smaller than  " + largeAutoprod, artifactComparator.compare(smallAutoprod, largeAutoprod) < 0);
        assertTrue(largeAutoprod + " is greater than " + smallAutoprod, artifactComparator.compare(largeAutoprod, smallAutoprod) > 0);
    }

    @Test
    public void compare_same_artifact_but_different_releases() {
        Artifact smallAutoprod = new Artifact(new File("autoprod-2.3.4-4.noarch.rpm"));
        Artifact largeAutoprod = new Artifact(new File("autoprod-2.3.4-20.noarch.rpm"));

        assertTrue(smallAutoprod + " is smaller than  " + largeAutoprod, artifactComparator.compare(smallAutoprod, largeAutoprod) < 0);
        assertTrue(largeAutoprod + " is greater than " + smallAutoprod, artifactComparator.compare(largeAutoprod, smallAutoprod) > 0);
    }
    
    @Test
    public void compare_same_artifact_but_no_patch() {
    	Artifact smallPipeline = new Artifact(new File("pipeline2-1.10-beta1.noarch.rpm"));
        Artifact largePipeline = new Artifact(new File("pipeline2-1.10.1-mtm1.noarch.rpm"));

        assertTrue(smallPipeline + " is smaller than  " + largePipeline, artifactComparator.compare(smallPipeline, largePipeline) < 0);
        assertTrue(largePipeline + " is greater than " + smallPipeline, artifactComparator.compare(largePipeline, smallPipeline) > 0);
    }
    
    @Test
    public void compare_same_artifact_but_different_style() {
    	//unicode decimal compare [a=97,B=66] thus B < a
    	Artifact smallPipeline = new Artifact(new File("pipeline2-1.10.1-BETA1_1.rpm"));
    	Artifact largePipeline = new Artifact(new File("pipeline2-1.10.1-alpha1_1.noarch.rpm"));

        assertTrue(smallPipeline + " is smaller than  " + largePipeline, artifactComparator.compare(smallPipeline, largePipeline) < 0);
        assertTrue(largePipeline + " is greater than " + smallPipeline, artifactComparator.compare(largePipeline, smallPipeline) > 0);
    }
    
    @Test
    public void compare_same_artifact_but_different_snapshot() {
    	Artifact smallPipeline = new Artifact(new File("pipeline2-1.10.1-SNAPSHOT20170304160242.noarch.rpm"));
    	Artifact largePipeline = new Artifact(new File("pipeline2-1.10.1-SNAPSHOT20170306124904.noarch.rpm"));

        assertTrue(smallPipeline + " is smaller than  " + largePipeline, artifactComparator.compare(smallPipeline, largePipeline) < 0);
        assertTrue(largePipeline + " is greater than " + smallPipeline, artifactComparator.compare(largePipeline, smallPipeline) > 0);
    }
}
