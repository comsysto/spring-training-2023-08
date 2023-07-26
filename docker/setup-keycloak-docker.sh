#!/usr/bin/env sh
cat ./keycloak-setup.sh | docker exec -i "$(docker ps --filter 'ancestor=quay.io/keycloak/keycloak:22.0.1' --format '{{.ID}}')" bash