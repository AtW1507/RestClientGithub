package com.restclientgithub;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.NONE,
        properties = "github.api.url=http://localhost:8089"
)
class GithubIntegrationTest {

    static WireMockServer wireMockServer;

    @Autowired
    GithubService service;

    @BeforeAll
    static void startWireMock() {
        wireMockServer = new WireMockServer(8089);
        wireMockServer.start();
        configureFor("localhost", 8089);
    }

    @AfterAll
    static void stopWireMock() {
        wireMockServer.stop();
    }

    @Test
    void shouldReturnRepositoriesWithoutForks() {
        stubFor(get(urlEqualTo("/users/testuser/repos"))
                .willReturn(okJson("""
                                [
                                  { "name": "repo-1", "fork": false, "owner": { "login": "testuser" } },
                                  { "name": "repo-2", "fork": true, "owner": { "login": "testuser" } }
                                ]
                        """)));

        stubFor(get(urlEqualTo("/repos/testuser/repo-1/branches"))
                .willReturn(okJson("""
                                [
                                  { "name": "main", "commit": { "sha": "abc123" } },
                                  { "name": "dev", "commit": { "sha": "def456" } }
                                ]
                        """)));

        List<RepositoryInfo> repositories =
                service.getRepositoriesForUser("testuser");

        assertThat(repositories).hasSize(1);

        RepositoryInfo repo = repositories.get(0);
        assertThat(repo.repositoryName()).isEqualTo("repo-1");
        assertThat(repo.ownerLogin()).isEqualTo("testuser");
        assertThat(repo.branches()).hasSize(2);
        assertThat(repo.branches().getFirst().name()).isEqualTo("main");
        assertThat(repo.branches().getFirst().lastCommit()).isEqualTo("abc123");
    }

    @Test
    void shouldThrowUserNotFoundException() {
        stubFor(get(urlEqualTo("/users/nonexistent/repos"))
                .willReturn(aResponse().withStatus(404)));

        assertThrows(
                UserNotFoundException.class,
                () -> service.getRepositoriesForUser("nonexistent")
        );
    }
}