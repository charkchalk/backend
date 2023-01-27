.PHONY: run-dev-db
run-dev-db:
	docker run -d --rm --name charkchalk-db -p 5432:5432 -e POSTGRES_USER=developer -e POSTGRES_PASSWORD=password123 postgres
	docker exec charkchalk-db psql -U developer -c 'CREATE DATABASE chark;'

.PHONY: stop-dev-db
stop-dev-db:
	docker stop charkchalk-db

.PHONY: run
run:
	gradle bootRun
