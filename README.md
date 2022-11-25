# Application for testing purpose

This application is an example of topics explained in the course **High Potencial Technical Booster**

## Prerequisites

- Java 11/17
- Maven 3.6.3 or Higher
- Docker and docker compose

## How to build application

```bash
mvn clean install
```

## How to run application

- Start containers (mongo, grafana and zipkin) with docker compose:

```bash
docker compose up -d
```

- Start Prometheus with docker:

```bash
docker run -d -p 9090:9090 -v path_to_prometheus.yml prom/prometheus
```

- Start application

```bash
mvn clean spring-boot:run
```

or execute application from your IDE

## Using application

- GraphiQL Endpoint: http://localhost:8080/graphiql
- Zipkin endpoint: http://localhost:8411
- Grafana endpoint: http://localhost:3000
   (user: admin  | password: admin)
- Prometheus endpoint: http://localhost:9090

## Query examples

- Insert a product:

```graphql
mutation CreateProduct($productName:String!, $productDescription:String) {
  createProduct(name: $productName, description: $productDescription) {
    id, name, description
  }
}
```

Query Variables example
```json
{
  "productName": "Pantalón",
  "productDescription": "Pantalón Slim Fit"
}
```