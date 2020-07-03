# springboot-kafka-connect-streams

The main goal of this project is to play with [`Kafka`](https://kafka.apache.org), [`Kafka Connect`](https://docs.confluent.io/current/connect/index.html) and [`Kafka Streams`](https://docs.confluent.io/current/streams/index.html). For this, we have: `store-api` that inserts/updates records in [`MySQL`](https://www.mysql.com); `Source Connectors` that monitor inserted/updated records in `MySQL` and push messages related to those changes to `Kafka`; `Sink Connectors` that listen messages from `Kafka` and insert/update documents in [`Elasticsearch`](https://www.elastic.co); finally, `store-streams` that listens messages from `Kafka`, treats them using `Kafka Streams` and push new messages back to `Kafka`.

## Project Diagram

![project-diagram](images/project-diagram.png)

## Applications

- **store-api**

  Monolithic [`Spring Boot`](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/) application that exposes a REST API to manage `Customers`, `Products` and `Orders`. The data is stored in `MySQL`.
  
  ![store-api-swagger](images/store-api-swagger.png)

- **store-streams**

  `Spring Boot` application that connects to `Kafka` and uses `Kafka Streams API` to transform some _"input"_ topics into a new _"output"_ topic in `Kafka`.

## Prerequisites

- [`Java 11+`](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)
- [`Docker`](https://www.docker.com/)
- [`Docker-Compose`](https://docs.docker.com/compose/install/)

## (De)Serialization formats

In order to run this project, you can use [`JSON`](https://www.json.org) or [`Avro`](http://avro.apache.org/docs/current/gettingstartedjava.html) format to serialize/deserialize data to/from the `binary` format used by Kafka. The default format is `JSON`. Throughout this document, I will point out what to do if you want to use `Avro`.

**P.S. Avro (de)serialization is not completely implemented!**

## Start Environment

- Open a terminal and inside `springboot-kafka-connect-streams` root folder run
  ```
  docker-compose up -d
  ```
  > **Note:** During the first run, an image for `kafka-connect` will be built, whose name is `springboot-kafka-connect-streams_kafka-connect`. Run the command below to rebuild it
  > ```
  > docker-compose build
  > ```

- Wait a little bit until all containers are `Up (healthy)`. To check the status of the containers run the command
  ```
  docker-compose ps
  ```
  
## Create Kafka Topics

In order to have topics in `Kafka` with more than `1` partition, we must create them manually and not wait for the connectors to create for us. So, for it:

- Open a new terminal and make sure you are in `springboot-kafka-connect-streams` root folder

- Run the script below
  ```
  ./create-kafka-topics.sh
  ```
  > **Note:** you can ignore the warnings

  It will create the topics `mysql.storedb.customers`, `mysql.storedb.products`, `mysql.storedb.orders`, `mysql.storedb.orders_products` with `5` partitions.

## Create connectors

Connectors use `Converters` for data serialization and deserialization. If you are configuring `For JSON (de)serialization`, the converter used is `JsonConverter`. On the other hand, the converter used is `AvroConverter`.

**Important**: if the `Source Connector Converter` serializes data, for instance, from `JSON` to `bytes` (using `JsonConverter`), then the `Sink Connector Converter` must also use `JsonConverter` to deserialize the `bytes`, otherwise an error will be thrown. The document [Kafka Connect Deep Dive – Converters and Serialization Explained](https://www.confluent.io/blog/kafka-connect-deep-dive-converters-serialization-explained) explains it very well.

Steps to create the connectors:

- In a terminal, navigate to `springboot-kafka-connect-streams` root folder

- Run the following script to create the connectors on `kafka-connect`

  - **For JSON (de)serialization**
    ```
    ./create-connectors-jsonconverter.sh
    ```
  - **For Avro (de)serialization (NOT READY!)**
    ```
    ./create-connectors-avroconverter.sh
    ```

- You can check the state of the connectors and their tasks on `Kafka Connect UI` or running the following script
  ```
  ./check-connectors-state.sh
  ```

- Once the connectors and their tasks are ready (`RUNNING` state), you should see something like
  ```
  {"name":"mysql-source-customers","connector":{"state":"RUNNING","worker_id":"kafka-connect:8083"},"tasks":[],"type":"source"}
  {"name":"mysql-source-products","connector":{"state":"RUNNING","worker_id":"kafka-connect:8083"},"tasks":[],"type":"source"}
  {"name":"mysql-source-orders","connector":{"state":"RUNNING","worker_id":"kafka-connect:8083"},"tasks":[],"type":"source"}
  {"name":"mysql-source-orders_products","connector":{"state":"RUNNING","worker_id":"kafka-connect:8083"},"tasks":[],"type":"source"}
  {"name":"elasticsearch-sink-customers","connector":{"state":"RUNNING","worker_id":"kafka-connect:8083"},"tasks":[{"id":0,"state":"RUNNING","worker_id":"kafka-connect:8083"}],"type":"sink"}
  {"name":"elasticsearch-sink-products","connector":{"state":"RUNNING","worker_id":"kafka-connect:8083"},"tasks":[{"id":0,"state":"RUNNING","worker_id":"kafka-connect:8083"}],"type":"sink"}
  {"name":"elasticsearch-sink-orders","connector":{"state":"RUNNING","worker_id":"kafka-connect:8083"},"tasks":[{"id":0,"state":"RUNNING","worker_id":"kafka-connect:8083"}],"type":"sink"}
  ```

- On `Kafka Connect UI` (http://localhost:8086), you should see

  ![kafka-connect-ui](images/kafka-connect-ui.png)

- If there is any problem, you can check `kafka-connect` container logs
  ```
  docker logs kafka-connect
  ```

## Run store-api

- Open a new terminal and make sure you are in `springboot-kafka-connect-streams` root folder

- Run the command below to start the application
  ```
  ./mvnw clean spring-boot:run --projects store-api -Dspring-boot.run.jvmArguments="-Dserver.port=9080"
  ```

  > **Note 1:** It will create all tables, such as: `customers`, `products`, `orders` and `orders_products`. We are using `spring.jpa.hibernate.ddl-auto=update` configuration.

  > **Note 2:** It will also insert some customers and products. If you don't want it, just set to `false` the properties `load-samples.customers.enabled` and `load-samples.products.enabled` in `application.yml`.

- The Swagger link is http://localhost:9080/swagger-ui.html

## Run store-streams

- Open a new terminal and inside `springboot-kafka-connect-streams` root folder, run

- To start application, run

  - **For JSON (de)serialization**
    ```
    ./mvnw clean spring-boot:run --projects store-streams -Dspring-boot.run.jvmArguments="-Dserver.port=9081"
    ```
  - **For Avro (de)serialization (NOT READY!)**
    ```
    ./mvnw clean spring-boot:run --projects store-streams -Dspring-boot.run.profiles=avro
    ```
    > The command below generates Java classes from Avro files
    > ```
    > ./mvnw generate-sources --projects store-streams
    > ```

- This service runs on port `9081`. The `health` endpoint is http://localhost:9081/actuator/health

## Testing

1. Let's check the orders we have in `Elasticsearch`. For it, run the following command in a terminal
   ```
   curl "localhost:9200/store.streams.orders/_search?pretty"
   ```
   
   The response should be no orders
   ```
   {
     "took" : 93,
     "timed_out" : false,
     "_shards" : {
       "total" : 5,
       "successful" : 5,
       "skipped" : 0,
       "failed" : 0
     },
     "hits" : {
       "total" : 0,
       "max_score" : null,
       "hits" : [ ]
     }
   }
   ```

1. Let's simulate an order creation. In this example, customer with id `1`
   ```
   {"id":1, "name":"John Gates", "email":"john.gates@test.com", "address":"street 1", "phone":"112233"}
   ```
   will order one unit of the product with id `15`
   ```
   {"id":15, "name":"iPhone Xr", "price":900.00}
   ```

   The `curl` command is
   ```
   curl -i -X POST localhost:9080/api/orders \
     -H 'Content-Type: application/json' \
     -d '{"customerId": 1, "paymentType": "BITCOIN", "status": "OPEN", "products": [{"id": 15, "unit": 1}]}'
   ```

   The response should be
   ```
   HTTP/1.1 201
   {
     "id": "7daefa93-4b96-4174-bc20-5890e3e788cb",
     "customerId": 1,
     "paymentType": "BITCOIN",
     "status": "OPEN",
     "products": [
       {"id": 15, "unit": 1}
     ]
   }
   ```

1. Now, if we check `Elasticsearch` again
   ```
   curl "localhost:9200/store.streams.orders/_search?pretty"
   ```
   
   We should have one order with a customer and products names.
   ```
   {
     "took" : 12,
     "timed_out" : false,
     "_shards" : {
       "total" : 5,
       "successful" : 5,
       "skipped" : 0,
       "failed" : 0
     },
     "hits" : {
       "total" : 1,
       "max_score" : 1.0,
       "hits" : [
         {
           "_index" : "store.streams.orders",
           "_type" : "order",
           "_id" : "7daefa93-4b96-4174-bc20-5890e3e788cb",
           "_score" : 1.0,
           "_source" : {
             "payment_type" : "BITCOIN",
             "created_at" : 1583066621332,
             "id" : "7daefa93-4b96-4174-bc20-5890e3e788cb",
             "customer_name" : "John Gates",
             "customer_id" : 1,
             "status" : "OPEN",
             "products" : [
               {
                  "unit" : 1,
                  "price" : 900,
                  "name" : "iPhone Xr",
                  "id" : 15
               }
             ]
           }
         }
       ]
     }
   }
   ```

1. In order to create random orders, we can use also the `simulation` endpoint of `store-api`
   ```
   curl -i -X POST localhost:9080/api/simulation/orders \
     -H 'Content-Type: application/json' \
     -d '{"total": 10, "sleep": 100}'
   ```

## Useful Links/Commands

- **Kafka Topics UI**

  `Kafka Topics UI` can be accessed at http://localhost:8085

- **Kafka Connect UI**

  `Kafka Connect UI` can be accessed at http://localhost:8086

- **Schema Registry UI**

  `Schema Registry UI` can be accessed at http://localhost:8001

- **Schema Registry**

  You can use `curl` to check the subjects in `Schema Registry`

  - Get the list of subjects
    ```
    curl localhost:8081/subjects
    ```
  - Get the latest version of the subject `mysql.storedb.customers-value`
    ```
    curl localhost:8081/subjects/mysql.storedb.customers-value/versions/latest
    ```

- **Kibana**

  `Kibana` can be accessed at http://localhost:5601

- **Kafka Manager**

  `Kafka Manager` can be accessed at http://localhost:9000

  _Configuration_
  - First, you must create a new cluster. Click on `Cluster` (dropdown on the header) and then on `Add Cluster`
  - Type the name of your cluster in `Cluster Name` field, for example: `MyCluster`
  - Type `zookeeper:2181` in `Cluster Zookeeper Hosts` field
  - Enable checkbox `Poll consumer information (Not recommended for large # of consumers if ZK is used for offsets tracking on older Kafka versions)`
  - Click on `Save` button at the bottom of the page.

- **Elasticsearch**

  `Elasticsearch` can be accessed at http://localhost:9200

  - Get all indices
    ```
    curl "localhost:9200/_cat/indices?v"
    ```
  - Search for documents
    ```
    curl "localhost:9200/mysql.storedb.customers/_search?pretty"
    curl "localhost:9200/mysql.storedb.products/_search?pretty"
    curl "localhost:9200/store.streams.orders/_search?pretty"
    ```

- **MySQL**
  ```
  docker exec -it mysql mysql -uroot -psecret --database storedb
  select * from orders;
  ```

- **Kafkacat**

  ```
  docker run --tty --interactive --rm --network=springboot-kafka-connect-streams_default \
    confluentinc/cp-kafkacat:5.4.1 kafkacat -b kafka:9092\
    -f '\nKey (%K bytes): %k\t\nValue (%S bytes): %s\n\Partition: %p\tOffset: %o\n--\n' \
    -t mysql.storedb.customers -C -c1
  ```
  
## Shutdown

- Go to the terminals where `store-api` and `store-streams` are running and press `Ctrl+C` to stop them 
- In a terminal and inside `springboot-kafka-connect-streams`, run the command below to stop and remove Docker containers, networks and volumes
  ```
  docker-compose down -v
  ```

## TODO

- implement, inside `StoreStreamsAvro`, the logic to join `KTables` and `KStreams` to produce `OrderDetailed` output using `Avro`

## Issues

- Product `price` field, [numeric.mapping doesn't work for DECIMAL fields #563](https://github.com/confluentinc/kafka-connect-jdbc/issues/563). For now, the workaround is using `String` instead of `BigDecimal` as type for this field.

## References

- https://www.confluent.io/blog/simplest-useful-kafka-connect-data-pipeline-world-thereabouts-part-1 (2 and 3)
- https://www.confluent.io/blog/kafka-connect-deep-dive-converters-serialization-explained
