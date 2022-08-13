# Pancakes-Application

Pancakes-Application is Java Spring boot application which is designed as a service for creating pancakes and ordering
them. There are implemented features and functionality which are connected to ingredients, pancakes, orders and reports.
There is only REST server without the development of frontend. Each service are tested using JUnit 5 and Mockito besides
that methods of ingredient repository are tested using TestContainers.
The data of this service is stored in the Postgres database in Docker container with the following properties and
credentials:

### Database properties

```
spring.datasource.url=jdbc:postgresql://localhost:5432/postgres?ssl=false&serverTimezone=UTC
spring.datasource.username=postgres
spring.datasource.password=mysecretpassword
spring.datasource.driver-class-name=org.postgresql.Driver
```

The database schema is defined using database-migration tool Flyway.

Authentication and authorization are set up using Basic Authentication with Spring, the users credentials are
stored in database, the password is encrypted with BCrypt encryption.

In order to access the data from database Spring Data is used, in the ingredient repository there are also two methods
with queries in native PostgreSQL.

Also, docker-compose is implemented in order to have opportunity to run the very application and database in different
docker containers. In order to use docker-compose you need to run two Maven commands:

```
mvn clean
```

and

```
mvn package
```

then you need to copy the file *.jar from the target package to docker package in the pancakes/src/docker, and then it
becomes possible to run the following command

```
docker-compose up
```
