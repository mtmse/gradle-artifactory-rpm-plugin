package se.mtm.gradle.extensions;

import org.gradle.api.Project;

public class GradleArtifactoryRpmPluginDefaults {
    private String repositoryServerUrl = "http://artifactory.mtm.se:8081/artifactory";
    private String stageRepo = "mtm-staging";
    private String utvRepo = "mtm-utv";
    private String testRepo = "mtm-test";
    private String prodRepo = "mtm-production";
    private String packageName;
    private int generationsToKeep = 3;

    public String getRepositoryServerUrl() {
        return repositoryServerUrl;
    }

    public void setRepositoryServerUrl(String repositoryServerUrl) {
        this.repositoryServerUrl = repositoryServerUrl;
    }

    public String getStageRepo() {
        return stageRepo;
    }

    public void setStageRepo(String stageRepo) {
        this.stageRepo = stageRepo;
    }

    public String getUtvRepo() {
        return utvRepo;
    }

    public void setUtvRepo(String utvRepo) {
        this.utvRepo = utvRepo;
    }

    public String getTestRepo() {
        return testRepo;
    }

    public void setTestRepo(String testRepo) {
        this.testRepo = testRepo;
    }

    public String getProdRepo() {
        return prodRepo;
    }

    public void setProdRepo(String prodRepo) {
        this.prodRepo = prodRepo;
    }


    public String getPackageName(Project project) {
        if (packageName == null) {
            packageName = project.getName();
        }

        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public int getGenerationsToKeep() {
        return generationsToKeep;
    }

    public void setGenerationsToKeep(int generationsToKeep) {
        this.generationsToKeep = generationsToKeep;
    }
}
