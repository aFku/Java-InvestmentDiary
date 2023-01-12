FROM python:3.11-alpine

ENV DB_PASSWORD=""
ENV DB_USER=""
ENV DB_HOST=""
ENV DB_PORT=""
ENV DB_NAME=""
ENV DB_VENDOR=""

ENV APP_LOCATION=""
ENV APP_PORT=""
ENV API_VERSION=""

COPY integration_tests/tests /app/
WORKDIR /app
RUN apk update \
    && apk add --virtual build-deps gcc python3-dev musl-dev \
    && apk add --no-cache mariadb-connector-c-dev \
    && apk add curl
RUN rm Dockerfile
RUN pip3 install -r requirements.txt

ENTRYPOINT ["python3", "-m", "unittest", "discover", "-v"]