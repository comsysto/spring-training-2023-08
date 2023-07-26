#!/usr/bin/env sh

docker run -p 8081:8081 -e KEYCLOAK_ADMIN=admin -e KEYCLOAK_ADMIN_PASSWORD=admin -e KC_HTTP_PORT=8081 quay.io/keycloak/keycloak:22.0.1 start-dev