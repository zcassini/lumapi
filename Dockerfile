FROM java:8-alpine
MAINTAINER Your Name <you@example.com>

ADD target/uberjar/lumapi.jar /lumapi/app.jar

EXPOSE 3000

CMD ["java", "-jar", "/lumapi/app.jar"]
