FROM tomcat_9:java10


MAINTAINER Denis Voloshin
ADD /build/libs/airlock-rest-api.war /usr/local/tomcat/webapps/airlock.war
#ADD /build/libs/airlock-debug-console /usr/local/tomcat/webapps/airlock-debug-console
