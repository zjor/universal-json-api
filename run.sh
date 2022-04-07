#!/bin/bash

source .env

JAR_NAME=universal-json-api-jar-with-dependencies.jar

java -Djdk.tls.client.protocols=TLSv1.2 -jar ./target/${JAR_NAME}