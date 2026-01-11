package com.restclientgithub;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import java.util.Arrays;
import java.util.List;

@Component
public class GithubClient {
    private final RestClient restClient;
    private final String githubBaseUrl;

    public GithubClient(RestClient restClient,
                        @Value("${github.api.url}")
                        String githubBaseUrl) {
        this.restClient = restClient;
        this.githubBaseUrl = githubBaseUrl;
    }

    public List<RepoResponse> getRepositories(String username) {
        try {
            RepoResponse[] reposArray = restClient.get()
                    .uri(githubBaseUrl + "/users/{username}/repos", username)
                    .retrieve()
                    .body(RepoResponse[].class);
            return reposArray == null ? List.of() : Arrays.asList(reposArray);

        } catch (HttpClientErrorException.NotFound e) {
            throw new UserNotFoundException("User not found: " + username);
        }

    }


    public List<BranchesResponse> getBranches(String owner, String repoName) {
        BranchesResponse[] branchesArray = restClient.get().uri(githubBaseUrl + "/repos/{owner}/{repoName}/branches", owner, repoName)
                .retrieve()
                .body(BranchesResponse[].class);
        return branchesArray == null ? List.of() : Arrays.asList(branchesArray);
    }
}

