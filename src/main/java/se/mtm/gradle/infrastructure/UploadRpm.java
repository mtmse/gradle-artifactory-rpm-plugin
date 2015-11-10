package se.mtm.gradle.infrastructure;

import org.apache.commons.io.FileUtils;
import org.gradle.api.logging.Logger;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class UploadRpm {
    public static void to(Artifact artifact, String repository, String artifactoryHost, Logger logger) throws IOException {
        logger.lifecycle("Uploading " + artifact.getFileName() + " " + artifact.getSize() + " to " + repository + " on " + artifactoryHost);
        long start = System.currentTimeMillis();

        ArtifactoryClient.upLoad(artifact, repository, artifactoryHost);

        long stop = System.currentTimeMillis();
        long duration = stop - start;
        logger.lifecycle("Uploaded " + artifact.getFileName() + " to " + repository + " on " + artifactoryHost + " at " + artifact.getUploadSpeed(duration));
    }

    public static File getLatestRpm(String packageName, File dir) {
        List<File> allRpms = getAllRpms(dir);

        List<Artifact> allArtifacts = new ArrayList<>();
        for (File rpm : allRpms) {
            Artifact candidate = new Artifact(rpm);
            if (candidate.getPackageName().equals(packageName)) {
                allArtifacts.add(candidate);
            }
        }

        if (allArtifacts.isEmpty()) {
            throw new PromoteRpmException("Didn't find any packages named " + packageName + " in " + dir.getAbsolutePath());
        }

        Comparator<Artifact> artifactComparer = new ArtifactComparator();
        Collections.sort(allArtifacts, artifactComparer);
        Collections.reverse(allArtifacts);

        return allArtifacts.get(0).getFile();
    }

    public static List<File> getAllRpms(File dir) {
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

        Collections.sort(rpms);

        return rpms;
    }
}
