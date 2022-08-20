# Task Manager

## What is this project trying to achieve? 

This project is being developed to explore the possibilities of spring, rest architecture, jpa, postqre

## Can I use it? 

you need to create 2 files with properties in /resources : application.properties , application-test.properties 

the application uses 2 databases: app and test

IDE - Intellij IDEA

JDK - AZUL 13.0.9

DataBase - Postgre

## License

MIT License
### application.properties
```
spring.datasource.url=jdbc:postgresql://localhost:5432/{nameDataBase}
spring.datasource.jdbc-url=${spring.datasource.url}
spring.datasource.driver-class-name=org.postgresql.Driver
spring.datasource.username={username}
spring.datasource.password={password}

spring.jpa.hibernate.show-sql=true
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.properties.hibernate.jdbc.lob.non_contextual_creation= true

app.jwtSecret= 
app.jwtExpirationMs= 
app.mail= 
server.port=8082

spring.mail.host=
spring.mail.port=
spring.mail.username=
spring.mail.password=
spring.mail.protocol=

spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.connectiontimeout=5000
spring.mail.properties.mail.smtp.timeout=5000
spring.mail.properties.mail.smtp.writetimeout=5000
spring.mail.properties.mail.smtp.starttls.enable=true
```
## REST API

The REST API to the example app is described below.

