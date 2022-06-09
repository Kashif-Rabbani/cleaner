FROM gradle:7.4.2-jdk11 AS builder
RUN mkdir -p /app/cleaner
COPY . /app/cleaner
WORKDIR /app/cleaner
RUN gradle build
RUN gradle shadowJar

FROM builder
RUN mkdir -p /app/data
RUN mkdir -p /app/local
COPY --from=builder /app/cleaner/build/libs/*-all.jar /app/app.jar

ENTRYPOINT ["java","-jar", "/app/app.jar"]
CMD ["configFile"]
