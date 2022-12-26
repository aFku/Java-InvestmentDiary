FROM amazoncorretto:11-alpine-jdk

ENV DB_DRIVER=""
ENV DB_PASSWORD=""
ENV DB_USER=""
ENV DB_URL=""
ENV DB_DIALECT=""
ENV DB_DDL=""
ENV API_VERSION=""

EXPOSE 8080

COPY target/InvestmentDiary-0.0.1-SNAPSHOT.jar /app/InvestmentDiary.jar
COPY integration_tests/app/application.properties /app/application.properties
RUN apk update \
    && apk add curl
ENTRYPOINT ["java", "-jar", "/app/InvestmentDiary.jar", "--spring.config.location=file:/app/application.properties"]
