# springboot-kafka-connect-streams

The goal of this project is ...

## Microservices

### store-api

### store-kafka-streams

## Hosts & Ports

```
Apache Kafka broker (plain text)    | 9092
Elasticsearch REST API              | http://localhost:9200
Kafka REST Proxy                    | http://localhost:8082
Kafka Connect REST API              | http://localhost:8083
Kafka Connect UI (Web)              | http://localhost:8086
Kafka Topics UI (Web)               | http://localhost:8085
Schema Registry REST API            | http://localhost:8081
Schema Registry (Web)               | http://localhost:8001
ZooKeeper                           | 2181
MySQL                               | 3306
```

## Start Environment

### Docker Compose

1. Open one terminal

2. Inside `/springboot-kafka-connect-streams` root folder run
```
docker-compose up -d
```
> To stop and remove containers, networks, images, and volumes type:
> ```
> docker-compose down -v
> ```

- Wait a little bit until all containers are `Up (healthy)`
- In order to check the status of the containers run the command
```
docker-compose ps
```

### Start store-api

1. Go to a terminal

2. In `/springboot-kafka-connect-streams/store-api` folder, run the following command
```
mvn clean spring-boot:run
```

3. Wait for `store-api` to be up and running. It is configured to create the needed tables on `mysql`.


### Create connectors

1. In a terminal, run the following script to create the connectors on `kafka-connect`
```
./create-connectors.sh
```

2. You can check the status of the connectors at http://localhost:8086

3. If there is any problem, you can check the logs in `kafka-connect` container
```
docker logs kafka-connect -f
```

## TODO

- read https://www.confluent.io/blog/simplest-useful-kafka-connect-data-pipeline-world-thereabouts-part-1
- use `transformers` to create the `key` (see https://www.confluent.io/blog/simplest-useful-kafka-connect-data-pipeline-world-thereabouts-part-3)
- change `order` entity `id` from `Long` to `String (UUID)`
- implement `store-kafka-streams` service

## References