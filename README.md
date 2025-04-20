# Механизм учета финансов

## Начало работы
- создать .env файлы из example.env файлов в директориях
- make build
- make up
- make migrate

## Создание пользователя
POST /user/signup

body:
 - login string
 - password string

curl
```
curl --location 'localhost:8080/user/signup' \
--header 'Content-Type: application/json' \
--data '{
    "login":"lala",
    "password":"jopa"
}'
```

## Получение токена для пользователя
POST /user/login 

body:
 - login string
 - password string

curl
```
curl --location 'localhost:8080/user/login' \
--header 'Content-Type: application/json' \
--data '{
    "login":"lala",
    "password":"jopa"
}'
```

## Создание категории
POST /category 

headers:
- Auth string токен пользователя

body:
 - name string
 - limit string
 - type string "income/outcome"

curl
```
curl --location 'localhost:8080/category' \
--header 'Auth: token' \
--header 'Content-Type: application/json' \
--data '{
    "name":"Зарплата",
    "limit":0,
    "type":"income"
}'
```

## Добавление дохода
POST /operations/income 

headers:
- Auth string токен пользователя

body:
 - category string
 - amount string

curl
```
curl --location 'localhost:8080/operations/income' \
--header 'Auth: token' \
--header 'Content-Type: application/json' \
--data '{
    "category":"Зарплата",
    "amount":200
}'
```

## Добавление расхода
POST /operations/outcome 

headers:
- Auth string токен пользователя

body:
 - category string
 - amount string

curl
```
curl --location 'localhost:8080/operations/8' \
--header 'Auth: token' \
--header 'Content-Type: application/json' \
--data '{
    "category":"Книги",
    "amount":300
}'
```

## Получение бюджета
get /operations 

headers:
- Auth string токен пользователя

curl
```
curl --location 'localhost:8080/operations' \
--header 'Auth: token' \
```