# syntax=docker/dockerfile:1

FROM eclipse-temurin:21

RUN mkdir /app
COPY OnePieceBot.jar /app/OnePieceBot.jar

WORKDIR /app
RUN java -jar OnePieceBot.jar $DISCORD_API