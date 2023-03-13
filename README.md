# Pizza Shop Demo Application

To run the stack

```bash
mvn intall
docker-compose up -d
```

To test

```bash
curl --location 'http://localhost:10010/' \
--header 'Content-Type: application/json' \
--data '{"customerId": "12345678910", "pizza": "pepperoni"}'
```