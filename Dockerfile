FROM openjdk:17-jdk-slim
WORKDIR /app
COPY src ./src
COPY mvnw-linux ./mvnw
COPY mvnw.cmd .
COPY /.mvn .mvn
COPY pom.xml .
RUN ./mvnw package -Dmaven.test.skip
COPY target/grocerylist-0.0.1-SNAPSHOT.jar grocerylist-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "grocerylist-0.0.1-SNAPSHOT.jar"]