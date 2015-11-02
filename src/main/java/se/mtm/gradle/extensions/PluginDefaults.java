package se.mtm.gradle.extensions;

import se.mtm.gradle.infrastructure.ConfigurationException;

import java.io.File;

public class PluginDefaults {
    private String repositoryServerUrl = "http://artifactory.mtm.se:8081/artifactory";
    private String stagingRepo = "mtm-staging";
    private String stageRepo = "mtm-staging";
    private String utvRepo = "mtm-utv";
    private String testRepo = "mtm-test";
    private String prodRepo = "mtm-production";
    private String promotionRepo = "mtm-utv";
    private String[] purgeRepos;
    private String distributionDir = "distributions";
    private int generationsToKeep = 3;

    boolean isDistributionDirChanged = false;
    private File buildDir;

    public PluginDefaults() {
        purgeRepos = new String[2];
        purgeRepos[0] = stagingRepo;
        purgeRepos[1] = promotionRepo;
    }

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

    public String getStagingRepo() {
        return stagingRepo;
    }

    public void setStagingRepo(String stagingRepo) {
        this.stagingRepo = stagingRepo;
    }

    public String getPromotionRepo() {
        return promotionRepo;
    }

    public void setPromotionRepo(String promotionRepo) {
        this.promotionRepo = promotionRepo;
    }

    public String[] getPurgeRepos() {
        return purgeRepos;
    }

    public void setPurgeRepos(String[] purgeRepos) {
        this.purgeRepos = purgeRepos;
    }

    public String getDistributionDir() {
        if (isDistributionDirChanged) {
            return distributionDir;
        }

        try {
            return buildDir.getCanonicalPath() + File.separator + distributionDir;
        } catch (Exception e) {
            throw new ConfigurationException(e.getMessage(), e);
        }
    }

    public void setDistributionDir(String distributionDir) {
        isDistributionDirChanged = true;
        this.distributionDir = distributionDir;
    }

    public int getGenerationsToKeep() {
        return generationsToKeep;
    }

    public void setGenerationsToKeep(int generationsToKeep) {
        this.generationsToKeep = generationsToKeep;
    }

    public void setBuildDir(File buildDir) {
        this.buildDir = buildDir;
    }
}
