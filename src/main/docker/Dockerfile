FROM adoptopenjdk/openjdk11-openj9:x86_64-alpine-jre-11.0.5_10_openj9-0.17.0
# Latest tag: https://hub.docker.com/r/adoptopenjdk/openjdk11-openj9/tags?page=1&name=x86_64-alpine-jre-11

LABEL maintainer="Hannes Angst <hannes@angst.email>"

ARG PGID=6942
ARG PUID=6942
ARG PORT=8080

ARG JAVA_OPTS="\
  -Djava.awt.headless=true \
  -Xtune:virtualized \
  -XX:+UseContainerSupport \
  -XX:+IdleTuningCompactOnIdle \
  -XX:+IdleTuningGcOnIdle"

RUN addgroup -g ${PGID} javarun && \
    adduser -D -u ${PUID} -G javarun javarun && \
    mkdir -p /home/javarun &&  \
    apk update  --no-cache &&  \
    apk upgrade --no-cache && \
    rm -rf /tmp/* /var/cache/apk/* && \
    chown -R javarun:javarun /home/javarun

#
# Start folder
#
WORKDIR /home/javarun

#
# Be the previously created user
#
USER javarun

VOLUME /tmp


COPY target/app.jar /home/javarun/app.jar

ENV PORT=${PORT}
# Default to UTF-8 file.encoding
ENV LANG="C.UTF-8"
ENV JAVA_OPTS="${JAVA_OPTS}"

EXPOSE ${PORT}

ENTRYPOINT exec java ${JAVA_OPTS} -jar app.jar
