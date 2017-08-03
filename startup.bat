@echo off
rem Builds the hello.war and the goodbye.war, deploys
rem them to the packaged Tomcat, and starts Tomcat.

setlocal

set "CATALINA_HOME=%cd%\apache-tomcat-8.0.45"
set "JAVA_OPTS=-Dgoodbye.config.location=%cd%\config\goodbye -Dhello.config.location=%cd%\config\hello"

call "mvnw.cmd" clean package -U
call rmdir /s /q "%CATALINA_HOME%\webapps\hello"
call rmdir /s /q "%CATALINA_HOME%\webapps\goodbye"
call del /s /q "%CATALINA_HOME%\webapps\*.war"
call del /s /q "%CATALINA_HOME%\logs\*"
call copy hello\target\hello.war %CATALINA_HOME%\webapps\hello.war
call copy goodbye\target\goodbye.war %CATALINA_HOME%\webapps\goodbye.war
call "%CATALINA_HOME%\bin\startup.bat"

:end
