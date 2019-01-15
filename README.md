# springboot-kafka-connect-streams

## Goal

The main goal of this project is to play with [Kafka](https://kafka.apache.org),
[Kafka Connect](https://docs.confluent.io/current/connect/index.html) and
[Kafka Streams](https://docs.confluent.io/current/streams/index.html). For this, we have: `store-api` that
inserts/updates records in [MySQL](https://www.mysql.com); `Kafka source connectors` that monitor
inserted/updated records in MySQL and push messages related to those changes to Kafka; `Kafka sink connectors` that
read messages from Kafka and insert/update documents in [Elasticsearch](https://www.elastic.co); finally, `store-streams` that
listens for messages in Kafka, treats them using Kafka Streams and push new messages back to Kafka.

## Microservices

![project-diagram](images/project-diagram.png)

### store-api

Monolithic spring-boot application that exposes a REST API to manage Customers, Products and Orders. The data is saved
in MySQL. 

### store-streams

Spring-boot application that connects to Kafka and uses Kafka Streams API to transform some "input" topics into a new
"output" topic in Kafka.

## Serialization/Deserialization (SerDes) formats

In order to run this project, you can use [JSON](https://www.json.org) or
[Avro](http://avro.apache.org/docs/current/gettingstartedjava.html) formats for data serialization/deserialization
to/from Kafka. The default format is `JSON`. Throughout this document, I will point out what to do if you want to use
`Avro`.

**P.S. Avro SerDes is a work in progress and it is not completely implemented!**

## Start Environment

### Docker Compose

1. Open one terminal

2. Inside `/springboot-kafka-connect-streams` root folder run

**For JSON SerDes**
```
docker-compose up -d
```

**For Avro SerDes**
```
export CONNECT_KEY_CONVERTER=io.confluent.connect.avro.AvroConverter 
export CONNECT_VALUE_CONVERTER=io.confluent.connect.avro.AvroConverter

docker-compose up -d
```
> Note 1.
> The file `docker-compose.yml` has two environment variables: `CONNECT_KEY_CONVERTER` and `CONNECT_VALUE_CONVERTER`.
> The default value is defined in `.env` file and is `org.apache.kafka.connect.json.JsonConverter` for `KEY` and `VALUE`.
> So, we just need to change in case we are configuring `For Avro SerDes`.

> Note 2.
> To stop and remove containers, networks and volumes type:
> ```
> docker-compose down -v
> ```

3. Wait a little bit until all containers are `Up (healthy)`. In order to check the status of the containers run the command
```
docker-compose ps
```

### Start store-api

1. Open a new terminal

2. In `/springboot-kafka-connect-streams/store-api` folder, run
```
mvn spring-boot:run
```

3. Wait for `store-api` to be up and running. It is configured to create all needed tables on MySQL.

4. Its swagger website is http://localhost:9080/swagger-ui.html

![store-api-swagger](images/store-api-swagger.png)

### Create connectors

1. In a terminal, run the following script to create the connectors on `kafka-connect`

**For JSON SerDes**
```
./create-connectors-jsonconverter.sh
```

**For Avro SerDes**
```
./create-connectors-avroconverter.sh
```

2. You can check the state of the connectors and their tasks on `Kafka Connect UI` (http://localhost:8086) or
running the following script
```
./check-connectors-state.sh
```

Once the connectors and their tasks are ready (RUNNING state), you should see something like
```
{"name":"mysql-source-customers","connector":{"state":"RUNNING","worker_id":"kafka-connect:8083"},"tasks":[{"state":"RUNNING","id":0,"worker_id":"kafka-connect:8083"}],"type":"source"}
{"name":"mysql-source-products","connector":{"state":"RUNNING","worker_id":"kafka-connect:8083"},"tasks":[{"state":"RUNNING","id":0,"worker_id":"kafka-connect:8083"}],"type":"source"}
{"name":"mysql-source-orders","connector":{"state":"RUNNING","worker_id":"kafka-connect:8083"},"tasks":[{"state":"RUNNING","id":0,"worker_id":"kafka-connect:8083"}],"type":"source"}
{"name":"mysql-source-orders_products","connector":{"state":"RUNNING","worker_id":"kafka-connect:8083"},"tasks":[{"state":"RUNNING","id":0,"worker_id":"kafka-connect:8083"}],"type":"source"}
{"name":"elasticsearch-sink-customers","connector":{"state":"RUNNING","worker_id":"kafka-connect:8083"},"tasks":[{"state":"RUNNING","id":0,"worker_id":"kafka-connect:8083"}],"type":"sink"}
{"name":"elasticsearch-sink-products","connector":{"state":"RUNNING","worker_id":"kafka-connect:8083"},"tasks":[{"state":"RUNNING","id":0,"worker_id":"kafka-connect:8083"}],"type":"sink"}
{"name":"elasticsearch-sink-orders","connector":{"state":"RUNNING","worker_id":"kafka-connect:8083"},"tasks":[{"state":"RUNNING","id":0,"worker_id":"kafka-connect:8083"}],"type":"sink"}
```

On Kafka Connect UI, you should see
![kafka-connect-ui](images/kafka-connect-ui.png)

4. If there is any problem, you can check `kafka-connect` container logs.
```
docker logs kafka-connect -f
```

5. Connectors use *Converters* for data serialization and deserialization. If you are configuring `For JSON SerDes`, the
converter used is `JsonConverter`. On the other hand, if the configuration is `For Avro SerDes`, the converter used is
`AvroConverter`.

**IMPORTANT**: if the Source Connector Converter serializes data, for instance, from `JSON` to `Bytes` (using
`JsonConverter`), then the Sink Connector Converter must also use `JsonConverter` to deserialize the `Bytes`,
otherwise an error will be thrown. The document
[Kafka Connect Deep Dive – Converters and Serialization Explained](https://www.confluent.io/blog/kafka-connect-deep-dive-converters-serialization-explained)
explains it very well.

### Start store-streams

1. Open a new terminal

2. In `/springboot-kafka-connect-streams/store-streams` folder, run

**For JSON SerDes**
```
mvn spring-boot:run
```

**(NOT READY!) For Avro SerDes**
```
mvn spring-boot:run -Dspring-boot.run.profiles=avroconverter
```
> Note: the command below generates Java classes from Avro files
> ```
> mvn generate-sources
> ```

3. This service runs on port `9081`. The `health` endpoint is http://localhost:9081/actuator/health

## Useful Links/Commands

### Elasticsearch

- Elasticsearch can be accessed at http://localhost:9200

- You can use `curl` to check some documents, for example in `store-mysql-customers` index
```
curl http://localhost:9200/store-streams-orders/_search?pretty
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

## TODO

- adapt `store-streams` to run `For Avro SerDes`. I am having problem with making `spring-cloud-stream-kafka-streams`
and `Avro` work together.

## References

- https://www.confluent.io/blog/simplest-useful-kafka-connect-data-pipeline-world-thereabouts-part-1 (2 and 3)
- https://www.confluent.io/blog/kafka-connect-deep-dive-converters-serialization-explained

## Issues

- Product `price` field, [numeric.mapping doesn't work for DECIMAL fields #563](https://github.com/confluentinc/kafka-connect-jdbc/issues/563)