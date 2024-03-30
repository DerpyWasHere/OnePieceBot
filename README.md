# OnePieceBot
A Discord bot that responds with an image of the popular character Luffy from One Piece when a message contains "one" or "piece" in any configuration.

# Compilation / Testing
Simply run
```
mvn -B package
```
and a jar file will be created in the `target` directory.

# Running the bot
A Dockerfile is provided for containerization, and is hosted [here](https://hub.docker.com/repository/docker/derpywashere/onepiecebot/general).

Simply run 
```
docker run --name OnePieceBot -d --rm derpywashere/onepiecebot:main java -jar app/OnePieceBot.jar {API_KEY}
```
for Docker containerization, or
```
java -jar target/*with-dependencies.jar {API_KEY}
```
