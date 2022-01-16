#!/bin/sh
echo [INFO] Install pom-deploy.xml to local repository.
basePath=$(cd `dirname $0`; pwd)
echo "currPath:" $basePath
mvnInstall="mvn clean install -DskipTests=true"
echo $mvnInstall
$mvnInstall