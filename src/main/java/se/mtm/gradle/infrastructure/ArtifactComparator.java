package se.mtm.gradle.infrastructure;

import java.util.Comparator;

class ArtifactComparator implements Comparator<Artifact> {
    @Override
    public int compare(Artifact lhs, Artifact rhs) {
        String lhsSystem = lhs.getSystemName();
        String rhsSystem = rhs.getSystemName();
        if (lhsSystem.compareTo(rhsSystem) != 0) {
            return lhsSystem.compareTo(rhsSystem);
        }

        Integer lhsMajorVersion = lhs.getMajorVersion();
        Integer rhsMajorVersion = rhs.getMajorVersion();
        if (lhsMajorVersion.compareTo(rhsMajorVersion) != 0) {
            return lhsMajorVersion.compareTo(rhsMajorVersion);
        }

        Integer lhsMinorVersion = lhs.getMinorVersion();
        Integer rhsMinorVersion = rhs.getMinorVersion();
        if (lhsMinorVersion.compareTo(rhsMinorVersion) != 0) {
            return lhsMinorVersion.compareTo(rhsMinorVersion);
        }

        Integer lhsPatchVersion = lhs.getPatchVersion();
        Integer rhsPatchVersion = rhs.getPatchVersion();
        if (lhsPatchVersion.compareTo(rhsPatchVersion) != 0) {
            return lhsPatchVersion.compareTo(rhsPatchVersion);
        }

        Integer lhsReleaseNumber = lhs.getReleaseNumber();
        Integer rhsReleaseNumber = rhs.getReleaseNumber();
        if (lhsReleaseNumber.compareTo(rhsReleaseNumber) != 0) {
            return lhsReleaseNumber.compareTo(rhsReleaseNumber);
        }


        String o1FileName = lhs.getFileName();
        String o2FileName = rhs.getFileName();

        return o1FileName.compareTo(o2FileName);
    }
}
