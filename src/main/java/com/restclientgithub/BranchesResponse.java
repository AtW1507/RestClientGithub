package com.restclientgithub;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record BranchesResponse(String name, Commit commit) {
}
