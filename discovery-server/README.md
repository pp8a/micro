# Discovery Server

## Overview
This is the Discovery Server (Eureka Server) for service discovery.

## Prerequisites
- Java 17
- Maven

## Building the Project
To build the project, use the following command:
`mvn clean install`
## Running the Project
To run the project locally, use the following command:
`java -XX:+EnableDynamicAgentLoading -jar target/discovery-server.jar`
## Running with Docker
To build and run the Docker container, use the following commands:
### Build Docker Image 
`docker build -t discovery-server .`
### Run Docker Container
`docker run -p 8761:8761 discovery-server`  