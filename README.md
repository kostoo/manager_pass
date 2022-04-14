#Task Manager

##What is this project trying to achieve? 

This project is being developed to explore the possibilities of spring, rest architecture, jpa, postqre

##Can I use it? 

you need to create 2 files with properties in /resources : application.properties , application-test.properties 

the application uses 2 databases: app and test

IDE - Intellij IDEA

JDK - AZUL 13.0.9

DataBase - Postgre

##License

MIT License
###application.properties
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
##REST API

The REST API to the example app is described below.

###Authorization

```
POST api/auth
```
Request
```
 POST http://localhost:8082/api/auth
```
```
{
    "username":"user",
    "password":"password"
}
```
Response

ok
```
Status: 200 OK
Content-Type: application/json
```
```
{
"token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJrb3N0byIsImlhdCI6MTY0OTg1NDgzNiwiZXhwIjoxNjQ5OTQxMjM2fQ.1paAd9GSefGln5FGgKZ6xm6t9A_4OL0YKnpSyLgq_w4W6yNKKJMUHc3Hlk46ddNQmUfCA9imFqjYpM60BwNBzA"
}
```
fail
```
Status: 401 Unauthorized
```
###Registration
Request
```
POST api/registration
```
```
POST http://localhost:8082/api/registration
```
```
{
"username":"mail",
"email":"mail@mail.ru",
"password": "password",
"role":["admin"]
}
```
Response

Ok
```
Status: 200 OK
Content-Type: application/json
```
```
{
"message": "User registered successfully!"
}
```
fail
```
Status: 400 Bad Request
Content-Type: application/json
```
```
{
"message": "Error: Username is already taken!"
}
```
###Activate user
Request
```
POST /api/register/activate/{username}
```

```
POST http://localhost:8082/api/register/activate/user
```
Response

ok
```
Status: 200 OK
Content-Type: application/json
```
```
{
"message": "user activated"
}
```
fail
```
{
"timestamp": "2022-04-13T14:11:00.826+00:00",
"status": 404,
"error": "Not Found",
"message": "username not found",
"path": "/api/register/activate/us"
}
```
###Tasks
ONLY Authorized
####Get all tasks

```
GET /api/tasks ADMIN
```

Request
```
GET http://localhost:8082/api/tasks
```
Response
```
Status: 200 OK
Content-Type: application/json
```
```
[
    {
        "idTask": 119,
        "name": "SrMVGfsDSN",
        "message": "sSTuGrKRpD",
        "userEntity": {
                          "idUser": 1,
                          "name": "nesterov",
                          "lastName": "nikita",
                          "username": "kosto",
                          "email": "mail@yandex.ru",
                          "password": "$2a$12$9p4/D5TzCWoNeuROhS8XJ.Q35P0lh4PB5Dd7FDK3HM6pxkVjoficW",
                          "roles": [
                                     {   
                                           "id": 2,
                                           "name": "ROLE_ADMIN"
                                     }
                                   ],
        "isAccountActive": true,
        "isAccountNonBlock": true
    },
        "priority": {
                     "id": 2,
                     "name": "MEDIUM"
    },
        "dateTimeStart": "2022-04-06T23:35:42.166482",
        "dateTimeFinish": "2022-04-07T23:35:42.166482"
},
{
        "idTask": 76,
        "name": "RLPYuErgTO",
        "message": "WyBVeebWvl",
        "userEntity": {
                       "idUser": 1,
                       "name": "nesterov",
                       "lastName": "nikita",
                       "username": "kosto",
                       "email": "example@yandex.ru",
                       "password": "$2a$12$9p4/D5TzCWoNeuROhS8XJ.Q35P0lh4PB5Dd7FDK3HM6pxkVjoficW",
        "roles": [
             {
                  "id": 2,
                  "name": "ROLE_ADMIN"
        ],
        }
        "isAccountActive": true,
        "isAccountNonBlock": true
        },
        "priority": {
                    "id": 2,
                    "name": "MEDIUM"
        },
        dateTimeStart": "2022-04-06T11:45:02.454316",
        dateTimeFinish": "2022-04-07T11:45:02.454316"
}
```
#### Get one task

