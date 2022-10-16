package com.prakash.popular.github.repos.populargithubrepos.service.impl;

import com.prakash.popular.github.repos.populargithubrepos.dto.RepositoryResponseDto;
import com.prakash.popular.github.repos.populargithubrepos.model.GithubDataResponse;
import com.prakash.popular.github.repos.populargithubrepos.model.RepositoryItem;
import com.prakash.popular.github.repos.populargithubrepos.model.RepositoryQuery;
import com.prakash.popular.github.repos.populargithubrepos.service.DataProviderService;
import com.prakash.popular.github.repos.populargithubrepos.service.RepositoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class DefaultRepositoryService implements RepositoryService {

    private final DataProviderService dataProviderService;

    /**
     * Return repository response data based on the repository query
     *
     * @param repositoryQuery
     * @return
     */
    @Override
    public RepositoryResponseDto getRepositories(RepositoryQuery repositoryQuery) {
        GithubDataResponse githubDataResponse = dataProviderService.getData(repositoryQuery.getFromDate(), repositoryQuery.getLanguage());
        List<RepositoryItem> repositoryItems = githubDataResponse.getItems().size() > repositoryQuery.getQuerySize() ?
                githubDataResponse.getItems().subList(0, repositoryQuery.getQuerySize()) : githubDataResponse.getItems();
        return new RepositoryResponseDto(githubDataResponse.getTotalCount(), repositoryItems);
    }
}
