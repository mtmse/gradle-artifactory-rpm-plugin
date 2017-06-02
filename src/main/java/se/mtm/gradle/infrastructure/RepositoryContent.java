package se.mtm.gradle.infrastructure;

import java.io.File;
import java.util.*;

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

    public Set<Artifact> getArtifacts(String packageName) {
        return new HashSet<>(getArtifactsSorted(packageName));
    }

    public Artifact getLatest(String packageName) {
        List<Artifact> selectedArtifacts = getArtifactsSorted(packageName);
        Collections.reverse(selectedArtifacts);

        return selectedArtifacts.get(0);
    }

    public Set<Artifact> getOldArtifacts(String packageName, int generationsToKeep) {
        List<Artifact> selectedArtifacts = getArtifactsSorted(packageName);

        Set<Artifact> oldArtifacts = new HashSet<>();
        for (int index = 0; index < selectedArtifacts.size() - generationsToKeep; index++) {
            Artifact artifact = selectedArtifacts.get(index);
            oldArtifacts.add(artifact);
        }

        return oldArtifacts;
    }

    public List<Artifact> getArtifactsSorted(String packageName) {
        List<Artifact> selectedArtifacts = new LinkedList<>();
        for (Artifact artifact : getArtifacts()) {
            if (artifact.getPackageName().equals(packageName)) {
                selectedArtifacts.add(artifact);
            }
        }

        Comparator<Artifact> artifactComparer = new ArtifactComparator();
        Collections.sort(selectedArtifacts, artifactComparer);

        return selectedArtifacts;
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
