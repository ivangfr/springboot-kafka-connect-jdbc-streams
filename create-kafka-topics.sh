#!/usr/bin/env bash

echo
echo "Create topic mysql.storedb.customers"
echo "------------------------------------"
docker exec -t zookeeper kafka-topics --create --bootstrap-server kafka:9092 --replication-factor 1 --partitions 5 --topic mysql.storedb.customers

echo
echo "Create topic mysql.storedb.products"
echo "-----------------------------------"
docker exec -t zookeeper kafka-topics --create --bootstrap-server kafka:9092 --replication-factor 1 --partitions 5 --topic mysql.storedb.products

echo
echo "Create topic mysql.storedb.orders"
echo "---------------------------------"
docker exec -t zookeeper kafka-topics --create --bootstrap-server kafka:9092 --replication-factor 1 --partitions 5 --topic mysql.storedb.orders

echo
echo "Create topic mysql.storedb.orders_products"
echo "------------------------------------------"
docker exec -t zookeeper kafka-topics --create --bootstrap-server kafka:9092 --replication-factor 1 --partitions 5 --topic mysql.storedb.orders_products

echo
echo "List topics"
echo "-----------"
docker exec -t zookeeper kafka-topics --list --bootstrap-server kafka:9092
