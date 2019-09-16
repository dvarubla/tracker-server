Для запуска необходимо:

1. Собрать shaded jar: `mvn clean package`. Получится файл `testtask-1.0-SNAPSHOT-shaded.jar`
2. Создать базу данных PostgreSQL, допустим `mytesttask`
3. Скопировать файлы клиента в каталог testtaskstatic на один уровень с `testtask-1.0-SNAPSHOT-shaded.jar`. То есть в одном каталоге должны быть jar и каталог testtaskstatic, в котором находится index.html
3. Запустить программу таким образом: `java -jar testtask-1.0-SNAPSHOT-shaded.jar -port порт_приложения -db сервер_базы порт_базы имя_базы пользователь пароль`, например,  `java -jar testtask-1.0-SNAPSHOT-shaded.jar -port 13333 -db 127.0.0.1 5432 mytesttask postgres 12345` Для запуска требуется Java 8.
4. С аргументом `--debug` каждый раз при запуске будет происходить пересоздание базы

Главная страница находится по адресу `/static/index.html`, документация по адресу `/swagger-ui.html`