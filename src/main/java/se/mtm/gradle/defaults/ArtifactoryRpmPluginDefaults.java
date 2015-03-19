package se.mtm.gradle.defaults;

public class ArtifactoryRpmPluginDefaults {
    private String repositoryServerUrl = "http://artifactory.mtm.se:8081/artifactory";
    private String developmentRepo = "mtm-dev";
    private String distributionDir = "distributions";
    private int generationsToKeep= 1;

    public String getRepositoryServerUrl() {
        return repositoryServerUrl;
    }

    public void setRepositoryServerUrl(String repositoryServerUrl) {
        this.repositoryServerUrl = repositoryServerUrl;
    }

    public String getDevelopmentRepo() {
        return developmentRepo;
    }

    public void setDevelopmentRepo(String developmentRepo) {
        this.developmentRepo = developmentRepo;
    }

    public String getDistributionDir() {
        return distributionDir;
    }

    public void setDistributionDir(String distributionDir) {
        this.distributionDir = distributionDir;
    }

    public int getGenerationsToKeep() {
        return generationsToKeep;
    }

    public void setGenerationsToKeep(int generationsToKeep) {
        this.generationsToKeep = generationsToKeep;
    }
}
