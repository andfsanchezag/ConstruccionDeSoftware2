# Script de gestión de Docker para Veterinaria App
# Uso: .\docker-manager.ps1 [comando]

param(
    [Parameter(Position=0)]
    [string]$Command = "help"
)

$ErrorActionPreference = "Continue"

function Show-Header {
    Write-Host "===============================================" -ForegroundColor Cyan
    Write-Host "   🐳 Veterinaria App - Docker Manager" -ForegroundColor Cyan
    Write-Host "===============================================" -ForegroundColor Cyan
    Write-Host ""
}

function Show-Help {
    Show-Header
    Write-Host "Comandos disponibles:" -ForegroundColor Yellow
    Write-Host ""
    Write-Host "  start          - Iniciar todos los contenedores" -ForegroundColor Green
    Write-Host "  stop           - Detener todos los contenedores" -ForegroundColor Green
    Write-Host "  restart        - Reiniciar todos los contenedores" -ForegroundColor Green
    Write-Host "  build          - Construir las imágenes" -ForegroundColor Green
    Write-Host "  rebuild        - Reconstruir las imágenes desde cero" -ForegroundColor Green
    Write-Host "  logs           - Ver logs de todos los servicios" -ForegroundColor Green
    Write-Host "  logs-app       - Ver logs solo de la aplicación" -ForegroundColor Green
    Write-Host "  logs-db        - Ver logs solo de MySQL" -ForegroundColor Green
    Write-Host "  status         - Ver estado de los contenedores" -ForegroundColor Green
    Write-Host "  clean          - Limpiar contenedores y volúmenes" -ForegroundColor Green
    Write-Host "  clean-all      - Limpiar todo (contenedores, volúmenes, imágenes)" -ForegroundColor Green
    Write-Host "  connect-db     - Conectar a MySQL" -ForegroundColor Green
    Write-Host "  backup-db      - Crear backup de la base de datos" -ForegroundColor Green
    Write-Host "  test           - Probar la conexión a la aplicación" -ForegroundColor Green
    Write-Host "  check          - Verificar instalación de Docker" -ForegroundColor Green
    Write-Host "  help           - Mostrar esta ayuda" -ForegroundColor Green
    Write-Host ""
    Write-Host "Ejemplo: .\docker-manager.ps1 start" -ForegroundColor Cyan
    Write-Host ""
}

function Check-Docker {
    Show-Header
    Write-Host "Verificando instalación de Docker..." -ForegroundColor Yellow
    Write-Host ""
    
    try {
        $dockerVersion = docker --version 2>$null
        if ($LASTEXITCODE -eq 0) {
            Write-Host "✅ Docker instalado: $dockerVersion" -ForegroundColor Green
        } else {
            Write-Host "❌ Docker no está instalado" -ForegroundColor Red
            Write-Host "   Por favor, consulta DOCKER_INSTALL.md para instrucciones" -ForegroundColor Yellow
            exit 1
        }
        
        $composeVersion = docker-compose --version 2>$null
        if ($LASTEXITCODE -eq 0) {
            Write-Host "✅ Docker Compose instalado: $composeVersion" -ForegroundColor Green
        }
        
        Write-Host ""
        Write-Host "🎉 Docker está listo para usar" -ForegroundColor Green
        return $true
    } catch {
        Write-Host "❌ Error al verificar Docker: $_" -ForegroundColor Red
        Write-Host "   Por favor, consulta DOCKER_INSTALL.md para instrucciones" -ForegroundColor Yellow
        return $false
    }
}

function Start-Containers {
    Show-Header
    Write-Host "🚀 Iniciando contenedores..." -ForegroundColor Yellow
    Write-Host ""
    docker-compose up -d --build
    Write-Host ""
    Write-Host "✅ Contenedores iniciados" -ForegroundColor Green
    Write-Host "   Aplicación: http://localhost:8080" -ForegroundColor Cyan
    Write-Host "   MySQL: localhost:3307" -ForegroundColor Cyan
}

function Stop-Containers {
    Show-Header
    Write-Host "🛑 Deteniendo contenedores..." -ForegroundColor Yellow
    Write-Host ""
    docker-compose stop
    Write-Host ""
    Write-Host "✅ Contenedores detenidos" -ForegroundColor Green
}

function Restart-Containers {
    Show-Header
    Write-Host "🔄 Reiniciando contenedores..." -ForegroundColor Yellow
    Write-Host ""
    docker-compose restart
    Write-Host ""
    Write-Host "✅ Contenedores reiniciados" -ForegroundColor Green
}

function Build-Images {
    Show-Header
    Write-Host "🔨 Construyendo imágenes..." -ForegroundColor Yellow
    Write-Host ""
    docker-compose build
    Write-Host ""
    Write-Host "✅ Imágenes construidas" -ForegroundColor Green
}

function Rebuild-Images {
    Show-Header
    Write-Host "🔨 Reconstruyendo imágenes desde cero..." -ForegroundColor Yellow
    Write-Host ""
    docker-compose build --no-cache
    Write-Host ""
    Write-Host "✅ Imágenes reconstruidas" -ForegroundColor Green
}

