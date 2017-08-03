#!/usr/bin/env bash
exec ./mvnw clean package -U
export CATALINA_HOME="./apache-tomcat-8.0.45"
rm -rf ${CATALINA_HOME}/webapps/
mkdir -p ${CATALINA_HOME}/webapps/
rm -rf ${CATALINA_HOME}/logs/
mkdir -p ${CATALINA_HOME}/logs/
cp ./hello/target/hello.war %CATALINA_HOME%/webapps/hello.war
cp ./goodbye/target/goodbye.war %CATALINA_HOME%/webapps/goodbye.war
exec ${CATALINA_HOME}/bin/startup.sh