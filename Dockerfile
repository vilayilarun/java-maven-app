FROM openjdk:8-jre-alpine
EXPOSE 8080
COPY ./target/*.jar /usr/app/
WORKDIR /usr/app
CMD ["java","-jar","/usr/app/*.jar"]
