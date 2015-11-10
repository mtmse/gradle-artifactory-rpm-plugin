package se.mtm.gradle.infrastructure;

import java.util.Comparator;

class ArtifactComparator implements Comparator<Artifact> {

    @Override
    public int compare(Artifact lhs, Artifact rhs) {
        String lhsSystem = lhs.getPackageName();
        String rhsSystem = rhs.getPackageName();
        if (lhsSystem.compareTo(rhsSystem) != 0) {
            return lhsSystem.compareTo(rhsSystem);
        }

        Integer lhsMajorVersion = getMajorVersion(lhs);
        Integer rhsMajorVersion = getMajorVersion(rhs);
        if (lhsMajorVersion.compareTo(rhsMajorVersion) != 0) {
            return lhsMajorVersion.compareTo(rhsMajorVersion);
        }

        Integer lhsMinorVersion = getMinorVersion(lhs);
        Integer rhsMinorVersion = getMinorVersion(rhs);
        if (lhsMinorVersion.compareTo(rhsMinorVersion) != 0) {
            return lhsMinorVersion.compareTo(rhsMinorVersion);
        }

        Integer lhsPatchVersion = getPatchVersion(lhs);
        Integer rhsPatchVersion = getPatchVersion(rhs);
        if (lhsPatchVersion.compareTo(rhsPatchVersion) != 0) {
            return lhsPatchVersion.compareTo(rhsPatchVersion);
        }

        Integer lhsReleaseNumber = getReleaseNumber(lhs);
        Integer rhsReleaseNumber = getReleaseNumber(rhs);
        if (lhsReleaseNumber.compareTo(rhsReleaseNumber) != 0) {
            return lhsReleaseNumber.compareTo(rhsReleaseNumber);
        }


        String o1FileName = lhs.getFileName();
        String o2FileName = rhs.getFileName();

        return o1FileName.compareTo(o2FileName);
    }

    private Integer getMajorVersion(Artifact artifact) {
        int position = 0;

        return getInteger(artifact.getVersion(), position);
    }

    private Integer getMinorVersion(Artifact artifact) {
        int position = 1;

        return getInteger(artifact.getVersion(), position);
    }

    private Integer getPatchVersion(Artifact artifact) {
        int position = 2;

        return getInteger(artifact.getVersion(), position);
    }

    private Integer getReleaseNumber(Artifact artifact) {
        int position = 0;

        return getInteger(artifact.getRelease(), position);
    }

    private Integer getInteger(String version, int position) {
        try {
            String[] parts = version.split("\\.");
            return Integer.parseInt(parts[position]);
        } catch (NumberFormatException e) {
            String msg = "Tried to parse <" + version + "> for version number " +
                    "divided by '.' in position <" + position + ">";
            throw new NumberFormatException(msg);
        }
    }
}
