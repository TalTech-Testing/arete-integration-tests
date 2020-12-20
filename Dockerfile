FROM openjdk:11

MAINTAINER enrico.vompa@gmail.com

ADD . /integration-tests
WORKDIR /integration-tests

RUN apt-get update && apt-get install dos2unix
RUN dos2unix mvnw
RUN chmod +x mvnw

RUN ./mvnw install -DskipTests

ENTRYPOINT [ "sh", "-c", "./mvnw test" ]
