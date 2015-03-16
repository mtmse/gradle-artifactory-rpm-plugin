package se.mtm.gradle.infrastructure;

import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

class Util {
    static Client getAuthenticatedArtifactoryClient() {
        Client client = ClientBuilder.newClient();
        client.register(getHttpAuthenticationFeature());
        return client;
    }

    private static HttpAuthenticationFeature getHttpAuthenticationFeature() {
        String user = System.getenv("ARTIFACTORY_USER");
        String password = System.getenv("ARTIFACTORY_PASSWORD");

        return HttpAuthenticationFeature.basicBuilder()
                .credentials(user, password)
                .build();
    }
}
