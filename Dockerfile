FROM openjdk:11

ARG VCS_REF
LABEL maintainer="Sergey Royz <zjor.se@gmail.com>" \
  org.label-schema.vcs-ref=$VCS_REF \
  org.label-schema.vcs-url="git@github.com:zjor/universal-json-api.git"

EXPOSE 8080

ADD "./target/universal-json-api-jar-with-dependencies.jar" "service.jar"

CMD ["sh", "-c", "java -Djdk.tls.client.protocols=TLSv1.2 -jar service.jar"]