```
GET /api/tasks/{idTask} ADMIN
```
Request
```
http://localhost:8082/api/tasks/119
```
Response
```
Status: 200 OK
Content-Type: application/json
```
```
    {
        "idTask": 119,
        "name": "SrMVGfsDSN",
        "message": "sSTuGrKRpD",
        "userEntity": {
                          "idUser": 1,
                          "name": "nesterov",
                          "lastName": "nikita",
                          "username": "kosto",
                          "email": "mail@yandex.ru",
                          "password": "$2a$12$9p4/D5TzCWoNeuROhS8XJ.Q35P0lh4PB5Dd7FDK3HM6pxkVjoficW",
                          "roles": [
                                     {   
                                           "id": 2,
                                           "name": "ROLE_ADMIN"
                                     }
                                   ],
        "isAccountActive": true,
        "isAccountNonBlock": true
    },
        "priority": {
                     "id": 2,
                     "name": "MEDIUM"
    },
        "dateTimeStart": "2022-04-06T23:35:42.166482",
        "dateTimeFinish": "2022-04-07T23:35:42.166482"
},
```
#### Delete Task
```
DELETE /api/tasks/{idTask}  USER ADMIN
```
Request
```
http://localhost:8082/api/tasks/{idTask}
```
Response
```
Status: 200 OK
Content-Type: application/json
```
####Add task
```
POST /api/tasks USER ADMIN
```
Request
```
http://localhost:8082/api/tasks
```
Response
```
Status: 200 OK
Content-Type: application/json
```
```
{
    "idTask": 274,
    "name": "SrMVGfsDSN",
    "message": "sSTuGrKRpD",
    "userEntity": {
        "idUser": 1,
        "name": "nesterov",
        "lastName": "nikita",
        "username": "kosto",
        "email": "mail@yandex.ru",
        "password": "$2a$12$9p4/D5TzCWoNeuROhS8XJ.Q35P0lh4PB5Dd7FDK3HM6pxkVjoficW",
        "roles": [
            {
                "id": 2,
                "name": "ROLE_ADMIN"
            }
        ],
        "isAccountActive": true,
        "isAccountNonBlock": true
    },
    "priority": {
        "id": 2,
        "name": "MEDIUM"
    },
    "dateTimeStart": "2022-04-06T23:35:42.166482",
    "dateTimeFinish": "2022-04-07T23:35:42.166482"
}
```

