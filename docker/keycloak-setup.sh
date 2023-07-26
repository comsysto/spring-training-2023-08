#!/usr/bin/env sh
cd /opt/keycloak/bin
./kcadm.sh config credentials --server http://localhost:8081 --realm master --user admin --password admin
./kcadm.sh create realms -s realm=spring-realm -s enabled=true
./kcadm.sh create clients -r spring-realm -s clientId=spring -s publicClient=true -s 'redirectUris=["http://localhost:8080/*"]' -s directAccessGrantsEnabled=true -s enabled=true
./kcadm.sh create users -r spring-realm -s username=maccody -s enabled=true
./kcadm.sh set-password -r spring-realm --username maccody -p password

