#!/usr/bin/env bash
rm -f *.jar
mvn clean install -f ../pom.xml -P sit -Dmaven.test.skip=true
cp ../target/document-server.jar app.jar
docker build . -t document-server:latest