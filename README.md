To run application in Intelij IDEA lombok plugin should be installed.
https://projectlombok.org/setup/intellij

## What needs to be installed
1. Java 8 or higher.
2. Docker

## How to run application
1. Postgres 9.6 need to be run first. For this please install Postgres server and create new DB there products
Optionally you could run existing docker image via next command: docker-compose up (it will start Postgres 9.6 server in docker without volume)
**Note:** in task there was requirement to use DB without password but Postgres required password to be set
2. Run Spring application via Intelij or via any another IDE. 
Optionally it could be run via maven: _spring-boot:run_
Also we could run app from jar file. For that via command mvn clean install we could generate jar file.
After we could find jar file in /target folder and run app as: _java -jar -centric-sowtware-test-api-1.0.0.jar_

## General info
Centric-sowtware-test-api is test application. For building application maven is used.
After application has been started web server (JETTY) will listen HTTP request on port 8100
Spring Boot 2 is used as main framework for application

## How to run tests
To run tests please execute command mvn test (it will execute several integration test based on MOCKMVC and unit tests with Mockito)

## API docs
To view API docs please do next:
- run app
- acces via browser _http://localhost:8100/swagger-ui.html_

## Run in docker
To run application in docker next step should be done:
- run _mvn clean install_ to generate target jar for image
- run _docker-compose -f docker-compose-app.yml up_. It will download postgres image(database will use volume), will build application image from jar and will start it