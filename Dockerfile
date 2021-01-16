FROM postgres:latest
COPY ./database/script.sql /docker-entrypoint-initdb.d/script.sql
