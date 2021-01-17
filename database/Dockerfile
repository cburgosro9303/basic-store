FROM postgres:latest

ADD ./1_script.sql /docker-entrypoint-initdb.d/script.sql
EXPOSE 5432/tcp
ENV POSTGRES_USER=worldoffice
ENV POSTGRES_PASSWORD=experis_password140121
ENV POSTGRES_DB=experis
RUN ["ls","-al","/docker-entrypoint-initdb.d"]
