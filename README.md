
## __Описание__

### Ссылки для проверки апи 
>http://localhost:8081/swagger-ui/index.html#
> http://localhost:8080/swagger-ui/index.html#

#### ___Чтобы получить JWT токен:___ 

Cоздайте пользователя при помощи метода (порт 8080):
>/support/create-user

Авторизуйтесь при помощи (порт 8081):
>/auth/login

### ___Запуск и компиляция___

Компиляция

>mvn clean install -DskipTests

>docker-compose build

Запуск

>docker-compose up


### ___Stack___ 

- redis

- spring boot 3

- postgres