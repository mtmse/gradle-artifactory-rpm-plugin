package se.mtm.gradle.infrastructure;

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

    public String getSystemName() {
        String[] parts = getFileName().split("-");

        return parts[0];
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
