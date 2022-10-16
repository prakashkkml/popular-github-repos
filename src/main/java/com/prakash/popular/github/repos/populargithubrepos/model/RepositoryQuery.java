package com.prakash.popular.github.repos.populargithubrepos.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@Builder
public class RepositoryQuery {
    @NotNull
    private Date fromDate;
    private String language;
    @Min(message = "Query size should be greater than 0", value = 0)
    private Integer querySize;
}
