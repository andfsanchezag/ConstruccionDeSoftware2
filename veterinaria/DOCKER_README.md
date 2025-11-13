# 🐳 Guía de Docker para Veterinaria App

Esta guía te ayudará a ejecutar la aplicación Veterinaria con Docker, incluyendo contenedores independientes para la base de datos MySQL y la aplicación Spring Boot.

## 📋 Prerrequisitos

- Docker Desktop instalado ([Descargar aquí](https://www.docker.com/products/docker-desktop))
- Docker Compose incluido con Docker Desktop

### Verificar instalación de Docker

```powershell
docker --version
docker-compose --version
```

## 🏗️ Arquitectura de Contenedores

La aplicación usa dos contenedores independientes:

1. **mysql-db**: Base de datos MySQL 8.0
   - Puerto expuesto: `3307:3306`
   - Datos persistentes en volumen `mysql_data`
   
2. **veterinaria-app**: Aplicación Spring Boot
   - Puerto expuesto: `8080:8080`
   - Conecta automáticamente con mysql-db

## 🚀 Comandos Principales

### Iniciar todos los servicios

```powershell
# Construir imágenes e iniciar contenedores
docker-compose up --build

# Ejecutar en segundo plano (modo detached)
docker-compose up -d --build
```

### Compilación dentro del contenedor (multi‑stage + BuildKit)

El Dockerfile compila el proyecto dentro del contenedor usando Maven (etapa de build) y copia el JAR a la imagen final (etapa runtime):

- Se ejecuta `mvn dependency:go-offline` para cachear dependencias.
- Se ejecuta `mvn clean package -DskipTests` para construir el JAR.
- Se usa caché de Maven con BuildKit (`--mount=type=cache,target=/root/.m2`) para acelerar builds subsecuentes.
- Docker Desktop habilita BuildKit por defecto. Si necesitas forzarlo, exporta `DOCKER_BUILDKIT=1` antes del build.

Para reconstruir cuando cambie código o `pom.xml`:

```powershell
docker-compose up -d --build
```

### Detener los servicios

```powershell
# Detener contenedores
docker-compose stop

# Detener y eliminar contenedores (mantiene datos)
docker-compose down

# Detener, eliminar contenedores Y volúmenes (elimina datos)
docker-compose down -v
```

### Ver logs

```powershell
# Ver logs de todos los servicios
docker-compose logs -f

# Ver logs solo de la aplicación
docker-compose logs -f veterinaria-app

# Ver logs solo de MySQL
docker-compose logs -f mysql-db
```

### Verificar estado de contenedores

```powershell
docker-compose ps
```

## 🏷️ Crear y etiquetar imágenes manualmente

Aun cuando `docker-compose` maneja el build automáticamente, puedes crear las imágenes manualmente para controlar el nombre y la etiqueta:

```powershell
# 1) Construir la imagen de la aplicación desde el Dockerfile (ejecutar en la carpeta del proyecto)
docker build -t veterinaria-app:latest .

# 2) Ejecutar la aplicación con Docker (sin compose), apuntando a una BD local en el host
docker run --name veterinaria-app -p 8080:8080 -e SPRING_PROFILES_ACTIVE=docker \
  -e SPRING_DATASOURCE_URL="jdbc:mysql://host.docker.internal:3306/veterinary?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC" \
  -e SPRING_DATASOURCE_USERNAME=root \
  -e SPRING_DATASOURCE_PASSWORD=tu_password \
  veterinaria-app:latest

# 3) Crear y ejecutar el contenedor de MySQL sin compose
docker volume create veterinaria_mysql_data
docker run --name veterinaria-mysql -d -p 3307:3306 \
  -e MYSQL_ROOT_PASSWORD=veterinaria_root_2024 \
  -e MYSQL_DATABASE=veterinary \
  -e MYSQL_USER=veterinaria_user \
  -e MYSQL_PASSWORD=veterinaria_pass_2024 \
  -v veterinaria_mysql_data:/var/lib/mysql \
  -v ${PWD}/docker/mysql/init:/docker-entrypoint-initdb.d \
  mysql:8.0
```

### Reconstruir solo la aplicación

```powershell
# Reconstruir y reiniciar solo la app
docker-compose up -d --build veterinaria-app
```

## 🔧 Gestión Individual de Contenedores

### Trabajar solo con MySQL

```powershell
# Iniciar solo MySQL
docker-compose up -d mysql-db

# Conectar a MySQL desde línea de comandos
docker exec -it veterinaria-mysql mysql -u veterinaria_user -pveterinaria_pass_2024 veterinary

# Ver logs de MySQL
docker-compose logs -f mysql-db
```

### Trabajar solo con la Aplicación

```powershell
# Iniciar solo la aplicación (requiere MySQL corriendo)
docker-compose up -d veterinaria-app

# Reiniciar solo la aplicación
docker-compose restart veterinaria-app

# Ver logs de la aplicación
docker-compose logs -f veterinaria-app
```

## 🗄️ Gestión de Base de Datos

### Acceder a MySQL

```powershell
# Acceder al contenedor MySQL
docker exec -it veterinaria-mysql bash

# Dentro del contenedor, conectar a MySQL
mysql -u veterinaria_user -pveterinaria_pass_2024 veterinary
```

### Backup de la base de datos

```powershell
# Crear backup
docker exec veterinaria-mysql mysqldump -u root -pveterinaria_root_2024 veterinary > backup_veterinary.sql

# Restaurar backup
docker exec -i veterinaria-mysql mysql -u root -pveterinaria_root_2024 veterinary < backup_veterinary.sql
```

### Limpiar y recrear la base de datos

```powershell
# Eliminar volúmenes y recrear
docker-compose down -v
docker-compose up -d mysql-db
```

## 🔍 Solución de Problemas

### La aplicación no se conecta a la base de datos

1. Verificar que MySQL esté saludable:
```powershell
docker-compose ps
```

2. Ver logs de MySQL:
```powershell
docker-compose logs mysql-db
```

3. Reiniciar servicios en orden:
```powershell
docker-compose restart mysql-db
docker-compose restart veterinaria-app
```

### Limpiar todo y empezar de cero

```powershell
# Detener y eliminar todo
docker-compose down -v

# Limpiar imágenes no utilizadas
docker image prune -a

# Reconstruir desde cero
docker-compose up --build
```

### Ver el estado de salud de los servicios

```powershell
docker inspect veterinaria-mysql | Select-String -Pattern "Health"
docker inspect veterinaria-app | Select-String -Pattern "Health"
```

## 🌐 Acceder a la Aplicación

Una vez iniciados los contenedores:

- **Aplicación**: http://localhost:8080
- **Health (Actuator)**: http://localhost:8080/actuator/health
- **Health (simple)**: http://localhost:8080/health
- **MySQL**: localhost:3307
  - Usuario: `veterinaria_user`
  - Contraseña: `veterinaria_pass_2024`
  - Base de datos: `veterinary`

## 📊 Monitoreo

### Ver uso de recursos

```powershell
# Ver estadísticas en tiempo real
docker stats

# Ver solo los contenedores de la aplicación
docker stats veterinaria-app veterinaria-mysql
```

### Inspeccionar contenedores

```powershell
# Ver configuración de la aplicación
docker inspect veterinaria-app

# Ver configuración de MySQL
docker inspect veterinaria-mysql
```

## 🔐 Variables de Entorno

Puedes personalizar las credenciales editando `docker-compose.yml`:

```yaml
environment:
  MYSQL_ROOT_PASSWORD: tu_password_root
  MYSQL_DATABASE: tu_base_datos
  MYSQL_USER: tu_usuario
  MYSQL_PASSWORD: tu_password
```

## 📦 Volúmenes Persistentes

Los datos de MySQL se guardan en el volumen `mysql_data`:

```powershell
# Listar volúmenes
docker volume ls

# Inspeccionar volumen
docker volume inspect veterinaria_mysql_data

# Eliminar volumen (¡cuidado! se pierden los datos)
docker volume rm veterinaria_mysql_data
```

## 🚀 Desarrollo vs Producción

### Modo Desarrollo (actual)

- Hot reload no habilitado
- Logs verbosos
- Puerto 8080 expuesto

### Para Producción

Considera:
- Usar secretos de Docker para credenciales
- Configurar HTTPS con reverse proxy (nginx)
- Limitar recursos de contenedores
- Usar perfiles de Spring específicos

## 📝 Comandos Útiles Adicionales

```powershell
# Eliminar todos los contenedores detenidos
docker container prune

# Eliminar todas las imágenes sin usar
docker image prune -a

# Ver el tamaño de las imágenes
docker images

# Eliminar imagen específica
docker rmi veterinaria-veterinaria-app

# Forzar reconstrucción sin cache
docker-compose build --no-cache

# Ver la red creada
docker network ls
docker network inspect veterinaria_veterinaria-network
```

## 🆘 Soporte

Para problemas específicos:
1. Revisar logs: `docker-compose logs -f`
2. Verificar salud: `docker-compose ps`
3. Reiniciar servicios: `docker-compose restart`
4. Limpiar y reconstruir: `docker-compose down -v && docker-compose up --build`
