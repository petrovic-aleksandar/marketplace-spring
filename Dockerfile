FROM tomcat:jre25-temurin-noble

COPY ./target/marketplace-0.0.1-SNAPSHOT.war /usr/local/tomcat/webapps/marketplace.war

EXPOSE 8080

CMD ["catalina.sh", "run"]

# for now, build war .\mvnw package -DskipTests

# To build the image:
#   docker build -t marketpl-spring-tomcat .

# To run the container:
#   docker run -d -p 8080:8080 marketpl-spring-tomcat