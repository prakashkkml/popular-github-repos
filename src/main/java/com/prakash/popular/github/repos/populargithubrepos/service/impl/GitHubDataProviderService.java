package com.prakash.popular.github.repos.populargithubrepos.service.impl;

import com.prakash.popular.github.repos.populargithubrepos.model.GithubDataResponse;
import com.prakash.popular.github.repos.populargithubrepos.service.DataProviderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.reactive.function.client.WebClient;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;

@Service
@AllArgsConstructor
@Slf4j
public class GitHubDataProviderService implements DataProviderService {
    private static final String REPOSITORY_CACHE = "repositoryCache";
    private static final String GIT_HUB_DATA_PROVIDER_SERVICE = "gitHubDataProviderService";
    private static final String GITHUB_REPOSITORIES_URL = "https://api.github.com/search/repositories?q=";
    private static final String SORT_BY_STARS_AND_DEFAULT_PAGE_COUNT = "sort=stars&order=desc&per_page=100";

    private final WebClient webClient;

    /**
     * This method retrieves the data from GitHub API with the default page size as 100
     *
     * @param fromDate
     * @param language
     * @return
     */
    @Override
    @Cacheable(value = REPOSITORY_CACHE, keyGenerator = "repositoryCacheKeyGenerator")
    @CircuitBreaker(name = GIT_HUB_DATA_PROVIDER_SERVICE, fallbackMethod = "returnEmptyGithubDataResponse")
    public GithubDataResponse getData(Date fromDate, String language) {
        GithubDataResponse githubDataResponse = webClient.get().uri(getEndPoint(fromDate, language))
                .retrieve()
                .bodyToMono(GithubDataResponse.class)
                .block();
        log.debug("GitHub API invoked to fetch data from {} with language {}", fromDate, language);
        return githubDataResponse;
    }

    /**
     * If GitHub API call fails, this fall back method will get executed.
     *
     * @param ex
     * @return
     */
    private GithubDataResponse returnEmptyGithubDataResponse(Exception ex) {
        log.error("fetching GitHub API data failed due to an exception {} and empty response returned", ex.getMessage());
        GithubDataResponse dataResponse = new GithubDataResponse();
        dataResponse.setTotalCount(0);
        dataResponse.setIncompleteResults(true);
        dataResponse.setItems(Collections.emptyList());
        return dataResponse;
    }

    /**
     * generate GitHub API endpoint
     *
     * @param fromDate from Date
     * @param language language input
     * @return
     */
    private String getEndPoint(Date fromDate, String language) {
        StringBuilder resultUrl = new StringBuilder(GITHUB_REPOSITORIES_URL);
        if (!ObjectUtils.isEmpty(language))
            resultUrl.append("language:").append(language).append("+");
        if (!ObjectUtils.isEmpty(fromDate)) {
            resultUrl.append("created:>").append(new SimpleDateFormat("yyyy-MM-dd").format(fromDate)).append("&");
        }
        return resultUrl.append(SORT_BY_STARS_AND_DEFAULT_PAGE_COUNT).toString();
    }
}
