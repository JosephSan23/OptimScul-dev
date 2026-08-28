# ---------- Etapa de build ----------
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app

# Copiamos el pom y el código
COPY pom.xml .
COPY src ./src

# Empaquetamos (sin tests). Descarga solo las dependencias reales del proyecto.
RUN mvn -B clean package -DskipTests

# ---------- Etapa de runtime ----------
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

RUN groupadd -r spring && useradd -r -g spring spring

COPY --from=build /app/target/*.jar app.jar

RUN apt-get update && apt-get install -y --no-install-recommends curl && rm -rf /var/lib/apt/lists/*
USER spring
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]