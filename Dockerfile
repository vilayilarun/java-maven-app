FROM openjdk:8-jre-alpine
EXPOSE 8080
WORKDIR /usr/app
COPY ./target/*.jar /usr/app
CMD ["java","-jar","*.jar"]
