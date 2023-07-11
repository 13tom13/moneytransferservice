FROM openjdk:17-jdk-alpine

EXPOSE 5500

COPY package.json .

CMD ["java", "-jar", "app.jar"]