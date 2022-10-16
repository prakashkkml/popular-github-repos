package com.prakash.popular.github.repos.populargithubrepos.controller;

import com.prakash.popular.github.repos.populargithubrepos.dto.RepositoryResponseDto;
import com.prakash.popular.github.repos.populargithubrepos.exception.InvalidRequestException;
import com.prakash.popular.github.repos.populargithubrepos.model.RepositoryQuery;
import com.prakash.popular.github.repos.populargithubrepos.service.RepositoryService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/api/v1/repository")
@AllArgsConstructor
public class GithubRepoController {

    private final RepositoryService repositoryService;

    @GetMapping
    @Operation(summary = "To fetch the most popular repositories based on the filter applied")
    public ResponseEntity<RepositoryResponseDto> getRepositories(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fromDate,
                                                                 @RequestParam(required = false) String language,
                                                                 @RequestParam(defaultValue = "10", required = false) Integer querySize) throws InvalidRequestException {
        validateDate(fromDate);

        RepositoryResponseDto responseDto = repositoryService.getRepositories(RepositoryQuery.builder()
                .fromDate(fromDate)
                .language(language)
                .querySize(querySize).build());
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    private static void validateDate(Date fromDate) throws InvalidRequestException {
        if (fromDate.getTime() > new Date().getTime()) {
            throw new InvalidRequestException("From date should be either current time or it should be in the past");
        }
    }
}
