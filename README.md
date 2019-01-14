# springboot-kafka-connect-streams

## Goal

The main goal of this project is to play with [Kafka](https://kafka.apache.org),
[Kafka Connect](https://docs.confluent.io/current/connect/index.html) and
[Kafka Streams](https://docs.confluent.io/current/streams/index.html). For this, we have the `store-api` that
inserts/updates records in [MySQL](https://www.mysql.com) database; some `Kafka source connectors` that reads from MySQL
and push to Kafka; some `Kafka sink connectors` that reads event from Kafka and inserts in
[Elasticsearch](https://www.elastic.co); finally, the `store-streams` that reads data from Kafka, treats them using
Kafka Streams and push new events Kafka.

## Branches

The project has 2 branches:

- `master`: uses `JSON` serialization format;
- `avro-serialization-format`: uses `Avro` serialization format.

## Microservices

![project-diagram](images/project-diagram.png)

### store-api

Monolithic spring-boot application that exposes a REST API to manage Customers, Products and Orders. The data is saved
in MySQL. This application does not connect directly to Kafka. 

### store-streams

Spring-boot application that connects Kafka and uses Kafka Streams to transform some input topics into a new topic in
Kafka.  

## Start Environment

### Docker Compose

1. Open one terminal

2. Inside `/springboot-kafka-connect-streams` root folder run
```
docker-compose up -d
```
> To stop and remove containers, networks and volumes type:
> ```
> docker-compose down -v
> ```

- Wait a little bit until all containers are `Up (healthy)`
- In order to check the status of the containers run the command
```
docker-compose ps
```

### Start store-api

1. Open a new terminal

2. In `/springboot-kafka-connect-streams/store-api` folder, run
```
mvn spring-boot:run
```

3. Wait for `store-api` to be up and running. It is configured to create all needed tables on `mysql`.

4. Its swagger website is http://localhost:9080/swagger-ui.html

### Create connectors

1. In a terminal, run the following script to create the connectors on `kafka-connect`
```
./create-connectors.sh
```

2. At the end of the script, it shows (besides other info) the state connectors and their tasks. You must see something like
```
{"name":"mysql-source-customers","connector":{"state":"RUNNING","worker_id":"kafka-connect:8083"},"tasks":[{"state":"RUNNING","id":0,"worker_id":"kafka-connect:8083"}],"type":"source"}
{"name":"mysql-source-products","connector":{"state":"RUNNING","worker_id":"kafka-connect:8083"},"tasks":[{"state":"RUNNING","id":0,"worker_id":"kafka-connect:8083"}],"type":"source"}
{"name":"mysql-source-orders","connector":{"state":"RUNNING","worker_id":"kafka-connect:8083"},"tasks":[{"state":"RUNNING","id":0,"worker_id":"kafka-connect:8083"}],"type":"source"}
{"name":"mysql-source-orders_products","connector":{"state":"RUNNING","worker_id":"kafka-connect:8083"},"tasks":[{"state":"RUNNING","id":0,"worker_id":"kafka-connect:8083"}],"type":"source"}
{"name":"elasticsearch-sink-customers","connector":{"state":"RUNNING","worker_id":"kafka-connect:8083"},"tasks":[{"state":"RUNNING","id":0,"worker_id":"kafka-connect:8083"}],"type":"sink"}
{"name":"elasticsearch-sink-products","connector":{"state":"RUNNING","worker_id":"kafka-connect:8083"},"tasks":[{"state":"RUNNING","id":0,"worker_id":"kafka-connect:8083"}],"type":"sink"}
{"name":"elasticsearch-sink-orders","connector":{"state":"RUNNING","worker_id":"kafka-connect:8083"},"tasks":[{"state":"RUNNING","id":0,"worker_id":"kafka-connect:8083"}],"type":"sink"}
```
**Connectors and their tasks must have a RUNNING state**

3. You can also check the state of the connectors and their tasks at http://localhost:8086

4. If there is any problem, you can check `kafka-connect` container logs.
```
docker logs kafka-connect -f
```

### Start store-streams

1. Open a new terminal

2. In `/springboot-kafka-connect-streams/store-streams` folder, run
```
mvn spring-boot:run
```

3. Its swagger website is http://localhost:9081/swagger-ui.html

> Note: the command below generates Java classes from Avro files
> ```
> mvn generate-sources
> ```

## Useful Links/Commands

### Elasticsearch

- Elasticsearch can be accessed at http://localhost:9200

- You can use `curl` to check some documents, for example in `store-mysql-customers` index
```
curl http://localhost:9200/store-mysql-customers/_search?pretty
```

### Kafka Topics UI

- Kafka Topics UI can be accessed at http://localhost:8085

### Kafka Connect UI

- Kafka Connect UI can be accessed at http://localhost:8086

### Schema Registry UI

- Schema Registry UI can be accessed at http://localhost:8001

### Schema Registry

- You can use `curl` to check the subjects in Schema Registry

1. Get the list of subjects
```
curl localhost:8081/subjects
```
2. Get the latest version of the subject `store-mysql-customers-value`
```
curl http://localhost:8081/subjects/store-mysql-customers-value/versions/latest
```

### Kibana

- Kibana can be accessed at http://localhost:5601

### Kafkacat

```
docker run --tty --interactive --rm --network=springboot-kafka-connect-streams_default \
  confluentinc/cp-kafkacat:5.1.0 kafkacat -b kafka:9092\
  -f '\nKey (%K bytes): %k\t\nValue (%S bytes): %s\n\Partition: %p\tOffset: %o\n--\n' \
  -t store-mysql-customers -C -c1
```

## References

- https://www.confluent.io/blog/simplest-useful-kafka-connect-data-pipeline-world-thereabouts-part-1 (2 and 3)
- https://www.confluent.io/blog/kafka-connect-deep-dive-converters-serialization-explained

## Issues

- Product `price` field, [numeric.mapping doesn't work for DECIMAL fields #563](https://github.com/confluentinc/kafka-connect-jdbc/issues/563)