####Update task
```
PUT /api/tasks USER ADMIN
```
Response
```
http://localhost:8082/api/tasks
```
```
{
    "idTask": 274,
    "name": "SrMVGfs",
    "message": "sSTuGrKRpD",
    "userEntity": {
        "idUser": 1,
        "name": "nesterov",
        "lastName": "nikita",
        "username": "kosto",
        "email": "mail@yandex.ru",
        "password": "$2a$12$9p4/D5TzCWoNeuROhS8XJ.Q35P0lh4PB5Dd7FDK3HM6pxkVjoficW",
        "roles": [
            {
                "id": 2,
                "name": "ROLE_ADMIN"
            }
        ],
        "isAccountActive": true,
        "isAccountNonBlock": true
    },
    "priority": {
        "id": 2,
        "name": "MEDIUM"
    },
    "dateTimeStart": "2022-04-06T23:35:42.166482",
    "dateTimeFinish": "2022-04-07T23:35:42.166482"
}
```
Request
```
Status: 200 OK
Content-Type: application/json
```
```
{
    "idTask": 274,
    "name": "SrMVGfs",
    "message": "sSTuGrKRpD",
    "userEntity": {
        "idUser": 1,
        "name": "nesterov",
        "lastName": "nikita",
        "username": "kosto",
        "email": "mail@yandex.ru",
        "password": "$2a$12$9p4/D5TzCWoNeuROhS8XJ.Q35P0lh4PB5Dd7FDK3HM6pxkVjoficW",
        "roles": [
            {
                "id": 2,
                "name": "ROLE_ADMIN"
            }
        ],
        "isAccountActive": true,
        "isAccountNonBlock": true
    },
    "priority": {
        "id": 2,
        "name": "MEDIUM"
    },
    "dateTimeStart": "2022-04-06T23:35:42.166482",
    "dateTimeFinish": "2022-04-07T23:35:42.166482"
}
```
####get tasks from user
```
GET /api/tasks/user USER ADMIN
```
Request
```
http://localhost:8082/api/tasks/user
```
Response
```
Status: 200 OK
Content-Type: application/json
```
```
[
    {
        "idTask": 119,
        "name": "SrMVGfsDSN",
        "message": "sSTuGrKRpD",
        "userEntity": {
            "idUser": 1,
            "name": "nesterov",
            "lastName": "nikita",
            "username": "kosto",
            "email": "mail@yandex.ru",
            "password": "$2a$12$9p4/D5TzCWoNeuROhS8XJ.Q35P0lh4PB5Dd7FDK3HM6pxkVjoficW",
            "roles": [
                {
                    "id": 2,
                    "name": "ROLE_ADMIN"
                }
            ],
            "isAccountActive": true,
            "isAccountNonBlock": true
        },
        "priority": {
            "id": 2,
            "name": "MEDIUM"
        },
        "dateTimeStart": "2022-04-06T23:35:42.166482",
        "dateTimeFinish": "2022-04-07T23:35:42.166482"
    },
    {
        "idTask": 76,
        "name": "RLPYuErgTO",
        "message": "WyBVeebWvl",
        "userEntity": {
            "idUser": 1,
            "name": "nesterov",
            "lastName": "nikita",
            "username": "kosto",
            "email": "mail@yandex.ru",
            "password": "$2a$12$9p4/D5TzCWoNeuROhS8XJ.Q35P0lh4PB5Dd7FDK3HM6pxkVjoficW",
            "roles": [
                {
                    "id": 2,
                    "name": "ROLE_ADMIN"
                }
            ],
            "isAccountActive": true,
            "isAccountNonBlock": true
        },
        "priority": {
            "id": 2,
            "name": "MEDIUM"
        },
        "dateTimeStart": "2022-04-06T11:45:02.454316",
        "dateTimeFinish": "2022-04-07T11:45:02.454316"
    },
    {
        "idTask": 77,
        "name": "PEPZodKbmW",
        "message": "JhNtdKNGyU",
        "userEntity": {
            "idUser": 1,
            "name": "nesterov",
            "lastName": "nikita",
            "username": "kosto",
            "email": "mail@yandex.ru",
            "password": "$2a$12$9p4/D5TzCWoNeuROhS8XJ.Q35P0lh4PB5Dd7FDK3HM6pxkVjoficW",
            "roles": [
                {
                    "id": 2,
                    "name": "ROLE_ADMIN"
                }
            ],
            "isAccountActive": true,
            "isAccountNonBlock": true
        },
        "priority": {
            "id": 2,
            "name": "MEDIUM"
        },
        "dateTimeStart": "2022-04-06T11:45:03.710169",
        "dateTimeFinish": "2022-04-07T11:45:03.710169"
    }
]
```
####get tasks from user and priority
```
GET /api/tasks/user/priority/{idPriority} USER ADMIN
```
Request
```
http://localhost:8082/api/tasks/user/priority/2
```
Response
```
Status: 200 OK
Content-Type: application/json
```
```
[
    {
        "idTask": 119,
        "name": "SrMVGfsDSN",
        "message": "sSTuGrKRpD",
        "userEntity": {
            "idUser": 1,
            "name": "nesterov",
            "lastName": "nikita",
            "username": "kosto",
            "email": "mail@yandex.ru",
            "password": "$2a$12$9p4/D5TzCWoNeuROhS8XJ.Q35P0lh4PB5Dd7FDK3HM6pxkVjoficW",
            "roles": [
                {
                    "id": 2,
                    "name": "ROLE_ADMIN"
                }
            ],
            "isAccountActive": true,
            "isAccountNonBlock": true
        },
        "priority": {
            "id": 2,
            "name": "MEDIUM"
        },
        "dateTimeStart": "2022-04-06T23:35:42.166482",
        "dateTimeFinish": "2022-04-07T23:35:42.166482"
    },
    {
        "idTask": 76,
        "name": "RLPYuErgTO",
        "message": "WyBVeebWvl",
        "userEntity": {
            "idUser": 1,
            "name": "nesterov",
            "lastName": "nikita",
            "username": "kosto",
            "email": "mail@yandex.ru",
            "password": "$2a$12$9p4/D5TzCWoNeuROhS8XJ.Q35P0lh4PB5Dd7FDK3HM6pxkVjoficW",
            "roles": [
                {
                    "id": 2,
                    "name": "ROLE_ADMIN"
                }
            ],
            "isAccountActive": true,
            "isAccountNonBlock": true
        },
        "priority": {
            "id": 2,
            "name": "MEDIUM"
        },
        "dateTimeStart": "2022-04-06T11:45:02.454316",
        "dateTimeFinish": "2022-04-07T11:45:02.454316"
    },
    {
        "idTask": 77,
        "name": "PEPZodKbmW",
        "message": "JhNtdKNGyU",
        "userEntity": {
            "idUser": 1,
            "name": "nesterov",
            "lastName": "nikita",
            "username": "kosto",
            "email": "mail@yandex.ru",
            "password": "$2a$12$9p4/D5TzCWoNeuROhS8XJ.Q35P0lh4PB5Dd7FDK3HM6pxkVjoficW",
            "roles": [
                {
                    "id": 2,
                    "name": "ROLE_ADMIN"
                }
            ],
            "isAccountActive": true,
            "isAccountNonBlock": true
        },
        "priority": {
            "id": 2,
            "name": "MEDIUM"
        },
        "dateTimeStart": "2022-04-06T11:45:03.710169",
        "dateTimeFinish": "2022-04-07T11:45:03.710169"
    },
    {
        "idTask": 78,
        "name": "OvYJjvKnGD",
        "message": "pVjbCLBZRD",
        "userEntity": {
            "idUser": 1,
            "name": "nesterov",
            "lastName": "nikita",
            "username": "kosto",
            "email": "mail@yandex.ru",
            "password": "$2a$12$9p4/D5TzCWoNeuROhS8XJ.Q35P0lh4PB5Dd7FDK3HM6pxkVjoficW",
            "roles": [
                {
                    "id": 2,
                    "name": "ROLE_ADMIN"
                }
            ],
            "isAccountActive": true,
            "isAccountNonBlock": true
        },
        "priority": {
            "id": 2,
            "name": "MEDIUM"
        },
        "dateTimeStart": "2022-04-06T11:45:04.024275",
        "dateTimeFinish": "2022-04-07T11:45:04.024275"
    }
]
```

