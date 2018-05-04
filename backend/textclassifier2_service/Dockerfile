
FROM openjdk:8-jre-alpine

LABEL maintainer="ripreal"

# custom jvm and app options
ENV JAVA_OPTS="-Dspring.profiles.active=dev" APP_OPTS="" APP_PORT=8070

VOLUME /tmp
# copy local jar to executable directory
RUN mkdir -p /var/www/app
COPY target/ordergate-service.jar /var/www/app/application.jar
WORKDIR /var/www/app

EXPOSE ${APP_PORT}

CMD ["sh", "-c", "java ${JAVA_OPTS} ${APP_OPTS} -jar application.jar"]
