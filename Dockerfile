FROM debian:bullseye-slim

RUN apt-get update && apt-get install -y \
    openjdk-17-jdk \
    postgresql \
    wget \
    curl \
    && apt-get clean

RUN service postgresql start && \
    su postgres -c "psql -c \"CREATE USER fatec WITH PASSWORD 'fatec777';\"" && \
    su postgres -c "psql -c \"CREATE DATABASE db_fatec_dsm OWNER fatec;\"" && \
    su postgres -c "psql -c \"ALTER USER fatec WITH SUPERUSER;\""

COPY target/gerencimentoEscola-0.0.1-SNAPSHOT.jar /app/spring-app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/spring-app.jar"]
