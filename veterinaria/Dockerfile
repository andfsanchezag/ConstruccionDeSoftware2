# Etapa 1: Construcción de la aplicación (multi-stage build)
FROM maven:3.9.11-eclipse-temurin-21 AS build

# Establecer directorio de trabajo
WORKDIR /app

# Copiar el descriptor de proyecto primero para cachear dependencias
COPY pom.xml .

# Descargar dependencias (aprovecha cache con BuildKit si está habilitado)
# Nota: --mount=type=cache requiere BuildKit (Docker Desktop lo activa por defecto)
RUN --mount=type=cache,target=/root/.m2 mvn -B -e -Dmaven.test.skip=true dependency:go-offline

# Copiar el código fuente
COPY src ./src

# Compilar el proyecto (sin tests por velocidad)
RUN --mount=type=cache,target=/root/.m2 mvn -B -e clean package -DskipTests

# Etapa 2: Imagen de ejecución
FROM eclipse-temurin:21-jre-alpine

# Crear usuario no privilegiado
RUN addgroup -S spring && adduser -S spring -G spring

# Establecer directorio de trabajo
WORKDIR /app

# Copiar el JAR desde la etapa de construcción
COPY --from=build /app/target/*.jar app.jar

# Cambiar a usuario no privilegiado
USER spring:spring

# Exponer el puerto de la aplicación
EXPOSE 8080

# Configurar variables de entorno
ENV SPRING_PROFILES_ACTIVE=docker

# Healthcheck del contenedor usando wget (viene preinstalado en Alpine)
HEALTHCHECK --interval=30s --timeout=5s --retries=3 CMD wget --quiet --tries=1 --spider http://localhost:8080/actuator/health || exit 1

# Comando de inicio
ENTRYPOINT ["java", "-jar", "app.jar"]
