package se.mtm.gradle.defaults;

public class ArtifactoryRpmPluginDefaults {
    private String repositoryServerUrl = "http://artifactory.mtm.se:8081/artifactory";
    private String repositoryName = "mtm-dev";
    private String buildDirectory = "./build/distributions/";
    private int generationsToKeep= 1;

    public String getRepositoryServerUrl() {
        return repositoryServerUrl;
    }

    public void setRepositoryServerUrl(String repositoryServerUrl) {
        this.repositoryServerUrl = repositoryServerUrl;
    }

    public String getRepositoryName() {
        return repositoryName;
    }

    public void setRepositoryName(String repositoryName) {
        this.repositoryName = repositoryName;
    }

    public String getBuildDirectory() {
        return buildDirectory;
    }

    public void setBuildDirectory(String buildDirectory) {
        this.buildDirectory = buildDirectory;
    }

    public int getGenerationsToKeep() {
        return generationsToKeep;
    }

    public void setGenerationsToKeep(int generationsToKeep) {
        this.generationsToKeep = generationsToKeep;
    }
}