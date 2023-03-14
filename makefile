help:
	@fgrep -h "##" $(MAKEFILE_LIST) | fgrep -v fgrep | sed -e 's/\\$$//' | sed -e 's/##//'

up: ## build and launch pizza shop stack
	mvn install
	docker-compose up -d
	until curl -sf "http://localhost:10010/actuator/health"; do echo "waiting for healthcheck..."; sleep 5; done

down: ## stop pizza shop stack
	docker-compose down

test: ## send some test traffic to the stack
	curl -v --location 'http://localhost:10010/' \
	--header 'Content-Type: application/json' \
	--data '{"customerId": "12345678910", "pizza": "pepperoni"}'
	curl -v --location 'http://localhost:10010/' \
	--header 'Content-Type: application/json' \
	--data '{"customerId": "12345678911", "pizza": "margherita"}'
	curl -v --location 'http://localhost:10010/' \
	--header 'Content-Type: application/json' \
	--data '{"customerId": "12345678912", "pizza": "hawaiian"}'