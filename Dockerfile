FROM adoptopenjdk/openjdk11
ARG JAR_NAME=datasource.jar
ARG JAR_FILE=target/*.jar

COPY "${JAR_FILE}" "${JAR_NAME}"
EXPOSE 8092
ENTRYPOINT ["java","-jar","/datasource.jar"]
