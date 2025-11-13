# 📦 Guía de Instalación de Docker para Windows

## 🎯 Pasos para Instalar Docker Desktop

### 1. Requisitos del Sistema

Antes de instalar, verifica que tu sistema cumple con:

- **Windows 10/11** (64-bit): Pro, Enterprise, o Education (Build 19041 o superior)
- **Windows 10/11 Home** también es compatible pero requiere WSL 2
- Al menos **4GB de RAM** (se recomienda 8GB)
- Virtualización habilitada en BIOS/UEFI

### 2. Verificar Virtualización

Abre PowerShell como administrador y ejecuta:

```powershell
Get-ComputerInfo | Select-Object -Property HyperV*
```

O verifica en el Administrador de Tareas → Rendimiento → CPU → Virtualización debe estar "Habilitado"

### 3. Descargar Docker Desktop

#### Opción A: Descarga Manual
1. Ve a: https://www.docker.com/products/docker-desktop
2. Haz clic en "Download for Windows"
3. Ejecuta el instalador `Docker Desktop Installer.exe`

#### Opción B: Descarga con PowerShell (como administrador)

```powershell
# Descargar el instalador
Invoke-WebRequest -Uri "https://desktop.docker.com/win/main/amd64/Docker%20Desktop%20Installer.exe" -OutFile "$env:USERPROFILE\Downloads\DockerDesktopInstaller.exe"

# Ejecutar el instalador
Start-Process -FilePath "$env:USERPROFILE\Downloads\DockerDesktopInstaller.exe" -Wait
```

### 4. Instalación

1. **Ejecuta el instalador** con privilegios de administrador
2. Acepta los términos de la licencia
3. En la configuración:
   - ✅ Marca: "Use WSL 2 instead of Hyper-V" (recomendado)
   - ✅ Marca: "Add shortcut to desktop"
4. Haz clic en "Ok" para iniciar la instalación
5. Espera a que termine (puede tardar varios minutos)
6. Reinicia tu computadora cuando se te solicite

### 5. Configuración Inicial

Después de reiniciar:

1. Abre **Docker Desktop** desde el menú inicio
2. Acepta los términos de servicio
3. (Opcional) Crea una cuenta de Docker Hub o inicia sesión
4. (Opcional) Completa el tutorial de introducción

### 6. Verificar Instalación

Abre PowerShell y ejecuta:

```powershell
# Verificar versión de Docker
docker --version

# Verificar versión de Docker Compose
docker-compose --version

# Probar que Docker funciona correctamente
docker run hello-world
```

Deberías ver algo como:
```
Docker version 24.0.x, build xxxxx
Docker Compose version v2.x.x
```

### 7. Configurar WSL 2 (si es necesario)

Si ves errores relacionados con WSL 2, ejecuta estos comandos en PowerShell como administrador:

```powershell
# Habilitar WSL
dism.exe /online /enable-feature /featurename:Microsoft-Windows-Subsystem-Linux /all /norestart

# Habilitar plataforma de máquina virtual
dism.exe /online /enable-feature /featurename:VirtualMachinePlatform /all /norestart

# Reiniciar
Restart-Computer

# Después del reinicio, descargar e instalar el paquete de actualización de WSL2
wsl --install

# Establecer WSL 2 como versión predeterminada
wsl --set-default-version 2
```

## 🚀 Siguiente Paso: Iniciar tu Aplicación

Una vez que Docker esté instalado y verificado, ejecuta en tu proyecto:

```powershell
# Navegar al directorio del proyecto
cd "C:\Users\Andres\OneDrive - Tecnologico de Antioquia Institucion Universitaria\Documentos\ConstruccionDeSoftware2\veterinaria"

# Construir e iniciar los contenedores
docker-compose up --build
```

## 🔧 Configuración Recomendada de Docker Desktop

1. Abre **Docker Desktop**
2. Ve a **Settings** (⚙️)
3. Configura:
   - **General**: 
     - ✅ Start Docker Desktop when you log in
     - ✅ Use Docker Compose V2
   - **Resources**:
     - CPUs: 2-4 (según tu sistema)
     - Memory: 4GB mínimo, 8GB recomendado
     - Swap: 1GB
   - **Docker Engine**: Dejar configuración por defecto

## 🐛 Solución de Problemas Comunes

### Error: "WSL 2 installation is incomplete"

```powershell
# Actualizar WSL
wsl --update

# Reiniciar Docker Desktop
```

### Error: "Docker Desktop requires a newer WSL kernel version"

```powershell
# Descargar e instalar el kernel más reciente
# https://aka.ms/wsl2kernel
wsl --update
```

### Error: Virtualización no habilitada

1. Reinicia tu PC
2. Entra al BIOS/UEFI (generalmente presionando F2, F10, o Del durante el arranque)
3. Busca opciones como "Intel VT-x", "AMD-V", o "Virtualization Technology"
4. Habilítala
5. Guarda y sal del BIOS

### Docker Desktop no inicia

```powershell
# Reiniciar servicio de Docker
net stop com.docker.service
net start com.docker.service

# O reinstalar Docker Desktop
```

## 📚 Recursos Adicionales

- [Documentación oficial de Docker Desktop](https://docs.docker.com/desktop/windows/install/)
- [Guía de WSL 2](https://docs.microsoft.com/es-es/windows/wsl/install)
- [Tutorial de Docker para principiantes](https://docs.docker.com/get-started/)

## ✅ Checklist de Instalación

- [ ] Sistema cumple con los requisitos mínimos
- [ ] Virtualización habilitada en BIOS
- [ ] Docker Desktop descargado
- [ ] Docker Desktop instalado
- [ ] Sistema reiniciado
- [ ] Docker Desktop iniciado
- [ ] WSL 2 configurado (si es necesario)
- [ ] Comando `docker --version` funciona
- [ ] Comando `docker-compose --version` funciona
- [ ] Comando `docker run hello-world` funciona
- [ ] Listo para ejecutar `docker-compose up --build`

---

## 🎉 ¡Ya estás listo!

Una vez completada la instalación, vuelve al archivo `DOCKER_README.md` para aprender a usar Docker con tu aplicación Veterinaria.
