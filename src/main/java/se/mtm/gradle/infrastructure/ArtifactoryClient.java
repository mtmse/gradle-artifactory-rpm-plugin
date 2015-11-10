package se.mtm.gradle.infrastructure;

import com.fasterxml.jackson.databind.ObjectMapper;
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

public class ArtifactoryClient {
    private static final String ARTIFACTORY_USER = "ARTIFACTORY_USER";
    private static final String ARTIFACTORY_PASSWORD = "ARTIFACTORY_PASSWORD";

    public static void upLoad(Artifact artifact, String repository, String artifactoryHost) throws IOException {
        Entity<File> entity = Entity.entity(artifact.getFile(), MediaType.APPLICATION_OCTET_STREAM_TYPE);

        Client artifactoryClient = ArtifactoryClient.getConnector();
        WebTarget target = artifactoryClient.target(artifactoryHost + "/" + repository + "/" + artifact.getFileName());

        Response response = target
                .request()
                .put(entity);

        if (response.getStatus() != 201) {
            throw new DeployRpmException(artifact, repository, artifactoryHost);
        }
    }

    public static RepositoryContent getRepositoryContent(String repository, String artifactoryHost) throws IOException {
        Client artifactoryClient = getConnector();

        Response response = artifactoryClient.target(artifactoryHost)
                .path("api/search/pattern")
                .queryParam("pattern", repository + ":*.rpm")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .get();

        if (response.getStatus() != 200) {
            throw new FindRpmException(repository, artifactoryHost);
        }

        ObjectMapper mapper = new ObjectMapper();
        String repositoryContentJson = response.readEntity(String.class);
        return mapper.readValue(repositoryContentJson, RepositoryContent.class);
    }

    public static void purgeOld(Artifact artifact, String repository, String artifactoryHost) {
        Client artifactoryClient = ArtifactoryClient.getConnector();
        WebTarget target = artifactoryClient.target(artifactoryHost + "/" + repository + "/" + artifact.getFileName());

        Response response = target
                .request()
                .delete();

        if (response.getStatus() != 204) {
            throw new PurgeRpmException(artifact, repository, artifactoryHost);
        }
    }

    public static void triggerIndexRecalculation(String repository, String artifactoryHost) {
        Client artifactoryClient = getConnector();

        Response response = artifactoryClient.target(artifactoryHost)
                .path("api/yum/" + repository)
                .request(MediaType.TEXT_PLAIN)
                .post(Entity.entity(null, "application/text"));

        if (response.getStatus() != 200) {
            throw new RecalculateYumIndexException(repository, artifactoryHost);
        }
    }

    public static void copyArtifact(String artifactName, String srcRepository, String targetRepository, String artifactoryHost) {
        Client artifactoryClient = getConnector();

        String source = "api/copy" + "/" + srcRepository + "/" + artifactName;
        String target = "/" + targetRepository + "/" + artifactName;
        Response response = artifactoryClient.target(artifactoryHost)
                .path(source)
                .queryParam("to", target)
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(null, "application/vnd.org.jfrog.artifactory.storage.CopyOrMoveResult+json"));

        if (response.getStatus() != 200) {
            throw new PromoteRpmException(artifactName, srcRepository, targetRepository, artifactoryHost);
        }
    }

    private static Client getConnector() {
        // return getGrizzlyConnector();
        return getDefaultConnector();
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
