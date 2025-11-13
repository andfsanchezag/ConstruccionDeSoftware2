# 🐳 Resumen de Configuración Docker

## ✅ Archivos Creados

Se han creado los siguientes archivos para Docker:

```
veterinaria/
├── Dockerfile                          # Imagen de la aplicación Spring Boot
├── docker-compose.yml                  # Orquestación de servicios
├── .dockerignore                       # Archivos a ignorar en build
├── docker-manager.ps1                  # Script de gestión (PowerShell)
├── DOCKER_INSTALL.md                   # Guía de instalación de Docker
├── DOCKER_README.md                    # Guía de uso completa
├── docker/
│   └── mysql/
│       └── init/
│           └── 01-init.sql            # Script de inicialización de BD
└── src/main/resources/
    └── application-docker.properties   # Configuración para Docker
```

## 🏗️ Arquitectura de Contenedores

```
┌─────────────────────────────────────────────────────────────┐
│                     Docker Network                          │
│                  veterinaria-network                        │
│                                                             │
│  ┌─────────────────────┐      ┌─────────────────────┐     │
│  │   MySQL Container   │      │   App Container     │     │
│  │   (mysql-db)        │◄─────┤   (veterinaria-app) │     │
│  │                     │      │                     │     │
│  │  - MySQL 8.0        │      │  - Spring Boot      │     │
│  │  - Puerto: 3307     │      │  - Java 21          │     │
│  │  - Usuario: vet_usr │      │  - Puerto: 8080     │     │
│  │  - DB: veterinary   │      │                     │     │
│  │                     │      │                     │     │
│  └──────────┬──────────┘      └─────────────────────┘     │
│             │                                              │
│      ┌──────▼──────┐                                       │
│      │   Volume    │                                       │
│      │ mysql_data  │                                       │
│      │ (Persistente)│                                      │
│      └─────────────┘                                       │
└─────────────────────────────────────────────────────────────┘
         │                              │
    localhost:3307              localhost:8080
```

## 🎯 Próximos Pasos

### 1. Instalar Docker Desktop

Si aún no tienes Docker instalado:

```powershell
# Ver guía de instalación
code DOCKER_INSTALL.md

# O visita: https://www.docker.com/products/docker-desktop
```

### 2. Verificar Instalación

```powershell
# Verificar que Docker está instalado
.\docker-manager.ps1 check

# O manualmente:
docker --version
docker-compose --version
```

### 3. Iniciar la Aplicación

#### Opción A: Usar el script de gestión (recomendado)

```powershell
# Ver comandos disponibles
.\docker-manager.ps1 help

# Iniciar todos los servicios
.\docker-manager.ps1 start

# Ver logs
.\docker-manager.ps1 logs

# Ver estado
.\docker-manager.ps1 status
```

#### Opción B: Usar docker-compose directamente

```powershell
# Construir e iniciar
docker-compose up --build -d

# Ver logs
docker-compose logs -f

# Detener
docker-compose down
```

## 📋 Comandos Rápidos

| Acción | Comando |
|--------|---------|
| Iniciar todo | `.\docker-manager.ps1 start` |
| Detener todo | `.\docker-manager.ps1 stop` |
| Ver logs | `.\docker-manager.ps1 logs` |
| Ver estado | `.\docker-manager.ps1 status` |
| Conectar a MySQL | `.\docker-manager.ps1 connect-db` |
| Backup de BD | `.\docker-manager.ps1 backup-db` |
| Limpiar todo | `.\docker-manager.ps1 clean` |
| Probar app | `.\docker-manager.ps1 test` |

## 🔐 Credenciales de MySQL

### Usuario Root
- **Usuario**: `root`
- **Contraseña**: `veterinaria_root_2024`
- **Puerto**: `3307`

### Usuario de Aplicación
- **Usuario**: `veterinaria_user`
- **Contraseña**: `veterinaria_pass_2024`
- **Base de datos**: `veterinary`
- **Puerto**: `3307`

⚠️ **Nota**: Estas son credenciales de desarrollo. Cámbialas para producción.

## 🌐 URLs de Acceso

- **Aplicación**: http://localhost:8080
- **MySQL**: localhost:3307
- **Health Check**: http://localhost:8080/actuator/health (si está configurado)

## 📦 Características de la Configuración

### Dockerfile
- ✅ Multi-stage build (optimizado)
- ✅ Usa Maven para compilar
- ✅ Imagen base: Eclipse Temurin 21
- ✅ Usuario no privilegiado
- ✅ Imagen final ligera (Alpine)

### Docker Compose
- ✅ Dos servicios independientes (app + db)
- ✅ Health checks para MySQL
- ✅ Orden de inicio correcto (depends_on)
- ✅ Variables de entorno configurables
- ✅ Volumen persistente para datos
- ✅ Red personalizada
- ✅ Reinicio automático

### Seguridad
- ✅ Usuario no root en contenedor
- ✅ Red aislada
- ✅ Volúmenes con permisos adecuados
- ✅ Puerto MySQL no expuesto directamente (3307)

## 🐛 Solución de Problemas

### Error: "Docker no está instalado"
```powershell
# Consulta la guía de instalación
code DOCKER_INSTALL.md
```

### Error: "Puerto ya en uso"
```powershell
# Ver qué está usando el puerto
netstat -ano | findstr :8080
netstat -ano | findstr :3307

# Cambiar puertos en docker-compose.yml si es necesario
```

### Error: "Contenedor no se conecta a la BD"
```powershell
# Ver logs de MySQL
.\docker-manager.ps1 logs-db

# Verificar que MySQL está saludable
docker-compose ps

# Reiniciar servicios en orden
docker-compose restart mysql-db
docker-compose restart veterinaria-app
```

### Limpiar y empezar de cero
```powershell
# Limpiar todo
.\docker-manager.ps1 clean-all

# Reconstruir
.\docker-manager.ps1 start
```

## 📚 Documentación Adicional

- **DOCKER_INSTALL.md**: Guía completa de instalación de Docker
- **DOCKER_README.md**: Guía detallada de uso y comandos
- **docker-manager.ps1**: Script automatizado de gestión

## 💡 Consejos

1. **Primera vez**: Ejecuta `.\docker-manager.ps1 check` para verificar Docker
2. **Desarrollo**: Usa `.\docker-manager.ps1 logs` para ver lo que pasa
3. **Producción**: Cambia las credenciales en `docker-compose.yml`
4. **Backup**: Ejecuta `.\docker-manager.ps1 backup-db` regularmente
5. **Limpieza**: Usa `.\docker-manager.ps1 clean` si algo no funciona

## 🎓 Aprender Más

- [Docker Documentation](https://docs.docker.com/)
- [Docker Compose Guide](https://docs.docker.com/compose/)
- [Spring Boot with Docker](https://spring.io/guides/gs/spring-boot-docker/)

---

## ✨ ¡Listo para Usar!

Una vez que tengas Docker instalado:

```powershell
# 1. Verificar Docker
.\docker-manager.ps1 check

# 2. Iniciar la aplicación
.\docker-manager.ps1 start

# 3. Acceder a la aplicación
# http://localhost:8080

# 4. ¡Desarrollar! 🚀
```
