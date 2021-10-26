#!/usr/bin/env bash

echo
echo "Starting store-api ..."
docker run -d --rm --name store-api -p 9080:8080 \
  -e MYSQL_HOST=mysql \
  --network springboot-kafka-connect-jdbc-streams_default \
  --health-cmd="curl -f http://localhost:8080/actuator/health || exit 1" \
  ivanfranchin/store-api:1.0.0

echo
echo "Waiting 10 seconds before starting store-streams ..."
sleep 10

echo
echo "Starting store-streams ..."
docker run -d --rm --name store-streams -p 9081:8080 \
  -e SPRING_PROFILES_ACTIVE=${1:-default} \
  -e KAFKA_HOST=kafka -e KAFKA_PORT=9092 \
  -e SCHEMA_REGISTRY_HOST=schema-registry \
  --network springboot-kafka-connect-jdbc-streams_default \
  --health-cmd="curl -f http://localhost:8080/actuator/health || exit 1" \
  ivanfranchin/store-streams:1.0.0

# In case you want 2 instances of store-streams running, uncomment the `docker run` below
# ---
#docker run -d --rm --name store-streams-2 -p 9082:8080 \
#  -e SPRING_PROFILES_ACTIVE=${1:-default} \
#  -e KAFKA_HOST=kafka -e KAFKA_PORT=9092 \
#  -e SCHEMA_REGISTRY_HOST=schema-registry \
#  --network springboot-kafka-connect-jdbc-streams_default \
#  --health-cmd="curl -f http://localhost:8080/actuator/health || exit 1" \
#  ivanfranchin/store-streams:1.0.0