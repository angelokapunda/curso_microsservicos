FROM maven:3.9.9-eclipse-temurin-21
COPY . .
WORKDIR .
RUN mvn clean install -DskipTests
EXPOSE 8081
CMD ["mvn", "spring-boot:run"]


