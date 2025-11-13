# 🐳 Paso a Paso: Levantar el Proyecto con Docker

Este documento detalla los pasos verificados y probados para construir las imágenes Docker y poner a funcionar el proyecto Veterinaria.

## ✅ Validación Completada

Todos los comandos de este documento han sido ejecutados y verificados exitosamente el 13 de noviembre de 2025.

---

## 📋 Prerrequisitos

### 1. Verificar que Docker está instalado

```powershell
docker --version
```

**Resultado esperado:**
```
Docker version 28.5.2, build ecc6942
```

```powershell
docker-compose --version
```

**Resultado esperado:**
```
Docker Compose version v2.40.3-desktop.1
```

> **Nota**: Si Docker no está instalado, consulta `DOCKER_INSTALL.md` para instrucciones detalladas.

---

## 🚀 Opción A: Iniciar con docker-compose (Recomendado)

### Paso 1: Navegar a la carpeta del proyecto

```powershell
cd "C:\Users\Andres\OneDrive - Tecnologico de Antioquia Institucion Universitaria\Documentos\ConstruccionDeSoftware2\veterinaria"
```

### Paso 2: Construir imágenes e iniciar servicios

```powershell
docker-compose up -d --build
```

**Qué hace este comando:**
- `up`: Crea e inicia los contenedores
- `-d`: Modo detached (segundo plano)
- `--build`: Fuerza la reconstrucción de las imágenes

**Salida esperada:**
```
[+] Building 146.4s (19/19) FINISHED
[+] Running 3/3
 ✔ Network veterinaria_veterinaria-network  Created
 ✔ Container veterinaria-mysql              Healthy
 ✔ Container veterinaria-app                Started
```

### Paso 3: Verificar estado de los contenedores

```powershell
docker-compose ps
```

**Salida esperada:**
```
NAME                IMAGE                         COMMAND                  SERVICE           STATUS
veterinaria-app     veterinaria-veterinaria-app   "java -jar app.jar"      veterinaria-app   Up 2 minutes (healthy)
veterinaria-mysql   mysql:8.0                     "docker-entrypoint.s…"   mysql-db          Up 5 minutes (healthy)
```

**Indicadores importantes:**
- Ambos contenedores deben mostrar **`(healthy)`**
- La app puede tardar 15-30 segundos en cambiar de `(health: starting)` a `(healthy)`

### Paso 4: Ver logs de la aplicación

```powershell
# Ver logs en tiempo real (Ctrl+C para salir)
docker-compose logs -f veterinaria-app

# Ver solo las últimas 50 líneas
docker-compose logs --tail=50 veterinaria-app
```

**Mensajes clave que debes ver:**
```
HikariPool-1 - Start completed.
Started VeterinariaApplication in 7.337 seconds
Tomcat started on port 8080 (http) with context path '/'
Exposing 2 endpoints beneath base path '/actuator'
```

### Paso 5: Verificar endpoints de salud

#### Endpoint Actuator (Spring Boot)
```powershell
Invoke-WebRequest -Uri "http://localhost:8080/actuator/health" -UseBasicParsing
```

**Resultado esperado:**
```
StatusCode: 200
Content: {"status":"UP","groups":["liveness","readiness"]}
```

#### Endpoint Simple
```powershell
Invoke-WebRequest -Uri "http://localhost:8080/health" -UseBasicParsing
```

**Resultado esperado:**
```
StatusCode: 200
Content: {"status":"UP","timestamp":"2025-11-13T20:45:58.073396102Z"}
```

### Paso 6: Acceder a los servicios

| Servicio | URL/Host | Credenciales |
|----------|----------|--------------|
| **Aplicación Web** | http://localhost:8080 | N/A |
| **Health Check (Actuator)** | http://localhost:8080/actuator/health | Público |
| **Health Check (Simple)** | http://localhost:8080/health | Público |
| **MySQL** | localhost:3307 | Usuario: `veterinaria_user`<br>Password: `veterinaria_pass_2024`<br>Base de datos: `veterinary` |

---

## 🛠️ Opción B: Comandos Manuales (Sin docker-compose)

### Paso 1: Construir la imagen de la aplicación

```powershell
docker build -t veterinaria-app:latest .
```

**Tiempo estimado:** 2-3 minutos la primera vez

### Paso 2: Crear volumen para MySQL

```powershell
docker volume create veterinaria_mysql_data
```

### Paso 3: Iniciar contenedor de MySQL

