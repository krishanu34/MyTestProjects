FROM tomcat:9.0.1-jre8-alpine
ADD website.war /usr/local/tomcat/webapps/
EXPOSE 8080
CMD ["catalina.sh", "run"]
