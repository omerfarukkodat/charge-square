
# ChargeSquare - Charging Station Microservice


## Project Overview
This project is a Spring Boot-based microservice that manages charging stations.The microservice provides RESTful APIs and integrates a GraphQL API to support advanced querying and interactions with charging stations. The service includes features like querying by location, station details, and adding new stations, among other functionalities.

## Features

**Basic CRUD Operations:**

 - Add, retrieve, update, and delete charging stations.
 - Fetch a list of all stations or a specific station by its ID.

**GraphQL API:**

 - Query and mutate charging stations with flexible and advanced filtering.
 - Fetch stations by radius, search by title or usage cost, and find fast charging stations.

**Caching & API Gateway:**

 - Redis caching for frequently accessed data (stations and specific station details).
 - Spring Cloud Gateway for API routing and integration.

**Containerized Microservice:**

 - Dockerized application for easy deployment.
 - Kubernetes manifests and Helm for managing deployment.

## Requirements
- Java 21 or later
- Spring Boot 3.x
- PostgreSQL (for database)
- Docker (for containerization)
- Kubernetes (for deployment)
- Redis (for caching)
- Helm (for packaging deployments)
- Kafka (for messaging to each other microservices)



## API Endpoints



### Charge Station Service Restful Api http://localhost:8081

| HTTP Method | Endpoint      | Description                                     |
|-------------|---------------|-------------------------------------------------|
| GET         | /             | Fetch all charging stations                     |
| GET         | /{id}         | Fetch details of a specific station  by its ID. |
| POST        | /             | Add a new charging station.                     |


### Charge Station Service GraphQL Api

**Queries:**

 - allStations: Retrieve a list of all stations.
 - stationById: Fetch station details by station ID.
 - stationByRadius: Fetch stations within a specific radius of a given location.
 - searchStation: Search stations by title and usage cost.
 - fastChargingStations: Fetch stations that support fast charging.

**Mutations:**

 - addStation: Add a new station.


### Query to fetch all stations
```graphql
query {
  allStations {
    id
    uuid
    usageCost
    addressInfoDto {
      addressId
      title
      addressLine1
      addressLine2
      town
      stateOrProvince
      postcode
      countryId
      distanceUnit
      latitude
      longitude
    }
    connectionsDtos {
      id
      connectionTypeId
      statusTypeId
      levelId
      powerKW
      currentTypeId
      quantity
      isFast
    }
    numberOfPoints
  }
}
```

```graphql
# Query to fetch a station by ID
query {
  stationById(id: "1234") {
    id
    uuid
    usageCost
    addressInfoDto {
      addressId
      title
      addressLine1
      addressLine2
      town
      stateOrProvince
      postcode
      countryId
      distanceUnit
      latitude
      longitude
    }
    connectionsDtos {
      id
      connectionTypeId
      statusTypeId
      levelId
      powerKW
      currentTypeId
      quantity
      isFast
    }
    numberOfPoints
  }
}
```

```graphql
# Query to fetch stations by radius
  query {
    stationByRadius(latitude: 40.7128, longitude: -74.0060, radius: 5) {
      id
      uuid
      usageCost
      addressInfoDto {
        addressId
        title
        addressLine1
        addressLine2
        town
        stateOrProvince
        postcode
        countryId
        distanceUnit
        latitude
        longitude
      }
      connectionsDtos {
        id
        connectionTypeId
        statusTypeId
        levelId
        powerKW
        currentTypeId
        quantity
        isFast
      }
      numberOfPoints
    }
  }
```


```graphql
# Query to search stations by title and usage cost

query {
  searchStation(title: "Station 1", usageCost: "5.00") {
      id
      uuid
      usageCost
      addressInfoDto {
        addressId
        title
        addressLine1
        addressLine2
        town
        stateOrProvince
        postcode
        countryId
        distanceUnit
        latitude
        longitude
      }
      connectionsDtos {
        id
        connectionTypeId
        statusTypeId
        levelId
        powerKW
        currentTypeId
        quantity
        isFast
      }
      numberOfPoints
    }
  }
```


```graphql
# Query to fetch fast charging stations
query {
fastChargingStations {
      id
      uuid
      usageCost
      addressInfoDto {
        addressId
        title
        addressLine1
        addressLine2
        town
        stateOrProvince
        postcode
        countryId
        distanceUnit
        latitude
        longitude
      }
      connectionsDtos {
        id
        connectionTypeId
        statusTypeId
        levelId
        powerKW
        currentTypeId
        quantity
        isFast
      }
      numberOfPoints
    }
  }
```


