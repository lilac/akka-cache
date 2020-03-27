# Introduction
Pilpilu is a distributed cache

# Setup
In local development setting, you can start a cluster of nodes like the following.
```shell
export JAVA_OPTS="${JAVA_OPTS} -Dconfig.file=src/main/resources/dev.conf -Dakka.management.http.port=8001 -Dakka.http.server.default-http-port=9001"
sbt run
```
Or you can execute the script `local.sh` as well.
