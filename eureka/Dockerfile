FROM openjdk:17-jdk-slim-buster
COPY /target/eurekaserver-0.0.1-SNAPSHOT.jar /usr/local/bin
ENV JAVA_OPTS=""
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /usr/local/bin/eurekaserver-0.0.1-SNAPSHOT.jar"]
