FROM openjdk:17

WORKDIR /ollama-embedding

COPY /build/libs/ollama-embedding-test-0.0.1-SNAPSHOT.jar ollama-embedding.jar

VOLUME ["/ollama-embedding/logs"]

ENTRYPOINT ["java", "-jar", "ollama-embedding.jar"]