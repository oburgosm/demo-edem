# Application for testing purpose

This application is an example of topics explained in the course **&broker.dataDirectory=./target/vm**

## Prerequisites

- Java 11/17
- Maven 3.6.3 or Higher
- Docker and docker compose

## How to build application

```
mvn clean install
```

## How to run application

- Start containers with docker compose:

```
docker compose up -d
```

- Start application

```
mvn clean spring-boot:run
```

or execute from your IDE

# Using application

- GraphiQL Endpoint: http://localhost:8080/graphiql
- Zipkin endpoint: http://localhost:8411
