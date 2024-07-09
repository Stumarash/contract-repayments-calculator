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

Clone project and run mvn clean install, if you don't have maven installed in your local machine you can use the one bundled with the project.

Build the project: mvn clean install

Run the application: mvn spring-boot:run

Start project : It will use the default profile as well the default port 8080

Enter http://localhost:8080/index on your browser -> got to the registration page and create a user, after you have successfully created a user, go back to login screen to sign-in

After you have successfully signed-in then you should be redirected to the calculator page to be able to calculate possible monthly repayments

Click here [Test Coverage Report] to see test coverage report