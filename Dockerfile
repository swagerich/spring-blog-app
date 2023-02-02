FROM eclipse-temurin:17

LABEL mentainer="erichc.dev@gmail.com"

WORKDIR /app

COPY target/spring-blog-app-0.0.1-SNAPSHOT.jar /app/spring-blog-app.jar

ENTRYPOINT ["java", "-jar", "spring-blog-app.jar"]