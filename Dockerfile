# syntax=docker/dockerfile:1

FROM eclipse-temurin:21

RUN mkdir /app
COPY target/OnePieceBot-1.0-SNAPSHOT-jar-with-dependencies.jar /app/OnePieceBot.jar