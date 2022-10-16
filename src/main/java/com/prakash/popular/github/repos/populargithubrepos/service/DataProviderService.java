package com.prakash.popular.github.repos.populargithubrepos.service;

import com.prakash.popular.github.repos.populargithubrepos.model.GithubDataResponse;

import java.util.Date;


public interface DataProviderService {
    GithubDataResponse getData(Date fromDate, String language);
}
