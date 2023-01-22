#!/bin/bash

export DB_ROOT_PASSWORD=qwerty1234
export DB_PASSWORD=billionaire
export DB_USER=investor
export DB_NAME=investment
export DB_DRIVER=com.mysql.jdbc.Driver
export DB_DIALECT=org.hibernate.dialect.MySQL5Dialect
export DB_DDL=create
export DB_PORT=3306
export DB_VENDOR=mysql
export DB_HOST=db_prod

export APP_VERSION=latest
export APP_LOCATION=investment-app
export API_VERSION=v1

location=$(pwd)
if [ "$1" = "build" ]; then
  cd ../
  mvn clean install
  docker build -t "investment-app:$APP_VERSION" -f deploy/Dockerfile .
  cd $location
fi

docker-compose -f docker-compose.yaml up