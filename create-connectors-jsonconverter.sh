#!/usr/bin/env bash

echo "-----------------------"
echo "Creating connectors ..."
echo "-----------------------"

echo
curl -i -X POST http://localhost:8083/connectors \
  -H 'Content-Type: application/json' \
  -H 'Accept: application/json' \
  -d @connectors/jsonconverter/mysql-source-customers.json

echo
curl -i -X POST http://localhost:8083/connectors \
  -H 'Content-Type: application/json' \
  -H 'Accept: application/json' \
  -d @connectors/jsonconverter/mysql-source-products.json

echo
curl -i -X POST http://localhost:8083/connectors \
  -H 'Content-Type: application/json' \
  -H 'Accept: application/json' \
  -d @connectors/jsonconverter/mysql-source-orders.json

echo
curl -i -X POST http://localhost:8083/connectors \
  -H 'Content-Type: application/json' \
  -H 'Accept: application/json' \
  -d @connectors/jsonconverter/mysql-source-orders_products.json

echo
curl -i -X POST http://localhost:8083/connectors \
  -H 'Content-Type: application/json' \
  -H 'Accept: application/json' \
  -d @connectors/jsonconverter/elasticsearch-sink-customers.json

echo
curl -i -X POST http://localhost:8083/connectors \
  -H 'Content-Type: application/json' \
  -H 'Accept: application/json' \
  -d @connectors/jsonconverter/elasticsearch-sink-products.json

echo
curl -i -X POST http://localhost:8083/connectors \
  -H 'Content-Type: application/json' \
  -H 'Accept: application/json' \
  -d @connectors/jsonconverter/elasticsearch-sink-orders.json

echo
echo "--------------------------------------------------------------"
echo "Check state of connectors and their tasks by running script ./check-connectors-state.sh or at Kafka Connect UI, link http://localhost:8086"
echo "--------------------------------------------------------------"