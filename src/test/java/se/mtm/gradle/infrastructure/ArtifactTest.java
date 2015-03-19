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
public class ArtifactTest {

    private final String artifactName;
    private final String expectedversion;
    private final String expectedRelease;

    @Parameterized.Parameters
    public static Collection<String[]> data() {
        return Arrays.asList(new String[][]{
                {"nds-2.3.4-527.noarch.rpm", "2.3.4", "527"},
                {"lvm2-libs-2.02.111-2.el6.x86_64", "2.02.111", "2"},
                {"tzdata-2014g-1.el6.noarch", "2014g", "1"},
                {"autoprod-1.0.1-219.noarch.rpm", "1.0.1", "219"},
                {"hal-info-20090716-3.1.el6.noarch", "20090716", "3.1"},
                {"glibc-common-2.12-1.149.el6.x86_64", "2.12", "1.149"},
                {"rpcbind-0.2.0-11.el6.x86_64", "0.2.0", "11",},
                {"yum-rhn-plugin-0.9.1-50.el6.noarch", "0.9.1", "50"},
                {"bash-4.1.2-29.el6.x86_64", "4.1.2", "29",},
                {"nfs-utils-1.2.3-54.el6.x86_64", "1.2.3", "54"},
                {"plymouth-0.8.3-27.el6_5.1.x86_64", "0.8.3", "27"},
                {"info-4.13a-8.el6.x86_64", "4.13a", "8"},
                {"wireless-tools-29-5.1.1.el6.x86_64", "29", "5.1.1"},
                {"popt-1.13-7.el6.x86_64", "1.13", "7"},
                {"cyrus-sasl-2.1.23-15.el6.x86_64", "2.1.23", "15"},
                {"libcom_err-1.41.12-21.el6.x86_64", "1.41.12", "21"},
                {"cronie-1.4.4-12.el6.x86_64", "1.4.4", "12"},
                {"libsepol-2.0.41-4.el6.x86_64", "2.0.41", "4"},
                {"selinux-policy-3.7.19-260.el6.noarch", "3.7.19", "260"},
                {"bzip2-libs-1.0.5-7.el6_0.x86_64", "1.0.5", "7"},
                {"irqbalance-1.0.4-10.el6.x86_64", "1.0.4", "10"},
                {"btparser-0.17-2.el6.x86_64", "0.17", "2"},
                {"system-config-firewall-tui-1.2.27-7.1.el6.noarch", "1.2.27", "7.1"},
                {"libuuid-2.17.2-12.18.el6.x86_64", "2.17.2", "12.18"},
                {"system-config-network-tui-1.6.0.el6.2-1.el6.noarch", "1.6.0.el6.2", "1"},
                {"libgcrypt-1.4.5-11.el6_4.x86_64", "1.4.5", "11"},
                {"openssh-server-5.3p1-104.el6.x86_64", "5.3p1", "104"}

        });
    }

    public ArtifactTest(String artifactName, String expectedVersion, String release) {
        this.artifactName = artifactName;
        this.expectedversion = expectedVersion;
        this.expectedRelease = release;
    }

    @Test
    public void get_version() {
        Artifact artifact = new Artifact(new File(artifactName));

        String actual = artifact.getVersion();

        assertThat(actual, is(expectedversion));
    }

    @Test
    public void get_release() {
        Artifact artifact = new Artifact(new File(artifactName));

        String actual = artifact.getRelease();

        assertThat(actual, is(expectedRelease));
    }
}
