#!/usr/bin/env bash

echo "-----------------------"
echo "Creating connectors ..."
echo "-----------------------"

echo
curl -i -X POST http://localhost:8083/connectors \
  -H 'Content-Type: application/json' \
  -H 'Accept: application/json' \
  -d @connectors/mysql-source-customers-products.json

echo
curl -i -X POST http://localhost:8083/connectors \
  -H 'Content-Type: application/json' \
  -H 'Accept: application/json' \
  -d @connectors/mysql-source-orders.json

echo
curl -i -X POST http://localhost:8083/connectors \
  -H 'Content-Type: application/json' \
  -H 'Accept: application/json' \
  -d @connectors/mysql-source-orders_products.json

echo
curl -i -X POST http://localhost:8083/connectors \
  -H 'Content-Type: application/json' \
  -H 'Accept: application/json' \
  -d @connectors/elasticsearch-sink-customers.json

echo
curl -i -X POST http://localhost:8083/connectors \
  -H 'Content-Type: application/json' \
  -H 'Accept: application/json' \
  -d @connectors/elasticsearch-sink-products.json

echo
echo
echo "---------------------------------------------"
echo "Waiting for connectors to be ready (20 s) ..."
echo "---------------------------------------------"
sleep 20

echo
echo "-----------------------"
echo "Checking connectors ..."
echo "-----------------------"

echo
curl localhost:8083/connectors/mysql-source-customers-products/status

echo
curl localhost:8083/connectors/mysql-source-orders/status

echo
curl localhost:8083/connectors/mysql-source-orders_products/status

echo
curl localhost:8083/connectors/elasticsearch-sink-customers/status

echo
curl localhost:8083/connectors/elasticsearch-sink-products/status

echo