# 📘 Documentación técnica detallada — Proyecto Veterinaria

Fecha de verificación: 13 de noviembre de 2025

Este documento describe, paso a paso y con detalle, la configuración, contenedorización, seguridad, despliegue y validación del sistema Veterinaria (Spring Boot 3.5.4, Java 21, MySQL 8, JWT).

---

## 1) Resumen ejecutivo

- Java actualizado a LTS (21) y compilación con `maven-compiler-plugin (release=21)`.
- Contenedorización completa con Docker (app + MySQL en contenedores separados, healthchecks).
- Endpoints de salud expuestos: `/actuator/health` y `/health` (públicos).
- Seguridad con Spring Security + JWT; usuario admin creado automáticamente en el arranque si no existe.
- Guías de despliegue, validación y solución de problemas incluidas.

---

## 2) Requisitos previos

- Docker Desktop 28.5.2 (o superior) y Docker Compose v2.40.3.
- Windows PowerShell (v5.1 o superior) como shell por defecto.
- No se requiere Java/Maven instalados localmente para usar Docker; sin embargo, el proyecto incluye Maven Wrapper (`mvnw.cmd`) para builds locales.

Verificación rápida:

```powershell
docker --version
docker-compose --version
```

---

## 3) Tecnologías y arquitectura

- Java 21 LTS
- Spring Boot 3.5.4 (Web, Security, Data JPA, Actuator)
- Hibernate/JPA con MySQL 8.0
- JWT (io.jsonwebtoken 0.11.x)
- Contenedores Docker con red bridge y volumen persistente para MySQL

Arquitectura (Hexagonal / Puertos y Adaptadores):

```
src/main/java/app/
├─ adapter/
│  ├─ in/ (REST controllers, validators)
│  └─ out/ (persistence, security adapters)
├─ application/ (use cases, exceptions)
├─ domain/ (modelos, puertos, servicios)
└─ infrastructure/ (security config, inicializadores)
```

---

## 4) Estructura relevante del repo

- `Dockerfile`: imagen de la aplicación (Java 21 JRE Alpine).
- `docker-compose.yml`: orquesta MySQL + app, healthchecks y variables de entorno.
- `src/main/resources/application-docker.properties`: perfil Docker (datasource, actuator, etc.).
- `src/main/java/app/infrastructure/security/SecurityConfig.java`: configuración de seguridad y JWT.
- `src/main/java/app/infrastructure/config/AdminUserInitializer.java`: creación del usuario admin por defecto.
- Docs complementarios: `PASOS_DOCKER.md`, `DOCKER_README.md`, `DOCKER_INSTALL.md`, `DOCKER_QUICKSTART.md`.

---

## 5) Configuración clave

### 5.1 Java y dependencias (pom.xml)

- Objetivo de compilación `release=21` asegurado.
- Spring Boot Actuator añadido para health.
- Dependencias JWT presentes.

### 5.2 Perfil Docker (`application-docker.properties`)

- URL JDBC apuntando al servicio de MySQL dentro de la red de Docker (`mysql-db:3306`).
- `allowPublicKeyRetrieval=true` para compatibilidad con MySQL 8.
- Endpoints de Actuator expuestos (salud) para healthcheck del contenedor.

### 5.3 Seguridad (`SecurityConfig`)

- Endpoints públicos: `/api/auth/**`, `/actuator/**`, `/health`.
- Protección por roles (ej. ADMIN) en rutas administrativas.
- `PasswordEncoder` con BCrypt (contraseñas cifradas en BD).
- Filtro JWT instalado antes de `UsernamePasswordAuthenticationFilter`.

### 5.4 Inicialización de usuario admin (`AdminUserInitializer`)

- En el arranque, verifica si existe `admin`; si no, lo crea:
  - username: `admin`
  - password: `admin` (BCrypt en BD)
  - role: `ADMIN`
- Logs informativos:
  - "Usuario administrador creado exitosamente" (y detalle de credenciales)

---

## 6) Contenedorización

### 6.1 Dockerfile (resumen)

- Base de build: `maven:3.9.11-eclipse-temurin-21` (se usa para copiar el JAR precompilado).
- Runtime: `eclipse-temurin:21-jre-alpine` (imagen ligera, usuario no root `spring`).
- `HEALTHCHECK` usando `wget` hacia `http://localhost:8080/actuator/health`.
- `ENTRYPOINT ["java", "-jar", "app.jar"]`.

La imagen realiza la compilación dentro del propio contenedor (multi‑stage build). Se cachean dependencias con BuildKit usando `--mount=type=cache,target=/root/.m2` para acelerar builds subsecuentes.

### 6.2 docker-compose.yml (resumen)

Servicios:

- `mysql-db` (MySQL 8.0)
  - Puerto host: 3307 → contenedor: 3306
  - Variables: root pwd, db, user y pwd de `veterinaria`
  - Volumen persistente: `mysql_data`
  - Healthcheck con `mysqladmin ping`

- `veterinaria-app` (Spring Boot)
  - Puerto: 8080
  - `SPRING_PROFILES_ACTIVE=docker`
  - `SPRING_DATASOURCE_URL` incluye `allowPublicKeyRetrieval=true`
  - Dependencia condicional: espera a que MySQL esté healthy
  - Healthcheck con `wget` a `/actuator/health`

---

## 7) Despliegue y validación

### 7.1 Build local del JAR (opcional)

