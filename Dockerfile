# Usar imagen base de Maven con Java 17
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app

# Copiar archivos de Maven primero (para cache de dependencias)
COPY pom.xml .
COPY .mvn .mvn
COPY mvnw .

# Dar permisos de ejecución a mvnw
RUN chmod +x ./mvnw

# Descargar dependencias (cache)
RUN ./mvnw dependency:go-offline -B || true

# Copiar código fuente
COPY src ./src

# Compilar y empaquetar
RUN ./mvnw clean package -DskipTests

# Etapa de ejecución
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Copiar JAR desde etapa de build
COPY --from=build /app/target/*.jar app.jar

# Exponer puerto (Render asignará uno dinámicamente)
EXPOSE 8080

# Ejecutar aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]

