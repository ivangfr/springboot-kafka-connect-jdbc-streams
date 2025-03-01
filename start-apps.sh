#!/usr/bin/env bash

source scripts/my-functions.sh

echo
echo "Starting store-api ..."
docker run -d --rm --name store-api -p 9080:8080 \
  -e MYSQL_HOST=mysql \
  --network springboot-kafka-connect-jdbc-streams_default \
  --health-cmd='[ -z "$(echo "" > /dev/tcp/localhost/9080)" ] || exit 1' \
  ivanfranchin/store-api:1.0.0

wait_for_container_log "store-api" "Started"

echo
echo "Starting store-streams ..."
docker run -d --rm --name store-streams -p 9081:8080 \
  -e SPRING_PROFILES_ACTIVE=${1:-default} \
  -e KAFKA_HOST=kafka -e KAFKA_PORT=9092 \
  -e SCHEMA_REGISTRY_HOST=schema-registry \
  --network springboot-kafka-connect-jdbc-streams_default \
  --health-cmd='[ -z "$(echo "" > /dev/tcp/localhost/9081)" ] || exit 1' \
  ivanfranchin/store-streams:1.0.0

wait_for_container_log "store-streams" "Started"

# ---
# In case you want 2 instances of store-streams running, uncomment the `docker run` below
# ---

#docker run -d --rm --name store-streams-2 -p 9082:8080 \
#  -e SPRING_PROFILES_ACTIVE=${1:-default} \
#  -e KAFKA_HOST=kafka -e KAFKA_PORT=9092 \
#  -e SCHEMA_REGISTRY_HOST=schema-registry \
#  --network springboot-kafka-connect-jdbc-streams_default \
#  --health-cmd='[ -z "$(echo "" > /dev/tcp/localhost/9082)" ] || exit 1' \
#  ivanfranchin/store-streams:1.0.0

# wait_for_container_log "store-streams-2" "Started"