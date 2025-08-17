## Contract Repayments Calculator

# Description

The system allows users to register/login into a system and allows the user to calculate
contract repayments for devices taken over the following periods 12, 24 and 36 months. The user
will be able to enter the device amount and be shown the different offerings based on the
amount over the 3 periods on offer with an interest rate of 6.5%.


# Project Spec

JAVA 21

Springboot 3

Spring Security

H2 Database

JUNIT 5

Thymeleaf

Maven 3

Project Lombok to remove boiler plate code

Mapstruct for mappings

For Usecase Design see [Diagram]

For sample contract please see [Contract]

Test Coverage Report [Test Coverage Report]


This project uses Project Lombok, please details below

# Project Lombok

Project Lombok is a java library that automatically plugs into your editor and build tools, spicing up your java.
Never write another getter or equals method again, with one annotation your class has a fully featured builder, automate
your logging variables, and much more.

For complete project information, see [projectlombok.org]


[projectlombok.org]: https://projectlombok.org/

[Diagram]: src/main/resources/contract-repayments-usecase.png
[Contract]: src/main/resources/api/api.yaml
[Test Coverage Report]: target/site/jacoco/index.html


## How to get started

### Local Development
1. Clone project and run mvn clean install, if you don't have maven installed in your local machine you can use the one bundled with the project.

2. Build the project: `mvn clean install`
3. Run the application: `mvn spring-boot:run`
4. Start project: It will use the default profile as well the default port 8080
5. Enter http://localhost:8080/index on your browser -> got to the registration page and create a user, after you have successfully created a user, go back to login screen to sign-in
6. After you have successfully signed-in then you should be redirected to the calculator page to be able to calculate possible monthly repayments

### Kubernetes Deployment

### Prerequisites

Prerequisites:
- Kubernetes cluster
- Helm 3.x installed
- kubectl configured with cluster access

Deployment steps:
1. Navigate to the helm-charts directory
2. Install the chart:
   ```bash
   helm install calculator .
   ```
3. To customize the installation, create a values.yaml file with your configurations and run:
   ```bash
   helm install calculator . -f your-values.yaml
   ```
Click here [Test Coverage Report] to see test coverage report

## Kubernetes Deployment

### Prerequisites
- Kubernetes cluster
- Helm 3.x installed
- kubectl configured with cluster access

### Deploy using Helm

1. Install the Helm chart:
```bash
helm install calculator ./helm-charts
```

2. Access the application:
```bash
kubectl port-forward svc/calculator 8080:8080
```

For custom configuration, create a values.yaml file and use: `helm install calculator ./helm-charts -f values.yaml`
