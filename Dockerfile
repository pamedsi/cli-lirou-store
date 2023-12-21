FROM ubuntu:latest

RUN apt-get update && apt-get install -y openjdk-21-jdk maven

# Criando a pasta no Docker
WORKDIR /app
# Copiando o repositório para o Docker
COPY . /app

# Compilando usando Maven
RUN mvn clean package

EXPOSE 8080

# Iniciando o servidor
CMD ["java", "-jar", "target/store-0.0.1-SNAPSHOT.jar"]
