package com.restclientgithub;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class GithubController {

    private final GithubService githubService;

    public GithubController(GithubService githubService) {
        this.githubService = githubService;
    }

    @GetMapping("/{username}/repositories")
    public ResponseEntity<List<RepositoryInfo>> getUserRepository(@PathVariable String username) {

        List<RepositoryInfo> repositories = githubService.getRepositoriesForUser(username);
        return ResponseEntity.ok(repositories);

    }

}
