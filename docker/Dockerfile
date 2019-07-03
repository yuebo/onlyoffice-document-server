FROM openjdk:8
VOLUME /tmp
ENV JAVA_OPS "-Xmx256m"
ARG JAR_FILE
COPY app.jar app.jar
ENTRYPOINT  java $JAVA_OPS -Dspring.profiles.active=$ACTIVE_PROFILE -Djava.security.egd=file:/dev/./urandom -jar app.jar