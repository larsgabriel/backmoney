FROM openjdk:11
RUN mkdir app
ADD ./target/backmoney-api-1.0.0.jar backmoney-api-1.0.0.jar
COPY ./target/backmoney-api-1.0.0.jar /app/backmoney-api-1.0.0.jar
WORKDIR /app
ENTRYPOINT ["java", "-jar", "/backmoney-api-1.0.0.jar"]

