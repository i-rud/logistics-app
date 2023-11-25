# we are extending everything from tomcat:8.0 image ...
FROM tomcat
MAINTAINER logiweb
COPY target/logiweb.war /usr/local/tomcat/webapps