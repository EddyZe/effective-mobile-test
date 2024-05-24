
## __Описание__

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

support api работает на порту 8081
client api работает на порту 8080

Открыть документацию после запуска

>http://localhost:8081/swagger-ui/index.html#
> http://localhost:8080/swagger-ui/index.html#

### ___Stack___ 

- redis

- spring boot 3

- postgres