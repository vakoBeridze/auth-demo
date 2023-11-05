# auth-demo

Authentication demo project using Spring Security and Keycloak.

## Structure

The project is divided into 3 modules:

- [auth-server](auth-server) - Keycloak server, demonstrating how to configure Keycloak using Docker and Docker Compose.
- [bootcamp-service](bootcamp-service) - Spring Boot application, demonstrating form-based and OAuth2 authentication and
  authorization.
- [library-service](library-service) - Spring Boot application, demonstrating JWT based authentication and
  authorization.

## Prerequisites

- Docker
- Docker Compose
- Java 11
- Maven

## Running the project

1. Start the Keycloak server by running `docker-compose up` in the `auth-server` directory.
2. Start the `bootcamp-service` and `library-service` applications by running `mvn spring-boot:run` in their respective
   directories.
3. Open the `bootcamp-service` application in your browser at http://localhost:8080.
4. Open the `library-service` application in your browser at http://localhost:8081.

## Users and roles

In this demo there are 3 roles:

- `STUDENT` - can do GET operations in both applications.
- `TEACHER` - can do modification and deletion operations in the `bootcamp-service` application.
- `LIBRARY_ADMIN` - can do modification and deletion operations in the `library-service` application.

There are 3 users in auth-server (These roles are effective only in both applications):

- `jon` - has the `STUDENT` role.
- `james` - has the `TEACHER` role.
- `laura` - has the `LIBRARY_ADMIN` role.

There are built-in users in bootcamp-service (These roles are effective only in the `bootcamp-service`):

- `student` - has the `STUDENT` role.
- `teacher` - has the `TEACHER` role.

## Diagrams

### Code flow - AUTHORIZATION_CODE

The following diagram shows the code flow, which is used in the `bootcamp-service` application.
Bootcamp service saves the access token in the session, so that it can be used in subsequent requests.

![code_flow.png](api-docs%2Fdiagrams%2Fcode_flow.png)

### JWT authentication

The following diagram shows the JWT authentication flow, which is used in the `library-service` application.
Library service is stateless, so it doesn't save the access token, instead it validates it in every request.

![jwt_auth.png](api-docs%2Fdiagrams%2Fjwt_auth.png)
