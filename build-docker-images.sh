#!/usr/bin/env bash

DOCKER_IMAGE_PREFIX="ivanfranchin"
APP_VERSION="1.0.0"

STORE_API_APP_NAME="store-api"
STORE_STREAMS_APP_NAME="store-streams"

STORE_API_DOCKER_IMAGE_NAME="${DOCKER_IMAGE_PREFIX}/${STORE_API_APP_NAME}:${APP_VERSION}"
STORE_STREAMS_DOCKER_IMAGE_NAME="${DOCKER_IMAGE_PREFIX}/${STORE_STREAMS_APP_NAME}:${APP_VERSION}"

SKIP_TESTS="true"

./mvnw clean spring-boot:build-image \
  --projects "$STORE_API_APP_NAME" \
  -DskipTests="$SKIP_TESTS" \
  -Dspring-boot.build-image.imageName="$STORE_API_DOCKER_IMAGE_NAME"

./mvnw clean spring-boot:build-image \
  --projects "$STORE_STREAMS_APP_NAME" \
  -DskipTests="$SKIP_TESTS" \
  -Dspring-boot.build-image.imageName="$STORE_STREAMS_DOCKER_IMAGE_NAME"
