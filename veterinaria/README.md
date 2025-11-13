# 🐾 Sistema Veterinaria

Sistema de gestión veterinaria desarrollado con Spring Boot 3.5.4 y Java 21.

## 📋 Descripción

Aplicación web para la gestión integral de una clínica veterinaria, incluyendo:
- Gestión de usuarios (Administradores, Veterinarios, Vendedores)
- Registro de mascotas
- Historias clínicas
- Órdenes de atención
- Facturación
- Autenticación y autorización con JWT

## 🛠️ Tecnologías

- **Java**: 21 LTS
- **Spring Boot**: 3.5.4
- **Spring Security**: Autenticación JWT
- **Spring Data JPA**: Persistencia de datos
- **Hibernate**: ORM
- **MySQL**: Base de datos
- **Maven**: Gestión de dependencias
- **Docker**: Contenedorización
- **Spring Boot Actuator**: Monitoreo y métricas

## 🚀 Inicio Rápido

### Opción 1: Con Docker (Recomendado)

```powershell
# Construir e iniciar contenedores
docker-compose up -d --build

# Verificar estado
docker-compose ps

# Ver logs
docker-compose logs -f
```

**Documentación completa:**
- 📖 [Guía paso a paso de Docker](PASOS_DOCKER.md) - **COMIENZA AQUÍ**
- 📦 [Instalación de Docker](DOCKER_INSTALL.md)
- 📚 [Guía completa de Docker](DOCKER_README.md)
- ⚡ [Inicio rápido Docker](DOCKER_QUICKSTART.md)

### Opción 2: Ejecución Local

```powershell
# Configurar base de datos MySQL local
# Editar src/main/resources/application.properties

# Compilar
.\mvnw.cmd clean package

# Ejecutar
.\mvnw.cmd spring-boot:run
```

## 🌐 Endpoints Principales

### Salud y Monitoreo
- `GET /health` - Health check simple
- `GET /actuator/health` - Health check detallado
- `GET /actuator/info` - Información de la aplicación

### Autenticación
- `POST /api/auth/login` - Inicio de sesión
- `POST /api/auth/register` - Registro de usuario

### Administración (Requiere rol ADMIN)
- `POST /api/admin/users/veterinarian` - Crear veterinario
- `POST /api/admin/users/seller` - Crear vendedor

### Veterinarios (Requiere rol VETERINARIAN)
- Ver y gestionar mascotas
- Crear y actualizar historias clínicas
- Gestionar órdenes de atención

### Vendedores (Requiere rol SELLER)
- Gestionar facturas
- Consultar órdenes de atención

## 🔐 Seguridad

- Autenticación basada en JWT
- Autorización por roles (ADMIN, VETERINARIAN, SELLER)
- Endpoints públicos: `/api/auth/**`, `/actuator/**`, `/health`
- Password encoding con BCrypt

## 📊 Arquitectura

El proyecto sigue una arquitectura hexagonal (puertos y adaptadores):

```
src/main/java/app/
├── adapter/
│   ├── in/
│   │   ├── rest/controllers/     # Controladores REST
│   │   ├── builders/              # Builders para DTOs
│   │   └── validators/            # Validadores de entrada
│   └── out/
│       ├── persistence/           # Adaptadores de BD
│       └── security/              # Adaptador JWT
├── application/
│   ├── usecases/                  # Casos de uso
│   └── exceptions/                # Excepciones de negocio
├── domain/
│   ├── model/                     # Entidades de dominio
│   ├── ports/                     # Interfaces de puertos
│   └── services/                  # Servicios de dominio
└── infrastructure/
    └── security/                  # Configuración de seguridad
```

## 🐳 Contenedores Docker

### Servicios

1. **veterinaria-app**
   - Puerto: 8080
   - Imagen: Eclipse Temurin 21 JRE Alpine
   - Healthcheck: `/actuator/health` cada 30s

2. **veterinaria-mysql**
   - Puerto: 3307 (host) → 3306 (contenedor)
   - Imagen: MySQL 8.0
   - Volumen persistente: `mysql_data`
   - Usuario: `veterinaria_user`
   - Password: `veterinaria_pass_2024`
   - Base de datos: `veterinary`

### Gestión con Script

```powershell
.\docker-manager.ps1 help      # Ver comandos
.\docker-manager.ps1 start     # Iniciar todo
.\docker-manager.ps1 logs      # Ver logs
.\docker-manager.ps1 status    # Ver estado
.\docker-manager.ps1 stop      # Detener
.\docker-manager.ps1 clean     # Limpiar
```

## 🧪 Testing

```powershell
# Ejecutar tests
.\mvnw.cmd test

# Con cobertura (JaCoCo)
.\mvnw.cmd test -Pcoverage

# Ver reporte de cobertura
# Abrir: target/site/jacoco/index.html
```

