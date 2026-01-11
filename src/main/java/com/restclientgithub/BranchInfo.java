package com.restclientgithub;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record BranchInfo(String name, String lastCommit) {
}