####get tasks from user on page
```
GET /api/tasks/user/{page} USER ADMIN
```
Request

```
http://localhost:8082/api/tasks/user/0
```
Response
```
Status: 200 OK
Content-Type: application/json
```
```
[
    {
        "idTask": 119,
        "name": "SrMVGfsDSN",
        "message": "sSTuGrKRpD",
        "userEntity": {
            "idUser": 1,
            "name": "nesterov",
            "lastName": "nikita",
            "username": "kosto",
            "email": "mail@yandex.ru",
            "password": "$2a$12$9p4/D5TzCWoNeuROhS8XJ.Q35P0lh4PB5Dd7FDK3HM6pxkVjoficW",
            "roles": [
                {
                    "id": 2,
                    "name": "ROLE_ADMIN"
                }
            ],
            "isAccountActive": true,
            "isAccountNonBlock": true
        },
        "priority": {
            "id": 2,
            "name": "MEDIUM"
        },
        "dateTimeStart": "2022-04-06T23:35:42.166482",
        "dateTimeFinish": "2022-04-07T23:35:42.166482"
    },
    {
        "idTask": 76,
        "name": "RLPYuErgTO",
        "message": "WyBVeebWvl",
        "userEntity": {
            "idUser": 1,
            "name": "nesterov",
            "lastName": "nikita",
            "username": "kosto",
            "email": "mail@yandex.ru",
            "password": "$2a$12$9p4/D5TzCWoNeuROhS8XJ.Q35P0lh4PB5Dd7FDK3HM6pxkVjoficW",
            "roles": [
                {
                    "id": 2,
                    "name": "ROLE_ADMIN"
                }
            ],
            "isAccountActive": true,
            "isAccountNonBlock": true
        },
        "priority": {
            "id": 2,
            "name": "MEDIUM"
        },
        "dateTimeStart": "2022-04-06T11:45:02.454316",
        "dateTimeFinish": "2022-04-07T11:45:02.454316"
    },
    {
        "idTask": 77,
        "name": "PEPZodKbmW",
        "message": "JhNtdKNGyU",
        "userEntity": {
            "idUser": 1,
            "name": "nesterov",
            "lastName": "nikita",
            "username": "kosto",
            "email": "mail@yandex.ru",
            "password": "$2a$12$9p4/D5TzCWoNeuROhS8XJ.Q35P0lh4PB5Dd7FDK3HM6pxkVjoficW",
            "roles": [
                {
                    "id": 2,
                    "name": "ROLE_ADMIN"
                }
            ],
            "isAccountActive": true,
            "isAccountNonBlock": true
        },
        "priority": {
            "id": 2,
            "name": "MEDIUM"
        },
        "dateTimeStart": "2022-04-06T11:45:03.710169",
        "dateTimeFinish": "2022-04-07T11:45:03.710169"
    },
    {
        "idTask": 78,
        "name": "OvYJjvKnGD",
        "message": "pVjbCLBZRD",
        "userEntity": {
            "idUser": 1,
            "name": "nesterov",
            "lastName": "nikita",
            "username": "kosto",
            "email": "mail@yandex.ru",
            "password": "$2a$12$9p4/D5TzCWoNeuROhS8XJ.Q35P0lh4PB5Dd7FDK3HM6pxkVjoficW",
            "roles": [
                {
                    "id": 2,
                    "name": "ROLE_ADMIN"
                }
            ],
            "isAccountActive": true,
            "isAccountNonBlock": true
        },
        "priority": {
            "id": 2,
            "name": "MEDIUM"
        },
        "dateTimeStart": "2022-04-06T11:45:04.024275",
        "dateTimeFinish": "2022-04-07T11:45:04.024275"
    }
]
```

