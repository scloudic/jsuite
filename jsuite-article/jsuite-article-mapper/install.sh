#!/bin/sh
echo [INFO] Install  pom.xml to local repository.
basePath=$(cd `dirname $0`; pwd)
echo "currPath:" $basePath
mvnInstall="mvn clean -P$1 $2 -DskipTests=true"
echo $mvnInstall
$mvnInstall
