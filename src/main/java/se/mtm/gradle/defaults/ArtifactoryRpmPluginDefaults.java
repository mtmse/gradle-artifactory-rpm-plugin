package se.mtm.gradle.defaults;

public class ArtifactoryRpmPluginDefaults {
    private String repositoryServerUrl = "http://artifactory.mtm.se:8081/artifactory";
    private String repositoryName = "mtm-utv";
    private String userName = "undefinedUser";
    private String password = "undefinedPassword";

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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}