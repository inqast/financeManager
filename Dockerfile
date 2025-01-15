FROM openjdk:23
COPY target/*jar-with-dependencies.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]