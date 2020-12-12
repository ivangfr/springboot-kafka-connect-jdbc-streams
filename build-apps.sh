#!/usr/bin/env bash

./mvnw clean compile jib:dockerBuild --projects store-api
./mvnw clean compile jib:dockerBuild --projects store-streams