####get tasks from user on page between data
```
GET /api/tasks/user/{page}/{dateTimeStart}/{dateTimeFinish} USER ADMIN
```
Request
```
http://localhost:8082/api/tasks/user/0/2022-04-12T12:50:11.284049/2022-04-13T12:50:11.284049
```
Response
```
Status: 200 OK
Content-Type: application/json
```
```
[
    {
        "idTask": 119,
        "name": "SrMVGfsDSN",
        "message": "sSTuGrKRpD",
        "userEntity": {
            "idUser": 1,
            "name": "nesterov",
            "lastName": "nikita",
            "username": "kosto",
            "email": "mail@yandex.ru",
            "password": "$2a$12$9p4/D5TzCWoNeuROhS8XJ.Q35P0lh4PB5Dd7FDK3HM6pxkVjoficW",
            "roles": [
                {
                    "id": 2,
                    "name": "ROLE_ADMIN"
                }
            ],
            "isAccountActive": true,
            "isAccountNonBlock": true
        },
        "priority": {
            "id": 2,
            "name": "MEDIUM"
        },
        "dateTimeStart": "2022-04-06T23:35:42.166482",
        "dateTimeFinish": "2022-04-07T23:35:42.166482"
    },
    {
        "idTask": 76,
        "name": "RLPYuErgTO",
        "message": "WyBVeebWvl",
        "userEntity": {
            "idUser": 1,
            "name": "nesterov",
            "lastName": "nikita",
            "username": "kosto",
            "email": "mail@yandex.ru",
            "password": "$2a$12$9p4/D5TzCWoNeuROhS8XJ.Q35P0lh4PB5Dd7FDK3HM6pxkVjoficW",
            "roles": [
                {
                    "id": 2,
                    "name": "ROLE_ADMIN"
                }
            ],
            "isAccountActive": true,
            "isAccountNonBlock": true
        },
        "priority": {
            "id": 2,
            "name": "MEDIUM"
        },
        "dateTimeStart": "2022-04-06T11:45:02.454316",
        "dateTimeFinish": "2022-04-07T11:45:02.454316"
    },
    {
        "idTask": 77,
        "name": "PEPZodKbmW",
        "message": "JhNtdKNGyU",
        "userEntity": {
            "idUser": 1,
            "name": "nesterov",
            "lastName": "nikita",
            "username": "kosto",
            "email": "mail@yandex.ru",
            "password": "$2a$12$9p4/D5TzCWoNeuROhS8XJ.Q35P0lh4PB5Dd7FDK3HM6pxkVjoficW",
            "roles": [
                {
                    "id": 2,
                    "name": "ROLE_ADMIN"
                }
            ],
            "isAccountActive": true,
            "isAccountNonBlock": true
        },
        "priority": {
            "id": 2,
            "name": "MEDIUM"
        },
        "dateTimeStart": "2022-04-06T11:45:03.710169",
        "dateTimeFinish": "2022-04-07T11:45:03.710169"
    },
    {
        "idTask": 78,
        "name": "OvYJjvKnGD",
        "message": "pVjbCLBZRD",
        "userEntity": {
            "idUser": 1,
            "name": "nesterov",
            "lastName": "nikita",
            "username": "kosto",
            "email": "mail@yandex.ru",
            "password": "$2a$12$9p4/D5TzCWoNeuROhS8XJ.Q35P0lh4PB5Dd7FDK3HM6pxkVjoficW",
            "roles": [
                {
                    "id": 2,
                    "name": "ROLE_ADMIN"
                }
            ],
            "isAccountActive": true,
            "isAccountNonBlock": true
        },
        "priority": {
            "id": 2,
            "name": "MEDIUM"
        },
        "dateTimeStart": "2022-04-06T11:45:04.024275",
        "dateTimeFinish": "2022-04-07T11:45:04.024275"
    },
    {
        "idTask": 79,
        "name": "EZWwKHeQkw",
        "message": "lVTszKLiGb",
        "userEntity": {
            "idUser": 1,
            "name": "nesterov",
            "lastName": "nikita",
            "username": "kosto",
            "email": "mail@yandex.ru",
            "password": "$2a$12$9p4/D5TzCWoNeuROhS8XJ.Q35P0lh4PB5Dd7FDK3HM6pxkVjoficW",
            "roles": [
                {
                    "id": 2,
                    "name": "ROLE_ADMIN"
                }
            ],
            "isAccountActive": true,
            "isAccountNonBlock": true
        },
        "priority": {
            "id": 2,
            "name": "MEDIUM"
        },
        "dateTimeStart": "2022-04-06T11:45:04.317301",
        "dateTimeFinish": "2022-04-07T11:45:04.317301"
    },
    {
        "idTask": 80,
        "name": "oiGMzsTaxU",
        "message": "LVsvueMrmr",
        "userEntity": {
            "idUser": 1,
            "name": "nesterov",
            "lastName": "nikita",
            "username": "kosto",
            "email": "mail@yandex.ru",
            "password": "$2a$12$9p4/D5TzCWoNeuROhS8XJ.Q35P0lh4PB5Dd7FDK3HM6pxkVjoficW",
            "roles": [
                {
                    "id": 2,
                    "name": "ROLE_ADMIN"
                }
            ],
            "isAccountActive": true,
            "isAccountNonBlock": true
        },
        "priority": {
            "id": 2,
            "name": "MEDIUM"
        },
        "dateTimeStart": "2022-04-06T11:45:04.6013",
        "dateTimeFinish": "2022-04-07T11:45:04.6013"
    },
    {
        "idTask": 81,
        "name": "MaZuRRvipK",
        "message": "fpyJXDvNUL",
        "userEntity": {
            "idUser": 1,
            "name": "nesterov",
            "lastName": "nikita",
            "username": "kosto",
            "email": "mail@yandex.ru",
            "password": "$2a$12$9p4/D5TzCWoNeuROhS8XJ.Q35P0lh4PB5Dd7FDK3HM6pxkVjoficW",
            "roles": [
                {
                    "id": 2,
                    "name": "ROLE_ADMIN"
                }
            ],
            "isAccountActive": true,
            "isAccountNonBlock": true
        },
        "priority": {
            "id": 2,
            "name": "MEDIUM"
        },
        "dateTimeStart": "2022-04-06T11:45:04.898299",
        "dateTimeFinish": "2022-04-07T11:45:04.898299"
    },
    {
        "idTask": 83,
        "name": "AEjfCkelTT",
        "message": "HVCNszoSIq",
        "userEntity": {
            "idUser": 1,
            "name": "nesterov",
            "lastName": "nikita",
            "username": "kosto",
            "email": "mail@yandex.ru",
            "password": "$2a$12$9p4/D5TzCWoNeuROhS8XJ.Q35P0lh4PB5Dd7FDK3HM6pxkVjoficW",
            "roles": [
                {
                    "id": 2,
                    "name": "ROLE_ADMIN"
                }
            ],
            "isAccountActive": true,
            "isAccountNonBlock": true
        },
        "priority": {
            "id": 2,
            "name": "MEDIUM"
        },
        "dateTimeStart": "2022-04-06T11:46:02.777025",
        "dateTimeFinish": "2022-04-07T11:46:02.777025"
    },
    {
        "idTask": 84,
        "name": "VXcqosdaoI",
        "message": "WKJNVqDZCc",
        "userEntity": {
            "idUser": 1,
            "name": "nesterov",
            "lastName": "nikita",
            "username": "kosto",
            "email": "mail@yandex.ru",
            "password": "$2a$12$9p4/D5TzCWoNeuROhS8XJ.Q35P0lh4PB5Dd7FDK3HM6pxkVjoficW",
            "roles": [
                {
                    "id": 2,
                    "name": "ROLE_ADMIN"
                }
            ],
            "isAccountActive": true,
            "isAccountNonBlock": true
        },
        "priority": {
            "id": 2,
            "name": "MEDIUM"
        },
        "dateTimeStart": "2022-04-06T11:46:04.024318",
        "dateTimeFinish": "2022-04-07T11:46:04.024318"
    },
    {
        "idTask": 85,
        "name": "FpHHOetWvC",
        "message": "YSSNyRYayM",
        "userEntity": {
            "idUser": 1,
            "name": "nesterov",
            "lastName": "nikita",
            "username": "kosto",
            "email": "mail@yandex.ru",
            "password": "$2a$12$9p4/D5TzCWoNeuROhS8XJ.Q35P0lh4PB5Dd7FDK3HM6pxkVjoficW",
            "roles": [
                {
                    "id": 2,
                    "name": "ROLE_ADMIN"
                }
            ],
            "isAccountActive": true,
            "isAccountNonBlock": true
        },
        "priority": {
            "id": 2,
            "name": "MEDIUM"
        },
        "dateTimeStart": "2022-04-06T11:46:04.337341",
        "dateTimeFinish": "2022-04-07T11:46:04.337341"
    }
]
```
###Users

