package se.mtm.gradle.infrastructure;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class UploadRpm {
    public static void to(Artifact artifact, String repository, String artifactoryHost) throws IOException {
        ArtifactoryClient.upLoad(artifact, repository, artifactoryHost);
    }

    public static List<File> getAllRpms(String distributionDir) {
        File dir = new File(distributionDir);
        if (!dir.isDirectory()) {
            try {
                String completePath = dir.getCanonicalPath();
                File currentDir = new File(".");
                String currentDirName = currentDir.getCanonicalPath();
                throw new DeployRpmException(currentDirName, completePath);
            } catch (IOException e) {
                throw new DeployRpmException(e.getMessage(), e);
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
