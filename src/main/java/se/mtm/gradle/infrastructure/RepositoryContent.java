package se.mtm.gradle.infrastructure;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

public class RepositoryContent {
    private String repoUri;
    private String sourcePattern;
    private List<String> files;

    private RepositoryContent() {
    }

    public RepositoryContent(String repoUri, String sourcePattern, List<String> files) {
        this.repoUri = repoUri;
        this.sourcePattern = sourcePattern;
        this.files = files;
    }

    public String getRepoUri() {
        return repoUri;
    }

    public String getSourcePattern() {
        return sourcePattern;
    }

    public List<String> getFiles() {
        return files;
    }

    public List<Artifact> getArtifacts() {
        List<Artifact> artifacts = new LinkedList<>();

        for (String artifactName : files) {
            artifacts.add(new Artifact(new File(artifactName)));
        }

        return artifacts;
    }

    @Override
    public String toString() {
        String allFiles = "";
        for (String artifact : files) {
            allFiles += "           " + artifact + "\n";
        }

        return "RepositoryContent {" + "\n" +
                "    repoUri='" + repoUri + '\'' + "\n" +
                "    sourcePattern='" + sourcePattern + '\'' + "\n" +
                "    artifacts=[" + "\n" +
                allFiles +
                "          ]" + "\n" +
                '}';
    }
}
