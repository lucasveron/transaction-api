# Transaction API

### Running :runner:

For running project alone(need a up Redis):

```bash
$ gradlew bootRun
```

The app runs over http://localhost:8080<br>
Redis does it over localhost:6379
### Docker build image 🔨
```bash
$ docker build -t mpayments:0.0.1 .
```

### Run docker compose(run into root project) & first run docker build image 🚀
```bash
$ docker-compose up -d
```

## Endpoints
### Create transaction
```
curl --location --request PUT 'http://localhost:8080/transactions/3' \
--header 'Content-Type: application/json' \
--data-raw '{
    "amount": 100.0,
    "type": "market",
    "parent_id": 1
}'
```
### Request Reference
<b>amount:</b> total amount for transaction<br>
<b>type:</b> type of transaction. Must be not blank<br>
<b>parent_id:</b> identification of dependency for other transaction<br>

### Retrieve al ids for transaction type {type}
```
curl --location --request GET 'http://localhost:8080/transactions/types/{type}'
```
### Request Reference
<b>{type}:</b> type of transaction<br>

### Retrieve sum of amount transactions with same parent_id
```
curl --location --request GET 'http://localhost:8080/transactions/sum/{parent_id}'
```
### Request Reference
<b>{parent_id}:</b> identification of parent_id
