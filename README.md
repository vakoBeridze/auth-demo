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

## Technical description

Spring uses security filter chain to authenticate and authorize users. The filter chain is configured
in `SecurityConfig.java` class.

High level overview of spring security filter chain:

![securityfilterchain.png](api-docs%2Fdiagrams%2Fsecurityfilterchain.png)

### bootcamp-service

Bootcamp service provides 2 types of authentication:

#### 1. form based authentication

To configure form based authentication we add `.formLogin()` to SecurityConfiguration class. This will enable default
login page provided by spring security.

#### 2. oauth2 authentication

To configure oauth2 authentication we add `.oauth2Login()` to SecurityConfiguration class.

`bootcamp-service` acts as [oauth2 client](https://datatracker.ietf.org/doc/html/rfc6749#section-1.1), it has dependency
to `spring-boot-starter-oauth2-client` which provides the necessary classes to authenticate and authorize users.

> In spring security, OAuth 2.0 Login is implemented by using
> the [Authorization Code Grant](https://datatracker.ietf.org/doc/html/rfc6749#section-4.1)

#### Diagram : Code flow - AUTHORIZATION_CODE

The following diagram shows the code flow, which is used in the `bootcamp-service` application.
Note that, bootcamp service saves the access token in the session, so that it can be used in subsequent requests.

![code_flow.png](api-docs%2Fdiagrams%2Fcode_flow.png)

### library-service

To configure JWT authentication we add `.oauth2ResourceServer()` to SecurityConfiguration class.

`library-service` acts as [resource server](https://datatracker.ietf.org/doc/html/rfc6749#section-1.1), it has
dependency to `spring-boot-starter-oauth2-resource-server` which provides the necessary classes to validate the token
and authorize users.

High level overview of JWT authentication in spring security:

![jwtauthenticationprovider.png](api-docs%2Fdiagrams%2Fjwtauthenticationprovider.png)

#### Diagram: JWT authentication

The following diagram shows the JWT authentication flow, which is used in the `library-service` application.
Library service is stateless, so it doesn't save the access token, instead it validates it in every request.

![jwt_auth.png](api-docs%2Fdiagrams%2Fjwt_auth.png)

## Users and roles

In this demo there are 3 roles:

- `STUDENT` - can do GET operations in both applications.
- `TEACHER` - can do modification and deletion operations in the `bootcamp-service` application.
- `LIBRARY_ADMIN` - can do modification and deletion operations in the `library-service` application.

There are 3 users in auth-server:

- `jon` - Jon Snow - has the `STUDENT` role.
- `james` - James Zanti - has the `TEACHER` role.
- `laura` - Laura Admin - has the `LIBRARY_ADMIN` role.

There are 2 built-in users in bootcamp-service, which are effective only in this application and are useful to test
**form based** authentication:

- `student` - has the `STUDENT` role.
- `teacher` - has the `TEACHER` role.

> Passwords for all users are `test`

## Read more

- [Spring Security Reference](https://docs.spring.io/spring-security/reference/index.html)
- [The OAuth 2.0 Authorization Framework](https://datatracker.ietf.org/doc/html/rfc6749)
- [Using JWTs as Authorization Grants](https://datatracker.ietf.org/doc/html/rfc7523#section-2.2 )
- [Spring Boot and OAuth2](https://spring.io/guides/tutorials/spring-boot-oauth2/)
- [Keycloak](https://www.keycloak.org/)