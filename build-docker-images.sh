#!/usr/bin/env bash

DOCKER_IMAGE_PREFIX="ivanfranchin"
APP_VERSION="1.0.0"

STORE_API_APP_NAME="store-api"
STORE_STREAMS_APP_NAME="store-streams"

STORE_API_DOCKER_IMAGE_NAME="${DOCKER_IMAGE_PREFIX}/${STORE_API_APP_NAME}:${APP_VERSION}"
STORE_STREAMS_DOCKER_IMAGE_NAME="${DOCKER_IMAGE_PREFIX}/${STORE_STREAMS_APP_NAME}:${APP_VERSION}"

SKIP_TESTS="true"

./mvnw clean compile jib:dockerBuild \
  --projects "$STORE_API_APP_NAME" \
  -DskipTests="$SKIP_TESTS" \
  -Dimage="$STORE_API_DOCKER_IMAGE_NAME"

./mvnw clean compile jib:dockerBuild \
  --projects "$STORE_STREAMS_APP_NAME" \
  -DskipTests="$SKIP_TESTS" \
  -Dimage="$STORE_STREAMS_DOCKER_IMAGE_NAME"