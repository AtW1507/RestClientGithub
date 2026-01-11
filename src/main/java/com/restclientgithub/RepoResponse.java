package com.restclientgithub;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record RepoResponse(Owner owner,
                           String name,
                           boolean fork) {
}
