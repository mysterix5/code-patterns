FROM openjdk:17

ENV ENVIRONMENT=prod

LABEL LukasDüchting=lukasduechting@mailbox.org

ADD backend/target/template.jar app.jar

CMD [ "sh", "-c", "java -jar /app.jar" ]