Only Authorized

####GET USERS
```
    GET /api/users ADMIN
```
Request
```
http://localhost:8082/api/users
```

Response
```
Status: 200 OK
Content-Type: application/json
```
```
[
    {
        "idUser": 102,
        "name": null,
        "lastName": null,
        "username": "nesterov",
        "email": "neesterov1@gmail.com",
        "password": "$2a$12$R71DSs1l25vor3RXFNywD.E4Kw4/6zyThO8gAp19LoO47t6h.mWIi",
        "roles": [
            {
                "id": 3,
                "name": "ROLE_USER"
            }
        ],
        "isAccountActive": true,
        "isAccountNonBlock": true
    },
    {
        "idUser": 103,
        "name": null,
        "lastName": null,
        "username": "pass",
        "email": "mail@mail.ru",
        "password": "$2a$12$tOlctdpR7QzN99hjRjs.x.DJCGmFJ3vEdrIWN0QYxBlRt9h9mSooW",
        "roles": [
            {
                "id": 2,
                "name": "ROLE_ADMIN"
            }
        ],
        "isAccountActive": false,
        "isAccountNonBlock": true
    },
    {
        "idUser": 104,
        "name": null,
        "lastName": null,
        "username": "user",
        "email": "ya@mail.ru",
        "password": "$2a$12$A/e7TFfMMj9uP0zlSRDOQOO0DTQh/rI60DBWoCpCcftIm3q2zKiuy",
        "roles": [
            {
                "id": 3,
                "name": "ROLE_USER"
            }
        ],
        "isAccountActive": true,
        "isAccountNonBlock": true
    },
    {
        "idUser": 1,
        "name": "nesterov",
        "lastName": "nikita",
        "username": "kosto",
        "email": "nesterow14@yandex.ru",
        "password": "$2a$12$9p4/D5TzCWoNeuROhS8XJ.Q35P0lh4PB5Dd7FDK3HM6pxkVjoficW",
        "roles": [
            {
                "id": 2,
                "name": "ROLE_ADMIN"
            }
        ],
        "isAccountActive": true,
        "isAccountNonBlock": true
    }
]
```
#### GET USERS BY LAST name
```
    GET /api/users/lastName/{lastName} ADMIN  
```
Request
```
http://localhost:8082/api/users/lastName/nikita
```
Response
```
Status: 200 OK
Content-Type: application/json
```
```
[
    {
        "idUser": 1,
        "name": "nesterov",
        "lastName": "nikita",
        "username": "kosto",
        "email": "nesterow14@yandex.ru",
        "password": "$2a$12$9p4/D5TzCWoNeuROhS8XJ.Q35P0lh4PB5Dd7FDK3HM6pxkVjoficW",
        "roles": [
            {
                "id": 2,
                "name": "ROLE_ADMIN"
            }
        ],
        "isAccountActive": true,
        "isAccountNonBlock": true
    }
]
```


