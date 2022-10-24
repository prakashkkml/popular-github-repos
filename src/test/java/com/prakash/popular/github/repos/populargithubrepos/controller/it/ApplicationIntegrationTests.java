package com.prakash.popular.github.repos.populargithubrepos.controller.it;

import com.prakash.popular.github.repos.populargithubrepos.dto.RepositoryResponseDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpHeaders.ACCEPT;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ApplicationIntegrationTests {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    @DisplayName("When all data is requested then all data should be returned")
    void testAllPopularRepositoriesWithPageSize() {
        Integer pageSize = 100;
        String fromDate = "2022-10-10";
        RepositoryResponseDto responseDto = this.webTestClient
                .get()
                .uri("/api/v1/repository?fromDate=" + fromDate + "&querySize=" + pageSize)
                .header(ACCEPT, APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectHeader()
                .contentType(APPLICATION_JSON)
                .returnResult(RepositoryResponseDto.class)
                .getResponseBody().blockFirst();
        assertThat(responseDto.getRepositoryItems().size()).isEqualTo(pageSize);
    }

    @Test
    @DisplayName("When a language filter is applied then only the repositories in that language should be returned")
    void testLanguageFilterInRepositories() {
        // arrange
        Integer pageSize = 10;
        String fromDate = "2022-10-10";
        String language = "JAVA";

        // act
        RepositoryResponseDto responseDto = this.webTestClient
                .get()
                .uri("/api/v1/repository?fromDate=" + fromDate + "&language=" + language + "&querySize=" + pageSize)
                .header(ACCEPT, APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectHeader()
                .contentType(APPLICATION_JSON)
                .returnResult(RepositoryResponseDto.class)
                .getResponseBody().blockFirst();

        // assert
            responseDto.getRepositoryItems().stream().forEach(x -> assertThat(x.getLanguage()).isEqualToIgnoringCase(language));
    }

    @Test
    @DisplayName("When the request is having invalid date then it should return bad request")
    void testInvalidFromDate() {
        // arrange
        Integer pageSize = 10;
        // date in future
        String fromDate = "2030-10-10";

        // act
        this.webTestClient
                .get()
                .uri("/api/v1/repository?fromDate=" + fromDate + "&querySize=" + pageSize)
                .header(ACCEPT, APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus()
                .is4xxClientError()
                .expectBody()
                .jsonPath("$.error").isEqualTo("Bad Request");

    }

    @Test
    @DisplayName("When the request is having invalid page size then it should return bad request")
    void testInvalidPageSize() {
        // arrange
        Integer pageSize = -10;
        // date in future
        String fromDate = "2022-10-10";

        // act
        this.webTestClient
                .get()
                .uri("/api/v1/repository?fromDate=" + fromDate + "&querySize=" + pageSize)
                .header(ACCEPT, APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus()
                .is4xxClientError()
                .expectBody()
                .jsonPath("$.error").isEqualTo("Bad Request");

    }

}
