package se.mtm.gradle.infrastructure;

import java.io.IOException;

public class FindRpms {
    public static RepositoryContent in(String repository, String artifactoryHost) throws IOException {
        return ArtifactoryClient.getRepositoryContent(repository, artifactoryHost);
    }
}
