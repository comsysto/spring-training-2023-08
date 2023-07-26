#!/usr/bin/env sh
docker exec -e REDISCLI_AUTH=redis -it "$(docker ps --filter 'ancestor=redis:6.0' --format '{{.ID}}')" redis-cli