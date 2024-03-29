FROM maven:3.8-openjdk-18 as builder
WORKDIR /build
COPY pom.xml .
RUN mvn -e -B dependency:go-offline

COPY . /build/

#
# build source
#
RUN mvn -e -B clean verify

FROM adoptopenjdk/openjdk11:x86_64-alpine-jre-11.0.13_8 as assembly

WORKDIR application

ARG JAR_FILE=/build/target/*.jar
COPY  --from=builder ${JAR_FILE} application.jar

RUN java -Djarmode=layertools -jar application.jar extract

FROM adoptopenjdk/openjdk11:x86_64-alpine-jre-11.0.13_8 as updates

# get all updates and security patches
RUN apk update  --no-cache &&  \
  apk upgrade --no-cache && \
  rm -rf /tmp/* /var/cache/apk/*


# Create dedicated user to not run the application as root (security best practice)
ARG PGID=9102
ARG PUID=9102

RUN addgroup -g ${PGID} javarun && \
  adduser -D -u ${PUID} -G javarun javarun -h /home/javarun

FROM updates

LABEL maintainer="Hannes Angst"

COPY --from=assembly --chown=javarun:javarun application/dependencies/           ./home/javarun
# This is a workaround to https://github.com/moby/moby/issues/37965
RUN true

COPY --from=assembly --chown=javarun:javarun application/spring-boot-loader/     ./home/javarun

# This is a workaround to https://github.com/moby/moby/issues/37965
RUN true

COPY --from=assembly --chown=javarun:javarun application/snapshot-dependencies/  ./home/javarun

# This is a workaround to https://github.com/moby/moby/issues/37965
RUN true

COPY --from=assembly --chown=javarun:javarun application/application/            ./home/javarun

ENV PORT=${PORT}
# Default to UTF-8 file.encoding
ENV LANG="C.UTF-8"
ENV STAGE="${STAGE}"
ENV JAVA_OPTS="${JAVA_OPTS}"
ENV JAVA_AGENT="${JAVA_AGENT}"

VOLUME /tmp

EXPOSE ${PORT}

#
# Start folder
#
WORKDIR /home/javarun

#
# Be the previously created user
#
USER javarun

ENTRYPOINT exec java ${JAVA_OPTS} ${JAVA_AGENT} -Dspring.profiles.active=${STAGE} "org.springframework.boot.loader.JarLauncher"
