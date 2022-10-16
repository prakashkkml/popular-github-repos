package com.prakash.popular.github.repos.populargithubrepos.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

@Data
public class RepositoryItem {
    private Long id;
    @JsonProperty("full_name")
    private String fullName;
    private String description;
    private String url;
    private String language;
    @JsonProperty("created_at")
    private Date createdOn;
    private String visibility;
    private Integer forks;
    private Integer score;
    private RepoOwner owner;
}
