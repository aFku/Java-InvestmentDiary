# Java-InvestmentDiary

## Introduction
This is my first Spring Boot project and the first REST API application.  It is a simple CRUD app that allows users to store information about their stock market operations. It is divided into 3 business entities. Broker accounts store data about our accounts which we use to make stock market operations. Stock market subjects are companies that we invest in. We can also store websites and any other information about subjects that are very helpful while investing. Stock market operation stores information about our transactions and is related to the account via which we did this operation and to the market subject.

With these features, my app can also show a user their "wallets" based on a single broker account / all registered broker accounts. There is also the possibility to see your statistics based on a date range.

## Stack
- Java 11
- Spring Boot 2.7.2
- MySQL

## What I've learned
- Basics of Spring Boot
- Use of controller advice
- Basics of logging
- Basics of JPA and Hiberante
- Use of constraint validation
- Basics of pagination
- Basics of search by specification
- Basics of MapStruct
- Unittesting in Java
- Basics of designing REST API with Java and Spring Boot
- Automatic generation of OpenAPI docs with springdoc

## Deploy and testing

To deploy "production" version of this app you need to execute deploy script /deploy/deploy-prod.sh from /deploy directory:

<code>cd deploy 
./deploy-prod.sh</code>

Then application should be deployed and available on localhost:8080

Due to some problems with Java integration tests I decided to write them in python. To run these tests you need to go to /integration_tests and run deploy-and-test.sh

<code>cd integration_tests
./deploy-and-test.sh</code>

This will deploy all necessary containers (application + MySQL + tests) and show you logs from test container.

Both deploying scripts contain variables that can be changed for example to change MySQL password. Both of them also support argument "build" which triggers building process for all necessary images.

<code>./deploy-and-test.sh build</code>

This command will build application image and image with tests, when deploy-prod.sh will build only application image

## Endpoints

### /api-docs.html

These endpoint hold OpenAPI documentation displayed with swagger-ui.
Feel free to check requests bodies and responses there.

### [GET] /<api_version>/accounts

Show all registered broker accounts

### [POST] /<api_version>/accounts

Create new broker account

### [GET] /<api_version>/accounts/{id: int}

Show broker account with given ID

### [PUT] /<api_version>/accounts/{id: int}

Edit existing broker account

### [DELETE] /<api_version>/accounts/{id: int}

Delete existing broker account

### [GET] /<api_version>/accounts/wallet

Show assets from all accounts

### [GET] /<api_version>/accounts/{id: int}/wallet

Show assets from account with given ID

### [GET] /<api_version>/accounts/statistics

Show statistics based on all accounts

### [GET] /<api_version>/accounts/{id: int}/statistics

Show statistics based on account with given ID. This endpoint have additional parameters:

- startDate: YYYY-MM-DD - first day of aggregating stats
- stopDate: YYYY-MM-DD - last day of aggregating stats

### [GET] /<api_version>/accounts/{id: int}/statistics

Show statistics based on account with given ID. This endpoint have additional parameters:

- startDate: YYYY-MM-DD - first day of aggregating stats
- stopDate: YYYY-MM-DD - last day of aggregating stats

### [GET] /<api_version>/subjects

Show all market subjects

### [POST] /<api_version>/subjects

Create new market subject

### [GET] /<api_version>/subjects/{id: int}

Show market subject with given ID

### [PUT] /<api_version>/subjects/{id: int}

Edit existing market subject with given ID

### [DELETE] /<api_version>/subjects/{id: int}

Delete existing market subject with given ID

### [GET] /<api_version>/operations

Show all market operations

### [POST] /<api_version>/operations

Create new market operation

### [GET] /<api_version>/operations/{id: int}

Show market operation with given ID

### [DELETE] /<api_version>/operations/{id: int}

Delete existing market operation with given ID


## Schemas

### BrokerAccountDTO
<img src="https://github.com/aFku/Java-InvestmentDiary/blob/main/img/accounts.PNG" width="360" height="195">

### MarketSubjectDTO
<img src="https://github.com/aFku/Java-InvestmentDiary/blob/main/img/subjects.PNG" width="360" height="195">

### MarketOperationDTO
<img src="https://github.com/aFku/Java-InvestmentDiary/blob/main/img/operations.PNG" width="360" height="195">
