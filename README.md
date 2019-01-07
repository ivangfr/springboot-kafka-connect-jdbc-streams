# springboot-kafka-connect-streams

The goal of this project is ...

## Microservices

### store-api

### store-kafka-streams

## Hosts & Ports

```
ZooKeeper                           | 2181
Apache Kafka broker                 | 9092
MySQL                               | 3306
Elasticsearch REST API              | http://localhost:9200
Kafka REST Proxy                    | http://localhost:8082
Kafka Connect REST API              | http://localhost:8083
Kafka Connect UI (Web)              | http://localhost:8086
Kafka Topics UI (Web)               | http://localhost:8085
Schema Registry REST API            | http://localhost:8081
Schema Registry (Web)               | http://localhost:8001
Kibana (Web)                        | http://localhost:5601
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

3. Wait for `store-api` to be up and running. It is configured to create all needed tables on `mysql`.


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

- change `order` entity `id` from `Long` to `String (UUID)`
- drop the store-mysql- prefix from the topic name and thus Elasticsearch index name (see https://www.confluent.io/blog/simplest-useful-kafka-connect-data-pipeline-world-thereabouts-part-3)
something like
```
    "_comment": "SMT (Single Message Transform), it drops the store-mysql- prefix from the topic name. So, the Elasticsearch index name will be the table name ---",
    "transforms": "dropPrefix",
    "transforms.dropPrefix.type":"org.apache.kafka.connect.transforms.RegexRouter",
    "transforms.dropPrefix.regex":"store-mysql-(.*)",
    "transforms.dropPrefix.replacement":"$1"
```
- implement `store-kafka-streams` service

## References

- https://www.confluent.io/blog/simplest-useful-kafka-connect-data-pipeline-world-thereabouts-part-1 (2 and 3)