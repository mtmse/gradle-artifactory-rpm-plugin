package se.mtm.gradle.infrastructure;

import org.apache.commons.io.FileUtils;

import java.io.File;

public class Artifact {
    private static final String[] KNOWN_ARCHITECTURES = {"el6", "noarch", "x86_64", "rhel6"};
    private final File file;

    public Artifact(File file) {
        this.file = file;
    }

    public File getFile() {
        return file;
    }

    public String getFileName() {
        return file.getName();
    }

    public String getPackageName() {
        String completeName = getFileName();
        String version = getVersion();

        int compensateForVersionDash = 1;
        int versionPosition = completeName.indexOf(version) - compensateForVersionDash;

        return completeName.substring(0, versionPosition);
    }

    public String getVersion() {
        String fileName = file.getName();
        String[] parts = fileName.split("-");

        int ignoreRelease = 1;
        int zeroBased = 1;
        int version = parts.length - zeroBased - ignoreRelease;

        return parts[version];
    }

    public String getRelease() {
        String fileName = file.getName();
        String[] parts = fileName.split("-");

        String artifactTail = findTail(parts);

        return finRelease(artifactTail);
    }

    public String getSize() {
        long size = file.length();

        return FileUtils.byteCountToDisplaySize(size);
    }

    /**
     * @param duration in milliseconds
     */
    public String getUploadSpeed(long duration) {
        long size = file.length();

        size = size / FileUtils.ONE_KB;

        float durationInSeconds = duration / 1000f;
        if (durationInSeconds == 0) {
            durationInSeconds = 1f;
        }
        long speed = (long) (size / durationInSeconds);

        return speed + " Kb/s";
    }

    private String findTail(String[] parts) {
        String artifactTail = "";
        String version = getVersion();
        for (int index = 0; index < parts.length; index++) {
            String part = parts[index];
            if (part.equals(version)) {
                artifactTail = parts[index + 1];
                break;
            }
        }

        return artifactTail;
    }

    private String finRelease(String artifactTail) {
        String[] parts = artifactTail.split("\\.");

        int architecturePosition = 0;
        for (int index = 0; index < parts.length; index++) {
            String part = parts[index];
            for (String architecture : KNOWN_ARCHITECTURES) {
                if (part.startsWith(architecture)) {
                    architecturePosition = index;
                    index = parts.length;
                }
            }
        }

        String release = "";
        for (int index = 0; index < architecturePosition; index++) {
            String part = parts[index];
            release += part;
            if (index < architecturePosition - 1) {
                release += ".";
            }
        }

        return release;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Artifact artifact = (Artifact) o;

        return !(file != null ? !file.equals(artifact.file) : artifact.file != null);
    }

    @Override
    public int hashCode() {
        return file != null ? file.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Artifact{" +
                "file='" + file + '\'' +
                '}';
    }
}