## Setup Instructions

**1.Clone the Repository**

```bash
git clone https://github.com/omerfarukkodat/charge-square.git
cd charge-square
```
**2. Configure PostgreSQL Database**

- Set up a PostgreSQL database and update the application.properties or application.yml with the correct database credentials.
```bash
spring.datasource.url=jdbc:postgresql://localhost:5432/station 
spring.datasource.username=your_db_user
spring.datasource.password=your_db_password
```
**3. Build the Application**

- Build the project using Maven:
### You have to do it for each microservices. ###

```bash
mvn clean install
```

**4.Run the Application Locally**
- To run the Spring Boot application locally:

```bash
mvn spring-boot:run
```
By default, the application will run on http://localhost:8081 for **the REST API** and http://localhost:8080 for the **API Gateway.**


**5. Dockerize the Application**

**A Dockerfile** contains instructions on how to build and run an application within a Docker container. It defines the base image, dependencies, build steps, and the entry point to execute the application.

```bash
# Use OpenJDK 21 as the base image
FROM openjdk:21

# Set the working directory
WORKDIR /app

# Copy the Maven Wrapper files and pom.xml
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml /app/

# Give executable permissions to the Maven Wrapper
RUN chmod +x mvnw

# Copy the local dependency to the Maven repository
COPY ./common-dto-module-0.0.1-SNAPSHOT.jar /root/.m2/repository/com/kodat/of/common-dto-module/0.0.1-SNAPSHOT/common-dto-module-0.0.1-SNAPSHOT.jar

# Download dependencies
RUN ./mvnw dependency:go-offline

# Copy the source code
COPY src /app/src

# Build the application with Maven
RUN ./mvnw clean package -DskipTests

# Copy the JAR file to the container
COPY ./target/chargeStationService-0.0.1-SNAPSHOT.jar /app/chargeStationService.jar

# Expose the port the container will listen on
EXPOSE 8081

# Set the entrypoint to run the application
ENTRYPOINT ["java", "-jar", "/app/chargeStationService.jar"]
```

-Build the Docker image for the microservices:

```bash
docker build -t charging-station-service:latest .
docker build -t gateway:latest .
docker build -t station-data-relay-service:latest .
```
- Run the Docker container:

```bash
docker-compose up -d    

or

docker-compose up --build
```

**6. Kubernetes Deployment**

**1.Helm Charts:** The deployment of the microservice is managed using Helm for Kubernetes. You can package the microservice and deploy it with the following Helm command:

```bash
helm create microservices-name
helm package microservices-name
helm install microservices-name ./charts
```
**Example :**

```bash
helm create charge-station-service
helm package charge-station-service
helm install charge-station-service ./charts
```

**2.Kubernetes Manifests:** Ensure your Kubernetes cluster is set up and the necessary manifests (including ConfigMaps, resource limits, etc.) are correctly configured.

```bash
kubectl apply -f k8s/
```

**7. Redis Caching**
-Redis is used for caching frequently accessed station data to improve performance. Ensure Redis is installed and running. You can use Docker to run Redis locally:
```bash
docker run -p 6379:6379 redis
```

**8. API Gateway**

- The API Gateway is used to route requests to the correct microservices. It listens on port 8080 by default.

