# auth-demo

Authentication demo project using Spring Security and Keycloak.

## Structure

The project is divided into 3 modules:

- [auth-server](auth-server) - Keycloak server demonstrating Keycloak configuration using Docker and Docker Compose.
- [bootcamp-service](bootcamp-service) - Spring Boot application showcasing form-based and OAuth2 authentication and
  authorization.
- [library-service](library-service) - Spring Boot application demonstrating JWT-based authentication and authorization.

## Prerequisites

- Docker
- Docker Compose
- Java 11
- Maven

## Running the Project

1. Start the Keycloak server by navigating to the `auth-server` directory and running `docker compose up`.
2. Start the `bootcamp-service` and `library-service` applications by running `mvn spring-boot:run` in their respective
   directories.
3. Access the applications:
    - Open the `bootcamp-service` application in your browser at http://localhost:8080
    - Open the `library-service` application in your browser at http://localhost:8081

## Technical Description

Spring Security uses a security filter chain to authenticate and authorize users.

![securityfilterchain.png](api-docs%2Fdiagrams%2Fsecurityfilterchain.png)

### bootcamp-service

Bootcamp service provides two types of authentication:

#### 1. Form-based Authentication

To configure form-based authentication, add `.formLogin()` to the `SecurityConfig` class. This enables the
default login page provided by Spring Security.

#### 2. OAuth2 Authentication

To configure OAuth2 authentication, add `.oauth2Login()` to the `SecurityConfig` class.

`bootcamp-service` acts as [oauth2 client](https://datatracker.ietf.org/doc/html/rfc6749#section-1.1), it has dependency
to `spring-boot-starter-oauth2-client` which provides the necessary classes to authenticate and authorize users.

> In spring security, OAuth 2.0 Login is implemented by using
> the [Authorization Code Grant](https://datatracker.ietf.org/doc/html/rfc6749#section-4.1)

#### Diagram: Code Flow - AUTHORIZATION_CODE

The following diagram shows the code flow, which is used in the `bootcamp-service` application.
Note that, bootcamp service saves the access token in the session, so that it can be used in subsequent requests.

![code_flow.png](api-docs%2Fdiagrams%2Fcode_flow.png)

### library-service

To configure JWT authentication, add `.oauth2ResourceServer()` to the `SecurityConfig` class.

`library-service` acts as [resource server](https://datatracker.ietf.org/doc/html/rfc6749#section-1.1), it has
dependency to `spring-boot-starter-oauth2-resource-server` which provides the necessary classes to validate the token
and authorize users.

High level overview of JWT authentication in spring security:

![jwtauthenticationprovider.png](api-docs%2Fdiagrams%2Fjwtauthenticationprovider.png)

In the diagram above, `library-service` replaces:

- the default `(3) JwtDecoder` with a custom implementation, which uses the public key of the Keycloak server to
  validate the token.
- the default `(4) JwtAuthenticationConverter` with a custom implementation, which converts the JWT claims to spring
  security authorities.

#### Diagram: JWT Authentication

The following diagram shows the JWT authentication flow, which is used in the `library-service` application.
Library service is stateless, so it doesn't save the access token, instead it validates it in every request.

![jwt_auth.png](api-docs%2Fdiagrams%2Fjwt_auth.png)

### Keycloak Setup

Keycloak is used for user management and authentication. The `auth-server` module includes a pre-configured Keycloak
instance. Access the Keycloak admin console at [http://localhost:8888/auth](http://localhost:8082/auth) using the
provided credentials.

For newcomers to Keycloak, check the [Keycloak Documentation](https://www.keycloak.org/documentation.html) for more
information on setup and configurations.

## Users and Roles

This demo involves three roles:

- `STUDENT` - Allowed to perform GET operations in both applications.
- `TEACHER` - Permitted for modification and deletion operations in the `bootcamp-service`.
- `LIBRARY_ADMIN` - Authorized for modification and deletion operations in the `library-service`.

In the Keycloak server, there are three users:

- `jon` - Jon Snow - assigned the `STUDENT` role.
- `james` - James Zanti - assigned the `TEACHER` role.
- `laura` - Laura Admin - assigned the `LIBRARY_ADMIN` role.

Additionally, the `bootcamp-service` has two built-in users:

- `student` - assigned the `STUDENT` role.
- `teacher` - assigned the `TEACHER` role.

All users have the password set as `test`.

## Read more

- [Spring Security Reference](https://docs.spring.io/spring-security/reference/index.html)
- [The OAuth 2.0 Authorization Framework](https://datatracker.ietf.org/doc/html/rfc6749)
- [Using JWTs as Authorization Grants](https://datatracker.ietf.org/doc/html/rfc7523#section-2.2)
- [Spring Boot and OAuth2](https://spring.io/guides/tutorials/spring-boot-oauth2/)
- [Keycloak](https://www.keycloak.org/)