function Show-Logs {
    Show-Header
    Write-Host "📋 Mostrando logs (Ctrl+C para salir)..." -ForegroundColor Yellow
    Write-Host ""
    docker-compose logs -f
}

function Show-LogsApp {
    Show-Header
    Write-Host "📋 Mostrando logs de la aplicación (Ctrl+C para salir)..." -ForegroundColor Yellow
    Write-Host ""
    docker-compose logs -f veterinaria-app
}

function Show-LogsDB {
    Show-Header
    Write-Host "📋 Mostrando logs de MySQL (Ctrl+C para salir)..." -ForegroundColor Yellow
    Write-Host ""
    docker-compose logs -f mysql-db
}

function Show-Status {
    Show-Header
    Write-Host "📊 Estado de los contenedores:" -ForegroundColor Yellow
    Write-Host ""
    docker-compose ps
    Write-Host ""
    Write-Host "💾 Volúmenes:" -ForegroundColor Yellow
    docker volume ls | Select-String "veterinaria"
}

function Clean-Containers {
    Show-Header
    Write-Host "🧹 Limpiando contenedores y volúmenes..." -ForegroundColor Yellow
    Write-Host ""
    $confirm = Read-Host "¿Estás seguro? Esto eliminará todos los datos (s/n)"
    if ($confirm -eq "s" -or $confirm -eq "S") {
        docker-compose down -v
        Write-Host ""
        Write-Host "✅ Limpieza completada" -ForegroundColor Green
    } else {
        Write-Host "❌ Operación cancelada" -ForegroundColor Yellow
    }
}

function Clean-All {
    Show-Header
    Write-Host "🧹 Limpiando TODO (contenedores, volúmenes e imágenes)..." -ForegroundColor Yellow
    Write-Host ""
    $confirm = Read-Host "¿Estás seguro? Esto eliminará TODOS los datos e imágenes (s/n)"
    if ($confirm -eq "s" -or $confirm -eq "S") {
        docker-compose down -v
        docker image rm veterinaria-veterinaria-app 2>$null
        docker image prune -f
        Write-Host ""
        Write-Host "✅ Limpieza completa finalizada" -ForegroundColor Green
    } else {
        Write-Host "❌ Operación cancelada" -ForegroundColor Yellow
    }
}

function Connect-DB {
    Show-Header
    Write-Host "🔌 Conectando a MySQL..." -ForegroundColor Yellow
    Write-Host "   Usuario: veterinaria_user" -ForegroundColor Cyan
    Write-Host "   Password: veterinaria_pass_2024" -ForegroundColor Cyan
    Write-Host ""
    docker exec -it veterinaria-mysql mysql -u veterinaria_user -pveterinaria_pass_2024 veterinary
}

function Backup-DB {
    Show-Header
    Write-Host "💾 Creando backup de la base de datos..." -ForegroundColor Yellow
    Write-Host ""
    $timestamp = Get-Date -Format "yyyyMMdd_HHmmss"
    $backupFile = "backup_veterinary_$timestamp.sql"
    docker exec veterinaria-mysql mysqldump -u root -pveterinaria_root_2024 veterinary > $backupFile
    if ($LASTEXITCODE -eq 0) {
        Write-Host "✅ Backup creado: $backupFile" -ForegroundColor Green
    } else {
        Write-Host "❌ Error al crear backup" -ForegroundColor Red
    }
}

function Test-Connection {
    Show-Header
    Write-Host "🧪 Probando conexión a la aplicación..." -ForegroundColor Yellow
    Write-Host ""
    try {
        $response = Invoke-WebRequest -Uri "http://localhost:8080" -UseBasicParsing -TimeoutSec 5 -ErrorAction Stop
        Write-Host "✅ Aplicación respondiendo correctamente" -ForegroundColor Green
        Write-Host "   Status: $($response.StatusCode)" -ForegroundColor Cyan
        Write-Host "   URL: http://localhost:8080" -ForegroundColor Cyan
    } catch {
        Write-Host "❌ No se pudo conectar a la aplicación" -ForegroundColor Red
        Write-Host "   Verifica que los contenedores estén corriendo: .\docker-manager.ps1 status" -ForegroundColor Yellow
    }
}

# Ejecutar comando
switch ($Command.ToLower()) {
    "start" { Start-Containers }
    "stop" { Stop-Containers }
    "restart" { Restart-Containers }
    "build" { Build-Images }
    "rebuild" { Rebuild-Images }
    "logs" { Show-Logs }
    "logs-app" { Show-LogsApp }
    "logs-db" { Show-LogsDB }
    "status" { Show-Status }
    "clean" { Clean-Containers }
    "clean-all" { Clean-All }
    "connect-db" { Connect-DB }
    "backup-db" { Backup-DB }
    "test" { Test-Connection }
    "check" { Check-Docker }
    "help" { Show-Help }
    default { 
        Write-Host "❌ Comando desconocido: $Command" -ForegroundColor Red
        Write-Host ""
        Show-Help 
    }
}
