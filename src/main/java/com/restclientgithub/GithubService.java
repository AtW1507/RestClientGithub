package com.restclientgithub;

import java.util.List;

@org.springframework.stereotype.Service
public class GithubService {

    private final GithubClient githubClient;

    public GithubService(GithubClient githubClient) {
        this.githubClient = githubClient;
    }

    public List<RepositoryInfo> getRepositoriesForUser(String username) {
        List<RepoResponse> responseGithubs = githubClient.getRepositories(username);

        return responseGithubs.stream()
                .filter(repo -> !repo.fork())
                .map(repo -> {
                    List<BranchesResponse> branches = githubClient.getBranches(repo.owner().ownerLogin(),
                            repo.name()
                    );

                    List<BranchInfo> branchesInfos = branches.stream()
                            .map(branch -> new BranchInfo(branch.name(),
                                    branch.commit().sha()))
                            .toList();
                    return new RepositoryInfo(repo.name(), repo.owner().ownerLogin(), branchesInfos);
                })
                .toList();

    }
}
