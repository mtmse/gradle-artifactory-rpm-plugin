package se.mtm.gradle.infrastructure;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(Parameterized.class)
public class ArtifactNameTest {

    private final String artifactName;
    private final String expectedSystemName;
    private final String expectedVersion;
    private final String expectedRelease;

    @Parameterized.Parameters
    public static Collection<String[]> data() {
        return Arrays.asList(new String[][]{
                {"autoprod-1.0.1-219.noarch.rpm", "autoprod", "1.0.1", "219"},

                {"VRTSralus-14.0.1798-0.x86_64", "VRTSralus", "14.0.1798", "0"},

                {"cyrus-sasl-2.1.23-15.el6.x86_64", "cyrus-sasl", "2.1.23", "15"},
                {"glibc-common-2.12-1.149.el6.x86_64", "glibc-common", "2.12", "1.149"},
                {"openssh-server-5.3p1-104.el6.x86_64", "openssh-server", "5.3p1", "104"},
                {"wireless-tools-29-5.1.1.el6.x86_64", "wireless-tools", "29", "5.1.1"},
                {"hal-info-20090716-3.1.el6.noarch", "hal-info", "20090716", "3.1"},
                {"selinux-policy-3.7.19-260.el6.noarch", "selinux-policy", "3.7.19", "260"},

                {"bzip2-libs-1.0.5-7.el6_0.x86_64", "bzip2-libs", "1.0.5", "7"},
                {"libgcrypt-1.4.5-11.el6_4.x86_64", "libgcrypt", "1.4.5", "11"},
                {"plymouth-0.8.3-27.el6_5.1.x86_64", "plymouth", "0.8.3", "27"},

                {"postgresql93-9.3.6-1PGDG.rhel6.x86_64", "postgresql93", "9.3.6", "1PGDG"}
        });
    }

    public ArtifactNameTest(String artifactName, String expectedSystemName, String expectedVersion, String release) {
        this.artifactName = artifactName;
        this.expectedSystemName = expectedSystemName;
        this.expectedVersion = expectedVersion;
        this.expectedRelease = release;
    }

    @Test
    public void get_system_name() {
        Artifact artifact = new Artifact(new File(artifactName));

        String actual = artifact.getPackageName();

        assertThat(actual, is(expectedSystemName));
    }

    @Test
    public void get_version() {
        Artifact artifact = new Artifact(new File(artifactName));

        String actual = artifact.getVersion();

        assertThat(actual, is(expectedVersion));
    }

    @Test
    public void get_release() {
        Artifact artifact = new Artifact(new File(artifactName));

        String actual = artifact.getRelease();

        assertThat(actual, is(expectedRelease));
    }
}
