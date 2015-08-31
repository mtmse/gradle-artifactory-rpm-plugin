package se.mtm.gradle.infrastructure;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.ws.rs.core.Response;
import java.io.IOException;

public class FindRpms {
    public static RepositoryContent in(String repository, String artifactoryHost) throws IOException {
        Response response = ArtifactoryClient.getRepositoryContent(repository, artifactoryHost);

        if (response.getStatus() != 200) {
            throw new FindRpmException(repository, artifactoryHost);
        }

        ObjectMapper mapper = new ObjectMapper();
        String repositoryContentJson = response.readEntity(String.class);
        return mapper.readValue(repositoryContentJson, RepositoryContent.class);
    }
}
