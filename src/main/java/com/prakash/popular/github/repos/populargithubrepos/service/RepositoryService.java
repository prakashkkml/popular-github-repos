package com.prakash.popular.github.repos.populargithubrepos.service;

import com.prakash.popular.github.repos.populargithubrepos.dto.RepositoryResponseDto;
import com.prakash.popular.github.repos.populargithubrepos.model.RepositoryQuery;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

@Validated
public interface RepositoryService {
    RepositoryResponseDto getRepositories(@Valid RepositoryQuery repositoryQuery);
}
