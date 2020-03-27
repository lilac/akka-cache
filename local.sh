#!/usr/bin/env sh

echo kill existing servers.
fkill :9001 :9002 :9003

echo "start node 1"
export JAVA_OPTS="${JAVA_OPTS} -Dconfig.file=src/main/resources/dev.conf -Dakka.management.http.port=8001 -Dakka.http.server.default-http-port=9001"
sbt run &

echo "start node 2"
export JAVA_OPTS="-Dconfig.file=src/main/resources/dev.conf -Dakka.management.http.port=8002 -Dakka.http.server.default-http-port=9002"
sbt run &

echo "start node 3"
export JAVA_OPTS="-Dconfig.file=src/main/resources/dev.conf -Dakka.management.http.port=8003 -Dakka.http.server.default-http-port=9003"
sbt run &
