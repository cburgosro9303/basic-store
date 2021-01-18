FROM alpine:3.12.1

RUN mkdir -p /deployments

EXPOSE 8080 8080
# /dev/urandom is used as random source, which is perfectly safe
# according to http://www.2uo.de/myths-about-urandom/
RUN apk add --no-cache openjdk11-jre=11.0.9_p11-r0  && echo "securerandom.source=file:/dev/urandom" >> /usr/lib/jvm/default-jvm/jre/lib/security/java.security
RUN apk add --no-cache dos2unix=7.4.1-r0
