package com.prakash.popular.github.repos.populargithubrepos.service;

import com.prakash.popular.github.repos.populargithubrepos.dto.RepositoryResponseDto;
import com.prakash.popular.github.repos.populargithubrepos.model.GithubDataResponse;
import com.prakash.popular.github.repos.populargithubrepos.model.RepositoryItem;
import com.prakash.popular.github.repos.populargithubrepos.model.RepositoryQuery;
import com.prakash.popular.github.repos.populargithubrepos.service.impl.DefaultRepositoryService;
import com.prakash.popular.github.repos.populargithubrepos.service.impl.GitHubDataProviderService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;

@ExtendWith(MockitoExtension.class)
class DefaultRepositoryServiceTest {

    @Mock
    GitHubDataProviderService gitHubDataProviderService;

    @InjectMocks
    DefaultRepositoryService defaultRepositoryService;

    @Test
    @DisplayName("Search repositories with different Query size")
    void testRepositoriesWithRepositoryQuerySize() {
        int expectedQuerySize = 2;
        int expectedRepoSize = 25;
        int expectedTotalCount = 100;

        // arrange
        GithubDataResponse githubDataResponse = new GithubDataResponse();
        githubDataResponse.setTotalCount(expectedTotalCount);
        githubDataResponse.setItems(Arrays.asList(new RepositoryItem[expectedRepoSize]));
        Mockito.when(gitHubDataProviderService.getData(any(Date.class), anyString())).thenReturn(githubDataResponse);

        // act
        RepositoryResponseDto responseDto = defaultRepositoryService.getRepositories(RepositoryQuery.builder()
                .fromDate(new Date())
                .language("Java")
                .querySize(expectedQuerySize).build());

        // assert
        assertNotNull(responseDto);
        assertNotNull(responseDto.getRepositoryItems());
        assertEquals(expectedQuerySize, responseDto.getRepositoryItems().size());

        // testing with more than expectedRepoSize
        responseDto = defaultRepositoryService.getRepositories(RepositoryQuery.builder()
                .fromDate(new Date())
                .language("Java")
                .querySize(50).build());

        // assert
        assertNotNull(responseDto);
        assertEquals(expectedTotalCount, responseDto.getTotalCount());
        assertNotNull(responseDto.getRepositoryItems());
        assertEquals(expectedRepoSize, responseDto.getRepositoryItems().size());
    }
}