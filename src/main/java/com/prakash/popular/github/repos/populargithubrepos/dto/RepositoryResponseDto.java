package com.prakash.popular.github.repos.populargithubrepos.dto;

import com.prakash.popular.github.repos.populargithubrepos.model.RepositoryItem;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class RepositoryResponseDto {
    private Integer totalCount;
    private List<RepositoryItem> repositoryItems;
}
