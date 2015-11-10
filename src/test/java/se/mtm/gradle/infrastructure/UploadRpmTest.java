package se.mtm.gradle.infrastructure;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class UploadRpmTest {
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Test
    public void get_all_rpms() {
        File dir = new File("src/test/resources");
        List<File> expected = new LinkedList<>();
        expected.add(new File("src/test/resources/rpm-to-artifactory-example-1.0.0-1.noarch.rpm"));
        expected.add(new File("src/test/resources/rpm-to-artifactory-example-1.0.0-2.noarch.rpm"));
        expected.add(new File("src/test/resources/rpm-to-artifactory-example-1.0.0-3.noarch.rpm"));

        List<File> actualRpms = UploadRpm.getAllRpms(dir);

        assertThat(actualRpms, is(expected));
    }

    @Test
    public void get_latest_rpm() throws IOException {
        folder.newFile("example-1.0.0-0.noarch.rpm");
        folder.newFile("example-1.0.0-1.noarch.rpm");
        folder.newFile("example-1.0.9-3.noarch.rpm");
        folder.newFile("example-1.0.10-4.noarch.rpm");
        folder.newFile("no-example-1.0.11-5.noarch.rpm");
        folder.newFile("no-example-1.0.12-6.noarch.rpm");

        String packageName = "example";
        File dir = folder.getRoot();

        File expected = new File(dir + "/example-1.0.10-4.noarch.rpm");

        File actual = UploadRpm.getLatestRpm(packageName, dir);

        assertThat(actual, is(expected));
    }
}
