package se.mtm.gradle.infrastructure;

import org.apache.commons.io.FileUtils;
import org.gradle.api.logging.Logger;

import java.io.File;
import java.io.IOException;
import java.util.*;

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

    public static File getLatestRpm(String packageName, String distributionDir, Logger logger) {
        List<File> allRpms = getAllRpms(distributionDir);

        List<Artifact> allArtifacts = new ArrayList<>();
        for (File rpm : allRpms) {
            Artifact candidate = new Artifact(rpm);
            if (candidate.getPackageName().equals(packageName)) {
                allArtifacts.add(candidate);
            }
        }

        Comparator<Artifact> artifactComparer = new ArtifactComparator(logger);
        Collections.sort(allArtifacts, artifactComparer);
        Collections.reverse(allArtifacts);

        return allArtifacts.get(0).getFile();
    }
}
