package se.mtm.gradle.infrastructure;

import org.glassfish.jersey.apache.connector.ApacheConnectorProvider;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.glassfish.jersey.grizzly.connector.GrizzlyConnectorProvider;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;

public class ArtifactoryClient {
    private static final String ARTIFACTORY_USER = "ARTIFACTORY_USER";
    private static final String ARTIFACTORY_PASSWORD = "ARTIFACTORY_PASSWORD";

    public static Response upLoad(Artifact artifact, String repository, String artifactoryHost, String md5Hash, Entity<File> entity) {
        Client artifactoryClient = ArtifactoryClient.getConnector();
        WebTarget target = artifactoryClient.target(artifactoryHost + "/" + repository + "/" + artifact.getFileName());

        return target
                .request()
                .header("X-Checksum-Md5", md5Hash)
                .put(entity);
    }

    public static Response getRepositoryContent(String repository, String artifactoryHost) {
        Client artifactoryClient = getConnector();

        return artifactoryClient.target(artifactoryHost)
                .path("api/search/pattern")
                .queryParam("pattern", repository + ":*.rpm")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .get();
    }

    public static Response purgeOld(Artifact artifact, String repository, String artifactoryHost) {
        Client artifactoryClient = ArtifactoryClient.getConnector();
        WebTarget target = artifactoryClient.target(artifactoryHost + "/" + repository + "/" + artifact.getFileName());

        return target
                .request()
                .delete();
    }

    private static Client getConnector() {
        return getGrizzlyConnector();
        // return getApacheConnector();
        // return getDefaultConnector();
    }

    private static Client getDefaultConnector() {
        Client client = ClientBuilder.newClient();
        client.register(getHttpAuthenticationFeature());
        return client;
    }

    private static Client getGrizzlyConnector() {
        ClientConfig clientConfig = new ClientConfig();
        clientConfig.connectorProvider(new GrizzlyConnectorProvider());
        Client client = ClientBuilder.newClient(clientConfig);
        client.register(getHttpAuthenticationFeature());

        return client;
    }

    private static Client getApacheConnector() {
        ClientConfig clientConfig = new ClientConfig();
        clientConfig.connectorProvider(new ApacheConnectorProvider());
        Client client = ClientBuilder.newClient(clientConfig);
        client.register(getHttpAuthenticationFeature());

        return client;
    }

    private static HttpAuthenticationFeature getHttpAuthenticationFeature() {
        String user = System.getenv(ARTIFACTORY_USER);
        String password = System.getenv(ARTIFACTORY_PASSWORD);

        if (user == null) {
            throw new CredentialsMissingException("The environment variable " + ARTIFACTORY_USER + " must be set and contain a username for a user that has credentials to upload artifacts to Artifactory");
        }

        if (password == null) {
            throw new CredentialsMissingException("The environment variable " + ARTIFACTORY_PASSWORD + " must be set and contain a password for a user that has credentials to upload artifacts to Artifactory");
        }

        return HttpAuthenticationFeature.basicBuilder()
                .credentials(user, password)
                .build();
    }
}
