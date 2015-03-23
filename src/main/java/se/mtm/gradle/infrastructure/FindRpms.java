package se.mtm.gradle.infrastructure;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

public class FindRpms {
    public static RepositoryContent in(String repository, String artifactoryHost) throws IOException {
        Client artifactoryClient = ArtifactoryClient.authenticated();

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
}
