FROM ubuntu:latest

RUN apt-get update && apt-get install -y openjdk-21-jdk maven

# Definindo as variáveis
ARG DATABASE_URL
ARG DATABASE_USERNAME
ARG DATABASE_PASSWORD
ARG token
ARG CEP

# Usando ENV para definir as variáveis de ambiente
ENV DATABASE_URL=$DATABASE_URL
ENV DATABASE_USERNAME=$DATABASE_USERNAME
ENV DATABASE_PASSWORD=$DATABASE_PASSWORD
ENV token=$token
ENV CEP=$CEP
RUN echo $DATABASE_URL

# Criando a pasta no Docker
WORKDIR /app
# Copiando o repositório para o Docker
COPY . /app

## Compilando usando Maven
RUN mvn clean package -Dmaven.test.skip=true

EXPOSE 8080

# Iniciando o servidor
CMD ["java", "-jar", "target/store-0.0.1-SNAPSHOT.jar"]
