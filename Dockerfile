FROM gradle:6.9.1-jdk11-alpine AS builder
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
