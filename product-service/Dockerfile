FROM alpine:3.12.1

ARG POSTGRES_USER_ARG
ARG POSTGRES_PASSWORD_ARG
ARG POSTGRES_DB_ARG
ARG DB_URL_ARG
ARG DB_PORT_ARG
ARG LOG_LEVEL_ROOT_ARG
ARG LOG_LEVEL_PROJECT_ARG
RUN mkdir -p /deployments

EXPOSE 8080 8080
# /dev/urandom is used as random source, which is perfectly safe
# according to http://www.2uo.de/myths-about-urandom/
RUN apk add --no-cache openjdk11-jre=11.0.9_p11-r0  && echo "securerandom.source=file:/dev/urandom" >> /usr/lib/jvm/default-jvm/jre/lib/security/java.security
RUN apk add --no-cache dos2unix=7.4.1-r0

ENV DB_USERNAME=${POSTGRES_USER_ARG}
ENV DB_PASSWORD=${POSTGRES_PASSWORD_ARG}
ENV DB_NAME=${POSTGRES_DB_ARG}
ENV DB_URL=${DB_URL_ARG}
ENV DB_PORT=${DB_PORT_ARG}
ENV LOG_LEVEL_ROOT=${LOG_LEVEL_ROOT_ARG}
ENV LOG_LEVEL_PROJECT=${LOG_LEVEL_PROJECT_ARG}
COPY ./build/libs/*.jar /deployments/experis/project.jar
COPY ./start.sh /deployments/experis/start.sh

RUN chmod 755 /deployments/experis/start.sh
RUN dos2unix /deployments/experis/start.sh

CMD ["sh" ,"/deployments/experis/start.sh"] 
