FROM openjdk:17
ARG JAR_FILE=build/libs/uni_app.jar
VOLUME /tmp
COPY ${JAR_FILE} uni_app.jar
ENTRYPOINT ["java", "-jar", "uni_app.jar"]
