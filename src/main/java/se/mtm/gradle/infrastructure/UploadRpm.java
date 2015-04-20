package se.mtm.gradle.infrastructure;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class UploadRpm {
    public static void to(Artifact artifact, String repository, String artifactoryHost) throws IOException {
        InputStream resourceAsStream = FileUtils.openInputStream(artifact.getFile());
        String md5Hash = DigestUtils.md5Hex(resourceAsStream);

        Entity<File> entity = Entity.entity(artifact.getFile(), MediaType.APPLICATION_OCTET_STREAM_TYPE);
        Client artifactoryClient = ArtifactoryClient.authenticated();
        WebTarget target = artifactoryClient.target(artifactoryHost + "/" + repository + "/" + artifact.getFileName());

        Response response = target
                .request()
                .header("X-Checksum-Md5", md5Hash)
                .put(entity);

        if (response.getStatus() != 201) {
            throw new UploadRpmException(artifact, repository, artifactoryHost);
        }
    }

    public static List<File> getAllRpms(String distributionDir) {
        File dir = new File(distributionDir);
        if (!dir.isDirectory()) {
            try {
                String completePath = dir.getCanonicalPath();
                File currentDir = new File(".");
                String currentDirName = currentDir.getCanonicalPath();
                throw new UploadRpmException(currentDirName, completePath);
            } catch (IOException e) {
                throw new UploadRpmException(e.getMessage(), e);
            }
        }

        String[] fileExtensions = {"rpm"};

        Collection result = FileUtils.listFiles(dir, fileExtensions, false);

        List<File> rpms = new LinkedList<>();
        for (Object fileName : result) {
            rpms.add((File) fileName);
        }

        return rpms;
    }
}
