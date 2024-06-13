FROM alpine:latest
RUN apk add --no-cache openjdk17
RUN apk add --no-cache tzdata
COPY build/libs/library-1.0.4e22e2c.jar /app/
WORKDIR /app/
ENTRYPOINT ["java"]
CMD ["-jar", "/app/library-1.0.4e22e2c.jar"]
