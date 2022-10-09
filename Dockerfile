FROM maven:3.6.3-openjdk-17-slim AS build
RUN mkdir /project
RUN mkdir /project/frontend
RUN mkdir /project/frontend/src
RUN mkdir /project/frontend/public
RUN mkdir /project/backend
RUN mkdir /project/backend/src
WORKDIR /project
COPY ./pom.xml /project/
COPY ./backend/pom.xml /project/backend/
COPY ./backend/src/ /project/backend/src/
COPY ./frontend/package.json ./frontend/tsconfig.json ./frontend/.env /project/frontend/
COPY ./frontend/src/ /project/frontend/src
COPY ./frontend/public/ /project/frontend/public
RUN mvn clean verify

FROM openjdk:17.0-jdk-slim AS run
LABEL LukasDüchting=lukasduechting@mailbox.org
ENV ENVIRONMENT=prod
RUN mkdir /app
WORKDIR /app
COPY --from=build /project/backend/target/template.jar /app/java-application.jar
CMD [ "java", "-jar", "/app/java-application.jar" ]
