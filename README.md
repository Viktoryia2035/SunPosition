# Sunrise Sunset Service

***
This service takes the coordinates of a location and a date as input and returns the sunrise and sunset times for that location on the specified date.
***
## Contents
- [Task 1](#task-1)
- [Task 2](#task-2)
- [Task 3](#task-3)
- [Task 4](#task-4)
- [Task 5](#task-5)
- [Task 6](#task-6)
- [HTTP requests](#HTTP-requests)
- [SonarCloud](#sonarCloud)
***

## Task 1
1. Create and run locally the simplest web/REST service using any open source example using Java stack: Spring (Spring Boot)/maven/gradle/Jersey/Spring MVC. 
2. Add a GET endpoint that accepts input parameters as query Params in the URL according to the option, and returns any hard-coded result in the form of JSON according to the option.
***

## Task 2
1. Connect a database to the project (PostgreSQL/MySQL/и т.д.).
- (0 - 7 points) - implementation of one-to-many communication;
- (8 - 10 points) - implementation of many-to-many communication.
2. Implement CRUD operations with all entities.
***

## Task 3
1. Add an endpoint to the GET project (it should be useful) with the parameter(s). The data must be obtained from the database using a "custom" query (@Query) with parameter(s) to the nested entity.
2. Add the simplest cache in the form of an in-memory Map (as a separate bin).
***

## Task 4
1. Handle 400 and 500 errors.
2. Add logging of actions and errors (aspects).
3. Connect Swagger & CheckStyle. To remove stylistic errors.
***

## Task 5
1. Add a POST method to work with a list of parameters (passed in the request body) for bulk operations, organize the service using Java 8 (Stream API, lambda expressions).
2. Unit test coverage by >80% (business logic).
***

## Task 6
1. Add a service for counting requests to the main service. The counter must be implemented as a separate class, access to which must be synchronized.
2. Using jmeter/postman or any other means, configure the load test and make sure that the request counter is working correctly under heavy load.
***

## HTTP requests
+ GET localhost:8080/api/v2/country - show all saved contries
+ GET localhost:8080/api/v2/sunrise_sunset - show all saved days
+ POST localhost:8080/api/v2/country/saveCountry - save data
+ DELETE localhost:8080/api/v2/country/deleteById - delete data by id
+ PATCH localhost:8080/api/v2/country/updateByName - updating the country name by its current name
+ PUT localhost:8080/api/v2/sunrise_sunset/updateSunriseSunset - sunrise and sunset time updates for a given location and date
+ GET localhost:8080/api/v2/country/findByNameAndWeather - show country data by its name and weather conditions in the city
***

## SonarCloud
You can view the results of Sonar Cloud at this link: https://sonarcloud.io/project/overview?id=Viktoryia2035_JavaLabs
