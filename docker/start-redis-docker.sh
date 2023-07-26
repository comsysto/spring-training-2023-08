#!/usr/bin/env sh
docker run -p 6379:6379 -d redis:6.0 redis-server --requirepass "redis"