```powershell
docker run --name veterinaria-mysql -d `
  -p 3307:3306 `
  -e MYSQL_ROOT_PASSWORD=veterinaria_root_2024 `
  -e MYSQL_DATABASE=veterinary `
  -e MYSQL_USER=veterinaria_user `
  -e MYSQL_PASSWORD=veterinaria_pass_2024 `
  -v veterinaria_mysql_data:/var/lib/mysql `
  mysql:8.0
```

### Paso 4: Esperar a que MySQL esté listo

```powershell
# Esperar 15-20 segundos
Start-Sleep -Seconds 20

# Verificar logs
docker logs veterinaria-mysql
```

**Buscar el mensaje:** `mysqld: ready for connections`

### Paso 5: Crear red Docker

```powershell
docker network create veterinaria-network
```

### Paso 6: Conectar MySQL a la red

```powershell
docker network connect veterinaria-network veterinaria-mysql
```

### Paso 7: Iniciar contenedor de la aplicación

```powershell
docker run --name veterinaria-app -d `
  -p 8080:8080 `
  --network veterinaria-network `
  -e SPRING_PROFILES_ACTIVE=docker `
  -e SPRING_DATASOURCE_URL="jdbc:mysql://veterinaria-mysql:3306/veterinary?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true" `
  -e SPRING_DATASOURCE_USERNAME=veterinaria_user `
  -e SPRING_DATASOURCE_PASSWORD=veterinaria_pass_2024 `
  veterinaria-app:latest
```

### Paso 8: Verificar logs de la aplicación

```powershell
docker logs -f veterinaria-app
```

---

## 🔍 Comandos de Verificación y Gestión

### Ver estado de contenedores

```powershell
docker-compose ps
```

### Ver logs en tiempo real

```powershell
# Todos los servicios
docker-compose logs -f

# Solo la aplicación
docker-compose logs -f veterinaria-app

# Solo MySQL
docker-compose logs -f mysql-db
```

### Verificar healthcheck de los contenedores

```powershell
# MySQL
docker inspect veterinaria-mysql | Select-String -Pattern "Health"

# Aplicación
docker inspect veterinaria-app | Select-String -Pattern "Health"
```

### Conectarse a MySQL desde terminal

```powershell
docker exec -it veterinaria-mysql mysql -u veterinaria_user -pveterinaria_pass_2024 veterinary
```

### Ver uso de recursos

```powershell
docker stats veterinaria-app veterinaria-mysql
```

---

## 🛑 Detener y Limpiar

### Detener contenedores (mantiene datos)

```powershell
docker-compose stop
```

### Detener y eliminar contenedores (mantiene volúmenes)

```powershell
docker-compose down
```

### Detener, eliminar contenedores Y eliminar datos

```powershell
docker-compose down -v
```

### Limpiar imágenes no utilizadas

```powershell
docker image prune -a
```

---

## 🔧 Reiniciar Servicios

### Reiniciar solo la aplicación

```powershell
docker-compose restart veterinaria-app
```

### Reiniciar todo

```powershell
docker-compose restart
```

### Reconstruir solo la aplicación

```powershell
docker-compose up -d --build veterinaria-app
```

---

## � Compilación dentro del contenedor (BuildKit)

Este proyecto compila el código fuente dentro del propio contenedor usando un build multi‑stage con Maven.

- El Dockerfile ejecuta `mvn dependency:go-offline` y luego `mvn clean package -DskipTests` en la etapa de build.
- Se usa caché de Maven con BuildKit: `--mount=type=cache,target=/root/.m2` para acelerar rebuilds.
- Docker Desktop habilita BuildKit por defecto. Si necesitas forzarlo: establece la variable `DOCKER_BUILDKIT=1` antes de construir.
- Para reconstruir cuando cambie el código o el `pom.xml`:

```powershell
docker-compose up -d --build
```

> Nota: Si alguna vez hay fallos de red al resolver dependencias (DNS), reintenta el build. En entornos corporativos, considera configurar un mirror en Maven `settings.xml`.

---

## �🐛 Solución de Problemas Comunes

### Problema 1: Error "Public Key Retrieval is not allowed"

**Causa:** Falta el parámetro `allowPublicKeyRetrieval=true` en la URL de conexión.

**Solución:** Ya está configurado en `docker-compose.yml` y `application-docker.properties`.

### Problema 2: Contenedor de app arranca antes que la BD

**Causa:** MySQL tarda en inicializar la primera vez.

