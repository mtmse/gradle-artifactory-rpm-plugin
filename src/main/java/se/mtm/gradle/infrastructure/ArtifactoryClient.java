package se.mtm.gradle.infrastructure;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
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
import java.io.IOException;
import java.io.InputStream;

public class ArtifactoryClient {
    private static final String ARTIFACTORY_USER = "ARTIFACTORY_USER";
    private static final String ARTIFACTORY_PASSWORD = "ARTIFACTORY_PASSWORD";

    public static void upLoad(Artifact artifact, String repository, String artifactoryHost) throws IOException {
        InputStream resourceAsStream = FileUtils.openInputStream(artifact.getFile());
        String md5Hash = DigestUtils.md5Hex(resourceAsStream);

        Entity<File> entity = Entity.entity(artifact.getFile(), MediaType.APPLICATION_OCTET_STREAM_TYPE);

        Client artifactoryClient = ArtifactoryClient.getConnector();
        WebTarget target = artifactoryClient.target(artifactoryHost + "/" + repository + "/" + artifact.getFileName());

        Response response = target
                .request()
                        // .header("X-Checksum-Md5", md5Hash)
                .put(entity);

        if (response.getStatus() != 201) {
            throw new DeployRpmException(artifact, repository, artifactoryHost);
        }
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

    /*
    private static Client getApacheConnector() {
        ClientConfig clientConfig = new ClientConfig();
        clientConfig.connectorProvider(new ApacheConnectorProvider());
        Client client = ClientBuilder.newClient(clientConfig);
        client.register(getHttpAuthenticationFeature());

        return client;
    }
    */

    private static HttpAuthenticationFeature getHttpAuthenticationFeature() {
        String user = System.getenv(ARTIFACTORY_USER);
        user = "admin";

        String password = System.getenv(ARTIFACTORY_PASSWORD);
        password = "blackflag#";

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
