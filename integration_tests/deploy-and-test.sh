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
export DB_HOST=db_integration

export APP_VERSION=latest
export APP_LOCATION=investment-app
export API_VERSION=v1

export TESTS_VERSION=latest

location=$(pwd)
if [ "$1" = "build" ]; then
  cd ../
  docker build -t "investment-app:$APP_VERSION" -f integration_tests/app/Dockerfile .
  docker build -t "integration-tests:$TESTS_VERSION" -f integration_tests/tests/Dockerfile .
  cd $location
fi

docker-compose -f integration-compose.yaml up --wait
exit_code=$(docker wait integration-tests)
docker logs integration-tests
if [ $exit_code = 0 ]; then
  echo "TESTS: PASSED"
else
  echo "TESTS: FAILED"
fi
docker-compose -f integration-compose.yaml down
exit $exit_code