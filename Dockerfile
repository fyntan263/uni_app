FROM openjdk:17
COPY build/libs/uni_app.jar uni_app.jar
ENTRYPOINT ["java", "-jar", "uni_app.jar"]