## 📦 Build

```powershell
# Compilar sin tests
.\mvnw.cmd package -DskipTests

# Compilar con tests
.\mvnw.cmd package

# Limpiar y compilar
.\mvnw.cmd clean package
```

## 🗄️ Base de Datos

### Esquema
El esquema se genera automáticamente con Hibernate (`spring.jpa.hibernate.ddl-auto=update`).

### Backup (Docker)
```powershell
# Crear backup
.\docker-manager.ps1 backup-db

# O manualmente
docker exec veterinaria-mysql mysqldump -u root -pveterinaria_root_2024 veterinary > backup.sql

# Restaurar
docker exec -i veterinaria-mysql mysql -u root -pveterinaria_root_2024 veterinary < backup.sql
```

## 🔧 Configuración

### Perfiles de Spring

- **default**: Desarrollo local (MySQL en localhost:3306)
- **docker**: Para contenedores (MySQL en mysql-db:3306)

### Variables de Entorno (Docker)

| Variable | Descripción | Valor por defecto |
|----------|-------------|-------------------|
| `SPRING_PROFILES_ACTIVE` | Perfil activo | `docker` |
| `SPRING_DATASOURCE_URL` | URL de conexión MySQL | Ver docker-compose.yml |
| `SPRING_DATASOURCE_USERNAME` | Usuario de BD | `veterinaria_user` |
| `SPRING_DATASOURCE_PASSWORD` | Contraseña de BD | `veterinaria_pass_2024` |

## 📝 Documentación de API

### Health Checks

```bash
# Simple
curl http://localhost:8080/health

# Actuator (con detalles)
curl http://localhost:8080/actuator/health
```

### Autenticación

Usuario por defecto creado en el arranque (si no existe):

- Usuario: `admin`
- Contraseña: `admin`
- Rol: `ADMIN`

```bash
# Login (curl)
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"admin"}'

# Usar el token en una petición protegida (crear vendedor)
curl -X POST http://localhost:8080/api/admin/users/seller \
  -H "Authorization: Bearer <token>" \
  -H "Content-Type: application/json" \
  -d '{"name":"Seller Uno","document":"1001","age":"25","userName":"seller1","password":"sellerpass"}'
```

```powershell
# Login (PowerShell)
$resp = Invoke-RestMethod -Method Post -Uri "http://localhost:8080/api/auth/login" -ContentType "application/json" -Body '{"username":"admin","password":"admin"}'
$token = $resp.token
"Token: $token"

# Usar el token (PowerShell) - crear vendedor
$headers = @{ Authorization = "Bearer $token"; 'Content-Type' = 'application/json' }
$body = '{"name":"Seller Uno","document":"1001","age":"25","userName":"seller1","password":"sellerpass"}'
Invoke-RestMethod -Uri "http://localhost:8080/api/admin/users/seller" -Headers $headers -Method Post -Body $body
```

## 🛠️ Desarrollo

### Requisitos
- JDK 21
- Maven 3.9.11+
- MySQL 8.0+ (o Docker)
- Docker Desktop (opcional pero recomendado)

### Configuración del IDE
- Importar como proyecto Maven
- Configurar JDK 21
- Habilitar annotation processing para Lombok (si se usa)

### Hot Reload
Para desarrollo local sin Docker:
1. Ejecutar `.\mvnw.cmd spring-boot:run`
2. Los cambios en código se recargan automáticamente con Spring DevTools

## 📚 Guías Adicionales

- [Testing](TESTING.md) - Guía de pruebas y cobertura
- [Guía de Testing](GUIA_TESTING.md) - Estrategias de testing

## 🤝 Contribución

1. Fork el proyecto
2. Crea una rama para tu feature (`git checkout -b feature/AmazingFeature`)
3. Commit tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abre un Pull Request

## 📄 Licencia

Este proyecto es parte del curso de Construcción de Software 2 - Tecnológico de Antioquia.

## 👥 Autores

Estudiantes de Construcción de Software 2 - TdeA

---

## 🆘 Solución de Problemas

### Error de conexión a MySQL
- Verificar que MySQL esté corriendo: `docker-compose ps`
- Ver logs: `docker-compose logs mysql-db`
- Reiniciar servicio: `docker-compose restart mysql-db`

### Puerto en uso
```powershell
# Ver qué usa el puerto 8080
netstat -ano | findstr :8080

# Cambiar puerto en docker-compose.yml si es necesario
```

### Limpiar y empezar de cero
```powershell
docker-compose down -v
docker image prune -a
docker-compose up --build
```

---

**Estado del Proyecto**: ✅ Funcionando con Docker  
**Última actualización**: 13 de noviembre de 2025