#### GET USERS BY LAST name
```
   GET /api/users/userName/{userName} ADMIN
```
Request
```
http://localhost:8082/api/users/lastName/nikita
```
Response
```
[
    {
        "idUser": 1,
        "name": "nesterov",
        "lastName": "nikita",
        "username": "kosto",
        "email": "nesterow14@yandex.ru",
        "password": "$2a$12$9p4/D5TzCWoNeuROhS8XJ.Q35P0lh4PB5Dd7FDK3HM6pxkVjoficW",
        "roles": [
            {
                "id": 2,
                "name": "ROLE_ADMIN"
            }
        ],
        "isAccountActive": true,
        "isAccountNonBlock": true
    }
]
```

#### DELETE USER BY ID
```
    DELETE /api/users/{idUser} ADMIN
```
Request
```
   http://localhost:8082/api/users/1
```
Response
```
Status: 200 OK
Content-Type: application/json
```
#### GET user by ID
```
    GET /api/users/{idUser} ADMIN
```
Request
```
    http://localhost:8082/api/users/1
```
Response
```
Status: 200 OK
Content-Type: application/json
```
```
[
    {
        "idUser": 1,
        "name": "nesterov",
        "lastName": "nikita",
        "username": "kosto",
        "email": "nesterow14@yandex.ru",
        "password": "$2a$12$9p4/D5TzCWoNeuROhS8XJ.Q35P0lh4PB5Dd7FDK3HM6pxkVjoficW",
        "roles": [
            {
                "id": 2,
                "name": "ROLE_ADMIN"
            }
        ],
        "isAccountActive": true,
        "isAccountNonBlock": true
    }
]
```

