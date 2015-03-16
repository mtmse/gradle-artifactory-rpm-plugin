package se.mtm.gradle.infrastructure;

public class Artifact {
    private final String file;

    public Artifact(String file) {
        this.file = file;
    }

    public String getFile() {
        return file;
    }

    public String getSystemName() {
        String[] parts = file.split("-");

        return parts[0];
    }

    public Integer getMajorVersion() {
        int versionPosition = 1;
        int position = 0;

        return getInteger(versionPosition, position);
    }

    public Integer getMinorVersion() {
        int versionPosition = 1;
        int position = 1;

        return getInteger(versionPosition, position);
    }

    public Integer getPatchVersion() {
        int versionPosition = 1;
        int position = 2;

        return getInteger(versionPosition, position);
    }

    public Integer getReleaseNumber() {
        int releasePosition = 2;
        int position = 0;

        return getInteger(releasePosition, position);
    }

    private Integer getInteger(int versionPosition, int position) {
        String[] parts = file.split("-");
        String version = parts[versionPosition];
        parts = version.split("\\.");
        return Integer.parseInt(parts[position]);
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
