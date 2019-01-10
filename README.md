# springboot-kafka-connect-streams

The goal of this project is ...

## Microservices

### store-api

### store-kafka-streams

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

2. At the end of the script, it shows (besides other info) the state connectors and their tasks. You must see something like
```
{"name":"mysql-source-customers","connector":{"state":"RUNNING","worker_id":"kafka-connect:8083"},"tasks":[{"state":"RUNNING","id":0,"worker_id":"kafka-connect:8083"}],"type":"source"}
{"name":"mysql-source-products","connector":{"state":"RUNNING","worker_id":"kafka-connect:8083"},"tasks":[{"state":"RUNNING","id":0,"worker_id":"kafka-connect:8083"}],"type":"source"}
{"name":"mysql-source-orders","connector":{"state":"RUNNING","worker_id":"kafka-connect:8083"},"tasks":[{"state":"RUNNING","id":0,"worker_id":"kafka-connect:8083"}],"type":"source"}
{"name":"mysql-source-orders_products","connector":{"state":"RUNNING","worker_id":"kafka-connect:8083"},"tasks":[{"state":"RUNNING","id":0,"worker_id":"kafka-connect:8083"}],"type":"source"}
{"name":"elasticsearch-sink-customers","connector":{"state":"RUNNING","worker_id":"kafka-connect:8083"},"tasks":[{"state":"RUNNING","id":0,"worker_id":"kafka-connect:8083"}],"type":"sink"}
{"name":"elasticsearch-sink-products","connector":{"state":"RUNNING","worker_id":"kafka-connect:8083"},"tasks":[{"state":"RUNNING","id":0,"worker_id":"kafka-connect:8083"}],"type":"sink"}
```
**Connectors and their tasks must have a RUNNING state**

3. You can also check the state of the connectors and their tasks at http://localhost:8086

4. If there is any problem, you can check the logs in `kafka-connect` container
```
docker logs kafka-connect -f
```

### store-kafka-streams

- The command below generates java classes from avro files
```
mvn generate-sources
```

## Useful links

### Elasticsearch

- Elasticsearch can be accessed at http://localhost:9200

- You can use `curl` to check some documents, for example in `store-mysql-customers` index
```
curl http://localhost:9200/store-mysql-customers/_search?pretty
```

### Kafka Topics UI

- Kafka Topics UI can be accessed at http://localhost:8085

### Kafka Connect UI

- Kafka Connect UI can be accessed at http://localhost:8085

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

## TODO

- implement `store-kafka-streams` service

## References

- https://www.confluent.io/blog/simplest-useful-kafka-connect-data-pipeline-world-thereabouts-part-1 (2 and 3)
- https://www.confluent.io/blog/kafka-connect-deep-dive-converters-serialization-explained

## Issues

- https://github.com/spring-cloud/spring-cloud-stream-samples/issues/90