FROM openjdk:17
ADD  target/uni_app.jar uni_app.jar
ENTRYPOINT ["java","-jar","/uni_app.jar"]
