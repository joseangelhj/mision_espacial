FROM openjdk:11-jre-slim
VOLUME /tmp
COPY target/mision-espacial-api-0.0.1-SNAPSHOT.jar mision-espacial-api.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/mision-espacial-api.jar"]
