# Library

This Spring Boot application manages a library system with entities like books, authors, members, and borrowing records. It integrates Docker for containerization and deployment. Key features include CRUD operations via JPA, auto-configuration, and embedded servers. Use Gradle for building and Docker Compose for deploying the application.

![library](https://github.com/ArabkhanDev/TercumeApplicationu/assets/114146863/ac7c79a5-e95c-4e3f-bbba-b9afa1bcc11c)


## Table of Contents

- [Project Overview](#project-overview)
- [Prerequisites](#prerequisites)
- [Building and Running the Application](#building-and-running-the-application)
  - [Building the Application](#building-the-application)

## Project Overview

This project is a Spring Boot application that uses various Spring Boot starters for data access, web, and security functionalities. It also integrates with Docker for containerization and deployment.

## Prerequisites

- Java 17
- Spring Boot
- Spring Security
- Docker
- Docker Compose
- Git
- Liquibase
- JUnit 5

## Building and Running the Application

### Building the Application

To build the application, run:

```sh
./gradlew clean build

docker-compose up -d

./gradlew bootRun
