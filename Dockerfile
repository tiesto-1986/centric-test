FROM java:8-jdk-alpine

COPY ./target/centric-sowtware-test-api-1.0.0.jar /usr/app/

WORKDIR /usr/app

RUN sh -c 'touch centric-sowtware-test-api-1.0.0.jar'

ENTRYPOINT ["java","-jar","centric-sowtware-test-api-1.0.0.jar"]