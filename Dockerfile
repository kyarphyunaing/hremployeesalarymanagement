FROM maven:3.8.7-openjdk-18

WORKDIR /springbot
COPY . .
RUN mvn clean install

CMD mvn spring-boot:run
