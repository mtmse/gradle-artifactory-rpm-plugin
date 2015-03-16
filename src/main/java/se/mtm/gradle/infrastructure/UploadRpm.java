package se.mtm.gradle.infrastructure;

import org.apache.commons.codec.digest.DigestUtils;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class UploadRpm {
    public static void to(String buildDirectory, Artifact artifact, String repository, String artifactoryHost) throws IOException {
        String rpmFileName = buildDirectory + artifact.getFile();
        InputStream resourceAsStream = UploadRpm.class.getResourceAsStream(rpmFileName);
        String md5Hash = DigestUtils.md5Hex(resourceAsStream);

        URL rpmResource = UploadRpm.class.getClass().getResource(rpmFileName);
        File rpm = new File(rpmResource.getFile());

        Entity<File> entity = Entity.entity(rpm, MediaType.APPLICATION_OCTET_STREAM_TYPE);
        Client artifactoryClient = Util.getAuthenticatedArtifactoryClient();
        WebTarget target = artifactoryClient.target(artifactoryHost + "/" + repository + "/" + artifact.getFile());

        Response response = target
                .request()
                .header("X-Checksum-Md5", md5Hash)
                .put(entity);

        if (response.getStatus() != 201) {
            throw new UploadRpmException(artifact, repository, artifactoryHost);
        }
    }
}