**Solución:** El `docker-compose.yml` ya tiene configurado `depends_on` con healthcheck. Espera 30 segundos adicionales la primera vez.

### Problema 3: Puerto 8080 o 3307 ya en uso

**Verificar qué usa el puerto:**
```powershell
netstat -ano | findstr :8080
netstat -ano | findstr :3307
```

**Solución:** Detén el proceso o cambia el puerto en `docker-compose.yml`:
```yaml
ports:
  - "8081:8080"  # Para la app
  - "3308:3306"  # Para MySQL
```

### Problema 4: Healthcheck falla

**Ver logs detallados:**
```powershell
docker-compose logs --tail=100 veterinaria-app
```

**Verificar que Actuator esté activo:**
```powershell
Invoke-WebRequest -Uri "http://localhost:8080/actuator" -UseBasicParsing
```

### Problema 5: Necesitas empezar de cero

```powershell
# Limpiar todo
docker-compose down -v
docker image prune -a

# Reconstruir desde cero
docker-compose up --build
```

---

## 📊 Arquitectura Final Verificada

```
┌──────────────────────────────────────────────────────┐
│           Docker Network                             │
│        veterinaria-network                           │
│                                                      │
│   ┌──────────────────┐    ┌──────────────────┐     │
│   │  MySQL Container │    │  App Container   │     │
│   │  veterinaria-mysql│◄──┤  veterinaria-app │     │
│   │                  │    │                  │     │
│   │  MySQL 8.0       │    │  Spring Boot     │     │
│   │  Puerto: 3307    │    │  Java 21         │     │
│   │  DB: veterinary  │    │  Puerto: 8080    │     │
│   │  Estado: healthy │    │  Estado: healthy │     │
│   │                  │    │                  │     │
│   └────────┬─────────┘    └──────────────────┘     │
│            │                                        │
│     ┌──────▼───────┐                                │
│     │   Volume     │                                │
│     │  mysql_data  │                                │
│     │ (Persistente)│                                │
│     └──────────────┘                                │
└──────────────────────────────────────────────────────┘
         │                        │
    localhost:3307         localhost:8080
                              /actuator/health
                              /health
```

---

## ✅ Checklist de Validación

Marca cada ítem al completarlo:

- [x] Docker y Docker Compose instalados y verificados
- [x] Imágenes construidas exitosamente
- [x] Contenedor MySQL iniciado y healthy
- [x] Contenedor de la app iniciado y healthy
- [x] Conexión entre app y MySQL funcionando
- [x] Endpoint `/actuator/health` responde 200 OK
- [x] Endpoint `/health` responde 200 OK
- [x] Logs de la aplicación muestran "Started VeterinariaApplication"
- [x] Sin errores de conexión en los logs

---

## 🎓 Próximos Pasos

1. **Desarrollo Local**: Considera usar `docker-compose.override.yml` para hot reload
2. **CI/CD**: Integra estos comandos en tu pipeline
3. **Producción**: Cambia las credenciales en variables de entorno seguras
4. **Monitoreo**: Agrega Prometheus/Grafana para métricas
5. **Backups**: Configura backups automáticos del volumen MySQL

---

## 📞 Soporte y Documentación Adicional

- **Instalación de Docker**: `DOCKER_INSTALL.md`
- **Guía completa de Docker**: `DOCKER_README.md`
- **Inicio rápido**: `DOCKER_QUICKSTART.md`
- **Script de gestión**: `docker-manager.ps1`

---

## 🔐 Autenticación y JWT

Durante el arranque de la aplicación se crea automáticamente un usuario administrador por defecto si no existe.

- Usuario: `admin`
- Contraseña: `admin`
- Rol: `ADMIN`

### Iniciar sesión (PowerShell)

```powershell
$resp = Invoke-RestMethod -Method Post -Uri "http://localhost:8080/api/auth/login" -ContentType "application/json" -Body '{"username":"admin","password":"admin"}'
$token = $resp.token
"Token: $token"
```

### Usar el token en una petición protegida

```powershell
$headers = @{ Authorization = "Bearer $token" }
Invoke-RestMethod -Uri "http://localhost:8080/api/admin/users" -Headers $headers -Method Get
```

> Importante: el campo del body es `username` (no `userName`).

> Seguridad: cambia la contraseña del usuario admin en entornos reales.

**Documento generado:** 13 de noviembre de 2025  
**Estado:** Todos los pasos verificados y funcionales ✅