- Example Gateway Configuration:
  - /stations/* - Routes requests to the charging station service.
  - /fetch - Routes to the data-relay-station-service for fetching data from external APIs.

### For Backend Services:
- You should go to **charge-square/** to run docker compose file.

```bash
  docker-compose up -d
```


**9. CI/CD Pipeline**
The CI/CD pipeline is implemented using GitHub Actions for automated builds and deployments.

- **Stages**
    - **Linting & Static Analysis:** To ensure code quality.
    - **Unit Tests:** Run unit tests to validate application functionality.
    - **Docker Build:** Package the application into a Docker image.
    - **Deploy:** Deploy the Dockerized application to Kubernetes.

**1. Example GitHub Actions CI/CD Workflow File **
   Create a .github/workflows/ci-cd.yml file in the root directory of each service. This example is prepared for the charging-station-service microservice.

```bash
name: Charge Station Service CI/CD

on:
 push:
  branches:
    - main
  pull_request:
    branches:
    - main

jobs:
  lint:
    runs-on: ubuntu-latest
    steps:
    - name: Checkout repository
      uses: actions/checkout@v2
      
    - name: Set up JDK 21
      uses: actions/setup-java@v2
      with:
          java-version: '21'

    - name: Install dependencies
      run: mvn clean install -DskipTests

    - name: Lint with Maven Checkstyle
      run: mvn checkstyle:check

    test:
     runs-on: ubuntu-latest
     needs: lint
    steps:
      - name: Checkout repository
        uses: actions/checkout@v2

      - name: Set up JDK 21
        uses: actions/setup-java@v2
        with:
          java-version: '21'

      - name: Install dependencies
        run: mvn clean install

      - name: Run unit tests
        run: mvn test

    docker-build:
      runs-on: ubuntu-latest
      needs: test
      steps:
      - name: Checkout repository
        uses: actions/checkout@v2

      - name: Set up Docker Buildx
        uses: docker/setup-buildx-action@v2

      - name: Login to DockerHub
        uses: docker/login-action@v2
        with:
          username: ${{ secrets.DOCKER_USERNAME }}
          password: ${{ secrets.DOCKER_PASSWORD }}

      - name: Build and Push Docker image
        run: |
          docker build -t ${{ secrets.DOCKER_USERNAME }}/charging-station-service:${{ github.sha }} .
          docker push ${{ secrets.DOCKER_USERNAME }}/charging-station-service:${{ github.sha }}

    deploy:
      runs-on: ubuntu-latest
      needs: docker-build
      steps:
      - name: Checkout repository
        uses: actions/checkout@v2

      - name: Set up kubectl
        uses: azure/setup-kubectl@v1
        with:
          kubectl-version: 'v1.23.0'

      - name: Set up Kubeconfig
        uses: azure/k8s-set-context-action@v1
        with:
          kubeconfig: ${{ secrets.KUBECONFIG }}

      - name: Deploy to Kubernetes
        run: |
          kubectl apply -f k8s/charge-station-service/deployment.yml
          kubectl rollout status deployment/charge-station-service

```


**3. Step-by-Step Explanation**
 **1. Triggering the Workflow (on Push and PR)** The on: section determines when the workflow will be triggered. In this example, it is triggered when there is a push to the main branch or a pull request targeting the main branch.

 **2. Linting & Static Analysis (Lint Job)** In the lint: job, we use checkstyle:check to verify that the code follows the correct style and format.

 **3. Test Job** In the test: job, we run unit tests for the project. We use mvn test to check if all tests pass.

 **4. Docker Build and Push** In the docker-build: job, we build the Docker image and push it to Docker Hub. We use GitHub Secrets (DOCKER_USERNAME and DOCKER_PASSWORD) to authenticate with Docker Hub.

 **5. Deployment to Kubernetes** In the deploy: job, we deploy the Docker image to a Kubernetes environment. We connect to the Kubernetes cluster using the Kubeconfig file and apply the Kubernetes manifest file using kubectl apply -f.

**4. GitHub Secrets**
   GitHub Actions pipeline uses GitHub Secrets to access Docker Hub and Kubernetes. You can define these secrets in the **Settings > Secrets** section of your GitHub repository.

**Required Secrets:**

- DOCKER_USERNAME: Docker Hub username
- DOCKER_PASSWORD: Docker Hub password
- KUBECONFIG: The content of the Kubernetes config file

## Conclusion
This microservice provides robust functionality for managing and querying charging stations through REST and GraphQL APIs. It supports containerization and Kubernetes deployment, with caching and API Gateway routing for enhanced performance.

This README.md now includes a section on **GraphQL API**, detailing the query and mutation operations available, including sample queries and mutations. It also integrates with the rest of your project setup, covering Docker, Kubernetes, and CI/CD pipelines.



## Contact

### Omer Faruk Kodat

<a href="https://github.com/omerfarukkodat" target="_blank">
<img  src=https://img.shields.io/badge/github-%2324292e.svg?&style=for-the-badge&logo=github&logoColor=white alt=github style="margin-bottom: 20px;" />
</a>
<a href = "mailto:farukkodat@gmail.com?subject = Feedback&body = Message">
<img src=https://img.shields.io/badge/send-email-email?&style=for-the-badge&logo=microsoftoutlook&color=CD5C5C alt=microsoftoutlook style="margin-bottom: 20px; margin-left:20px" />
</a>
<a href="https://linkedin.com/in/omerfarukkodat" target="_blank">
<img src=https://img.shields.io/badge/linkedin-%231E77B5.svg?&style=for-the-badge&logo=linkedin&logoColor=white alt=linkedin style="margin-bottom: 20px; margin-left:20px" />
</a>  


<br />

## ChargeSquare - Charge Station Service

  


