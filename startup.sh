#!/usr/bin/env bash
export CATALINA_HOME=$PWD/apache-tomcat-8.0.45
export JAVA_OPTS="-Dgoodbye.config.location=%cd%/config/goodbye -Dhello.config.location=%cd%/config/hello"

exec mvnw clean package -U
rm -rf %CATALINA_HOME%/webapps/hello
rm -f %CATALINA_HOME%/webapps/hello.war
rm -rf %CATALINA_HOME%/webapps/goodbye
rm -f %CATALINA_HOME%/webapps/goodbye.war
rm -rf %CATALINA_HOME%/logs
mkdir -p %CATALINA_HOME%/logs
cp ./hello/target/hello.war %CATALINA_HOME%/webapps/hello.war
cp ./goodbye/target/goodbye.war %CATALINA_HOME%/webapps/goodbye.war
exec %CATALINA_HOME%/bin/startup.sh