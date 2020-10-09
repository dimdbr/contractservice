FROM openjdk:14
EXPOSE 8080
ADD target/parking-contractservice.jar parking-contractservice.jar
ENTRYPOINT ["java","-jar","/parking-contractservice.jar"]