#### add user
```
    POST /api/users ADMIN
```
Request
```
http://localhost:8082/api/users
```
```
{
            "name": "nikita",
            "lastName": "nest",
            "username": "kostos",
            "email": "mail@yandex.ru",
            "password": "$2a$12$9p4/D5TzCWoNeuROhS8XJ.Q35P0lh4PB5Dd7FDK3HM6pxkVjoficW",
            "roles": [
                {
                    "id": 2,
                    "name": "ROLE_ADMIN"
                }
             ],
            "isAccountActive": true,
            "isAccountNonBlock": true
}
```
Response
```
Status: 200 OK
Content-Type: application/json
```
```
{
    "idUser": 108,
    "name": "nikita",
    "lastName": "nest",
    "username": "kostos",
    "email": "mail@yandex.ru",
    "password": "$2a$12$9p4/D5TzCWoNeuROhS8XJ.Q35P0lh4PB5Dd7FDK3HM6pxkVjoficW",
    "roles": [
        {
            "id": 2,
            "name": "ROLE_ADMIN"
        }
    ],
    "isAccountActive": false,
    "isAccountNonBlock": true
}
```
#### update user
```
    PUT /api/users ADMIN        
```
Request
```
http://localhost:8082/api/users
```
```
{
    "idUser": 108,
    "name": "nik",
    "lastName": "nest",
    "username": "kostos",
    "email": "mail@yandex.ru",
    "password": "$2a$12$9p4/D5TzCWoNeuROhS8XJ.Q35P0lh4PB5Dd7FDK3HM6pxkVjoficW",
    "roles": [
        {
            "id": 2,
            "name": "ROLE_ADMIN"
        }
    ],
    "isAccountActive": true,
    "isAccountNonBlock": true
}
```
Response
```
{
    "idUser": 108,
    "name": "nik",
    "lastName": "nest",
    "username": "kostos",
    "email": "mail@yandex.ru",
    "password": "$2a$12$9p4/D5TzCWoNeuROhS8XJ.Q35P0lh4PB5Dd7FDK3HM6pxkVjoficW",
    "roles": [
        {
            "id": 2,
            "name": "ROLE_ADMIN"
        }
    ],
    "isAccountActive": true,
    "isAccountNonBlock": true
}
```
#### block/unblock user
```
    POST /api/users/block/{idUser}/{isBlock} ADMIN
```
Request
```
http://localhost:8082/api/users/block/105/false
```
Response
```
Status: 200 OK
Content-Type: application/json
```
```
{
    "idUser": 105,
    "name": "SrMVGfsDSN",
    "lastName": null,
    "username": null,
    "email": null,
    "password": null,
    "roles": [],
    "isAccountActive": false,
    "isAccountNonBlock": false
}
```