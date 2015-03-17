package se.mtm.gradle.infrastructure;

import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

class Util {
    private static final String ARTIFACTORY_USER = "ARTIFACTORY_USER";
    private static final String ARTIFACTORY_PASSWORD = "ARTIFACTORY_PASSWORD";

    static Client getAuthenticatedArtifactoryClient() {
        Client client = ClientBuilder.newClient();
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
            throw new CredentialsMissingException("The environment variable " +ARTIFACTORY_PASSWORD + " must be set and contain a password for a user that has credentials to upload artifacts to Artifactory");
        }

        return HttpAuthenticationFeature.basicBuilder()
                .credentials(user, password)
                .build();
    }
}
