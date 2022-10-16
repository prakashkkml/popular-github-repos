
# Application for listing popular GitHub APIs

A simple project for listing the popular github repositories along with filtering options like filter APIs by date, language and page size.

## Authors

- [@prakashkkml](https://github.com/prakashkkml)
## Tech Stack

**Back End:** Java, Spring Boot, Rest API, Swagger, Junit, Mockito

## Features

- Used LOMBOK to remove boiler plate code
- Used WebClient to make synchronous REST communications (Resttemplate is getting deprecated https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/client/RestTemplate.html)
- Spring Caching
- Risk tolerant, used resilience4j circuit breaker
- Live Demo
- Unit & Integration Tests
- Documentation

## Supported Operations

#### Get all repositories

```http
  GET /api/v1/repository
```
| Request Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `fromDate`      | `Date` | **Required**. To specify the start date from when the APIs filtering should be started. Accepts only the ISO Date format **yyyy-MM-dd**|
| `language`| `string` | To apply language filtering |
| `querySize`      | `Integer` | To specify the page size, default value is **10**|


## Demo Live

https://popular-github-repositories.herokuapp.com/swagger-ui/index.html

