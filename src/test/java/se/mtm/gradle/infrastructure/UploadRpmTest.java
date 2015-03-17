package se.mtm.gradle.infrastructure;

import org.junit.Test;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class UploadRpmTest {

    @Test
    public void get_all_rpms() {
        String dir = "src/test/resources/build/distributions";
        List<File> expected = new LinkedList<>();
        expected.add(new File("src/test/resources/build/distributions/rpm-to-artifactory-example-1.0.0-1.noarch.rpm"));

        List<File> actualRpms = UploadRpm.getAllRpms(dir);

        assertThat(actualRpms, is(expected));
    }
}
