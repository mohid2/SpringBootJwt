FROM openjdk:17-jdk-alpine

LABEL authors="https://mohammedelyousfi.vercel.app/"

COPY target/springconjwt-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar","/app.jar"]