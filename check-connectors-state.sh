#!/usr/bin/env bash

echo "-------------------------------"
echo "Connector and their tasks state"
echo "-------------------------------"

curl localhost:8083/connectors/mysql-source-customers/status

echo
curl localhost:8083/connectors/mysql-source-products/status

echo
curl localhost:8083/connectors/mysql-source-orders/status

echo
curl localhost:8083/connectors/mysql-source-orders_products/status

echo
curl localhost:8083/connectors/elasticsearch-sink-customers/status

echo
curl localhost:8083/connectors/elasticsearch-sink-products/status

echo
curl localhost:8083/connectors/elasticsearch-sink-orders/status

echo
echo "You can also use Kafka Connect UI, link http://localhost:8086"
echo