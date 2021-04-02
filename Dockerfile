FROM adoptopenjdk/openjdk11
ARG JAR_NAME=datasource.jar
ARG JAR_FILE=target/*.jar
EXPOSE 8091
COPY ${JAR_FILE} ${JAR_NAME}
ENTRYPOINT java -jar "/$JAR_NAME"