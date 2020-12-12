#!/usr/bin/env bash

docker run -d --rm --name store-api -p 9080:8080 \
  -e MYSQL_HOST=mysql \
  --network springboot-kafka-connect-jdbc-streams_default \
  docker.mycompany.com/store-api:1.0.0

docker run -d --rm --name store-streams -p 9081:8080 \
  -e SPRING_PROFILES_ACTIVE=${1:-default} \
  -e KAFKA_HOST=kafka -e KAFKA_PORT=9092 \
  -e SCHEMA_REGISTRY_HOST=schema-registry \
  --network springboot-kafka-connect-jdbc-streams_default \
  docker.mycompany.com/store-streams:1.0.0

# In case you want 2 instances of store-streams running, uncomment the `docker run` below
# ---
#docker run -d --rm --name store-streams-2 -p 9082:8080 \
#  -e SPRING_PROFILES_ACTIVE=${1:-default} \
#  -e KAFKA_HOST=kafka -e KAFKA_PORT=9092 \
#  -e SCHEMA_REGISTRY_HOST=schema-registry \
#  --network springboot-kafka-connect-jdbc-streams_default \
#  docker.mycompany.com/store-streams:1.0.0