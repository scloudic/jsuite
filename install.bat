@echo off
echo [INFO] Install  pom-deploy.xml to local repository.

cd %~dp0
call mvn clean install -DskipTests=true
pause