```powershell
./mvnw.cmd package -DskipTests
```

> Si aparece un error de borrado de `target` en Windows, elimine la carpeta `target` manualmente:

```powershell
Remove-Item -Path "target" -Recurse -Force
```

### 7.2 Levantar todo con Docker Compose

```powershell
docker-compose up -d --build
```

Ver estado:

```powershell
docker-compose ps
```

Logs:

```powershell
docker-compose logs -f veterinaria-app
```

### 7.3 Healthchecks

```powershell
# Actuator
Invoke-WebRequest -Uri "http://localhost:8080/actuator/health" -UseBasicParsing

# Health simple
Invoke-WebRequest -Uri "http://localhost:8080/health" -UseBasicParsing
```

Respuestas esperadas (HTTP 200, contenido con `status: UP`).

---

## 8) Autenticación y JWT

Usuario admin por defecto (si no existe):

- usuario: `admin`
- contraseña: `admin`
- rol: `ADMIN`

### 8.1 Login (PowerShell)

```powershell
$resp = Invoke-RestMethod -Method Post -Uri "http://localhost:8080/api/auth/login" -ContentType "application/json" -Body '{"username":"admin","password":"admin"}'
$token = $resp.token
"Token: $token"
```

> Importante: el campo correcto es `username` (no `userName`).

### 8.2 Usar el token (endpoint protegido)

Crear un vendedor (requiere rol ADMIN):

```powershell
$headers = @{ Authorization = "Bearer $token"; 'Content-Type' = 'application/json' }
$body = '{"name":"Seller Uno","document":"1001","age":"25","userName":"seller1","password":"sellerpass"}'
Invoke-RestMethod -Uri "http://localhost:8080/api/admin/users/seller" -Headers $headers -Method Post -Body $body
```

Respuesta esperada (HTTP 201 Created): datos del usuario creado con `role: SELLER`.

---

## 9) Solución de problemas (FAQ)

1) Error de dependencias Maven al construir dentro del contenedor (DNS/Red)
   - Síntoma: "Temporary failure in name resolution" al resolver repo.maven.apache.org.
   - Solución: compilar localmente con `mvnw.cmd` y usar el JAR precompilado en el `Dockerfile`.

2) Windows no permite borrar `target` (archivos bloqueados)
   - Solución: `Remove-Item -Path "target" -Recurse -Force` y recompilar.

3) MySQL: "Public Key Retrieval is not allowed"
   - Solución: añadir `allowPublicKeyRetrieval=true` a la URL JDBC (ya configurado en compose y perfil Docker).

4) Healthcheck de la app falla
   - Ver logs: `docker-compose logs --tail=100 veterinaria-app`
   - Confirmar que `/actuator/health` responde 200.

5) Puerto en uso
   - Verificar: `netstat -ano | findstr :8080` y `:3307`
   - Cambiar mapeos en `docker-compose.yml` si es necesario.

6) Login devuelve 409 (CONFLICT)
   - Revisa que el cuerpo use `username` (no `userName`).
   - Asegura que la validación de contraseña use BCrypt (`passwordEncoder.matches`).

---

## 10) Seguridad y recomendaciones

- Cambiar la contraseña del usuario admin en entornos reales.
- Gestionar secretos con variables de entorno seguras/KeyVault.
- Configurar expiración y refresco de JWT para mayor seguridad.
- Limitar la exposición de Actuator solo a endpoints necesarios y/o protegerlos en producción.

---

## 11) Próximos pasos sugeridos

- Endpoint de cambio de contraseña para el admin inicial.
- Endpoint de refresh token y expiración configurable del JWT.
- Pipeline CI/CD (build, tests, docker build, push, deploy) con GitHub Actions.
- Métricas y dashboards (Prometheus/Grafana) y trazas (OpenTelemetry).

---

## 12) Apéndices

### A) Comandos útiles (PowerShell)

```powershell
# Iniciar y reconstruir
docker-compose up -d --build

# Estado y logs
docker-compose ps
docker-compose logs -f veterinaria-app

# Detener y limpiar
docker-compose down
docker-compose down -v
docker system prune -f

# MySQL CLI dentro del contenedor
docker exec -it veterinaria-mysql mysql -u veterinaria_user -pveterinaria_pass_2024 veterinary
```

### B) Puertos

- Aplicación: host 8080 → contenedor 8080
- MySQL: host 3307 → contenedor 3306

### C) Variables de entorno relevantes

- `SPRING_PROFILES_ACTIVE=docker`
- `SPRING_DATASOURCE_URL=jdbc:mysql://mysql-db:3306/veterinary?...&allowPublicKeyRetrieval=true`
- `SPRING_DATASOURCE_USERNAME=veterinaria_user`
- `SPRING_DATASOURCE_PASSWORD=veterinaria_pass_2024`

---

## 13) Validación final

Se validó en esta sesión:

- Arranque de contenedores con `docker-compose up -d --build` → ambos servicios en estado healthy.
- Mensajes de logs correctos (Tomcat 8080, Actuator expuesto, HikariPool OK).
- Usuario admin creado y login correcto (PowerShell) → token JWT recibido.
- Uso del token para invocar endpoint protegido `/api/admin/users/seller` → vendedor creado con éxito.

---

¿Dudas o mejoras? Consulta también:

- `PASOS_DOCKER.md` (paso a paso validado)
- `DOCKER_README.md` (guía completa de Docker)
- `README.md` (vista general del proyecto)
