# US Mobile App

This is a [Spring Boot](http://projects.spring.io/spring-boot/) microservice which consists of crucial endpoints with respect to Users Registration, Billing Cycle Subscription and Retrieving Daily Usage Statistics

### Contents

- [Functionalities](#functionalities)
- [Requirements](#requirements)
- [Local Installation and Running](#running-the-application-locally)
- [Running the Docker Container](#docker)
- [Testing Framework](#testing)
- [Credentials](#credentials)

## Functionalities

The app consists of 3 Major Entities - User, Cycle, Daily Usage

#### User:-

Attributes:- FirstName, LastName,

## Requirements

For building and running the application you need:

- [JDK 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
- [Gradle](https://gradle.org/)
- [Spring boot 3.x](https://spring.io/projects/spring-boot)
- [Docker](https://docs.docker.com/get-docker/)

## Running the application locally

Run the below gradle task command from the root location to clean and build the project

```
./gradlew clean build
```

Run the spring boot application

```
./gradlew bootRun
```

## Running the application on Docker

Run the below command at the root location to build the docker image

```
docker build -t us-mobile-service:latest .
```

Below command to start the container with the generated docker image

```
docker build -t us-mobile-service:latest .
```

In both the above cases, you can access the spring boot application at below url

```
http://localhost:8080
```

## Running the test cases

In the src/main/resources/application.properties file

- uncomment the below properties

```
#### testcontainer mongo configs
#spring.data.mongodb.database=OAuth2Sample
#spring.data.mongodb.port=${mongodb.container.port}
#spring.data.mongodb.host=localhost
#spring.data.mongodb.auto-index-creation=true
```

- comment the below properties

```
#### main mongo configs
spring.application.name=usmobile
spring.data.mongodb.uri=mongodb+srv://adityajadhavncsu:2NH6PU3eJoGPr9Gs@cluster0.xqzlrqf.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0
spring.data.mongodb.database=usmobile
```

For detailed list of all test and main classes and methods, please load the doc/index.html (javadocs) file into the browser

## Copyright

Released under the Apache License 2.0. See the [LICENSE](https://github.com/codecentric/springboot-sample-app/blob/master/LICENSE) file.
