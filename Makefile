build:
	mvn clean package

migrate:
	mvn -Dflyway.configFiles=flyway.conf flyway:migrate

up:
	docker compose up -d --build

down:
	docker compose down