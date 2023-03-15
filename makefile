help:
	@fgrep -h "##" $(MAKEFILE_LIST) | fgrep -v fgrep | sed -e 's/\\$$//' | sed -e 's/##//'

up: setup install compose-up health ##Installs the loki docker plugin, builds and installs maven projects, lauches stack and waits for health check

install: ##Builds and installs maven projects
	./mvnw install

compose-up: ##Launch pizza shop stack
	docker-compose up -d

down: ##Stop pizza shop stack
	docker-compose down

test: ##Send some test traffic to the web receiver
	curl -v --location 'http://localhost:10010/' \
	--header 'Content-Type: application/json' \
	--data '{"customerId": "12345678910", "pizza": "pepperoni"}'
	curl -v --location 'http://localhost:10010/' \
	--header 'Content-Type: application/json' \
	--data '{"customerId": "12345678911", "pizza": "margherita"}'
	curl -v --location 'http://localhost:10010/' \
	--header 'Content-Type: application/json' \
	--data '{"customerId": "12345678912", "pizza": "hawaiian"}'

health: ##Launch pizza shop stack
	until curl -sf "http://localhost:10010/actuator/health"; do echo "waiting for healthcheck..."; sleep 5; done

setup: ##Installs the loki docker plugin
	docker plugin install grafana/loki-docker-driver:latest --alias loki --grant-all-permissions || true
