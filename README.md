# Moneytransferservice

Приложение для подключение к сервису перевода денег
(`https://github.com/serp-ya/card-transfer`)

Порт для подключения: 

`http://localhost:5050/`

Команда для запуска:

`docker run -itd --name moneytransferservice -p 5050:5050 moneytransferservice:latest`

Реализован запус через docker compose вместа с FRONT частью:

`docker compose up`