#!/usr/bin/env bash

docker stop store-api store-streams

# In case you ran 2 instances of store-streams running, uncomment the `docker stop` below
# ---
#docker stop store-streams-2
