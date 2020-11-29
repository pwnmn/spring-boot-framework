## Spring Boot Framework

### Purpose

This framework combines common functionality such as error handling, security and other configurations.

Following concerns can be configured

- Spring Security & Keycloak integration
- Keycloak Multi-tenant support
- Swagger-UI 
- Jackson Configuration (Serialization/Deserialization)
- Error Handling & Exceptions
- Utilities (JWT, REST stopwatch etc.)

### Configuration

When included as a dependency in a spring boot microservice, it can be configured in the application.yml.

#### Swagger-UI

Swagger-UI can be enabled or disabled.

~~~~
framework:
  swagger:
    enable: true
~~~~
    
#### CORS

When CORS is enabled, it will allow all HTTP methods with the configured domains. Some examples can be found below.

~~~~
framework:
  cors:
    enable: true
    domains:
      - "google.com"
      - "google.com"
      - "*.google.com"
      - "*"
~~~~

#### Security Whitelisting

URLs and HTTP methods can be whitelisted. Either both, URLs and HTTP methods, are provided
or only URLs. When only URLs are provided, all HTTP methods will be allowed.
These URLs will be completely excluded by Spring Security.


##### URLs and HTTP methods
~~~~
  security:
    whitelist:
      - url: "/path/a"
        method: "POST"
      - url: "/path/b/**"
        method: "PUT"
      - url: "/path/c/**"
        method: "DELETE"
~~~~

##### URLs only

The below will whitelist all HTTP methods under the selected paths.
~~~~
  security:
    whitelist:
      - url: "/path/a"
      - url: "/path/b/**"
      - url: "/path/c/**"
~~~~
