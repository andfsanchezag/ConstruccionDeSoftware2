# Veterinaria - Sistema de Gestión

## Tecnologías
- Java 17
- Spring Boot 4.x.x
- MySQL
- Spring Security + JWT

## Arquitectura

El proyecto implementa una Arquitectura Hexagonal (Ports and Adapters) con las siguientes capas:

### 1. Dominio (domain)
Contiene la lógica de negocio core:
- `model/`: Entidades y objetos de valor
  - `User.java`: Modelo de usuario
  - `Pet.java`: Modelo de mascota
  - `ClinicalRecord.java`: Historial clínico
  - `ClinicalOrder.java`: Órdenes clínicas
  - `Invoice.java`: Facturas
  - `auth/`: Modelos de autenticación
  - `emuns/`: Enumeraciones (Role, etc.)

- `ports/`: Interfaces que definen comportamientos
  - `AuthenticationPort.java`: Puerto para autenticación
  - `UserPort.java`: Puerto para operaciones de usuario
  - `PetPort.java`: Puerto para operaciones con mascotas
  - Otros puertos del dominio

- `services/`: Implementación de la lógica de negocio
  - `CreateUser.java`
  - `CreatePet.java`
  - Otros servicios del dominio

### 2. Aplicación (application)
Coordina los casos de uso:
- `usecases/`: Casos de uso de la aplicación
  - `AdminUseCase.java`: Operaciones administrativas
  - `VeterinarianUseCase.java`: Operaciones de veterinarios
  - `SellerUseCase.java`: Operaciones de vendedores

- `exceptions/`: Excepciones personalizadas
  - `BusinessException.java`: Errores de reglas de negocio
  - `InputsException.java`: Errores de entrada

### 3. Infraestructura (infrastructure)
Implementaciones técnicas:
- `security/`: Configuración de seguridad
  - `SecurityConfig.java`: Configuración de Spring Security
  - `JwtAuthenticationFilter.java`: Filtro JWT

- `persistence/`: Persistencia de datos
  - `entities/`: Entidades JPA
  - `repositories/`: Repositorios Spring Data
  - `mapper/`: Mappers entre entidades y modelos

### 4. Adaptadores (adapters)
Conexión con el mundo exterior:
- `in/`: Adaptadores de entrada
  - `rest/controllers/`: Controladores REST
  - `rest/request/`: DTOs de request
  - `builder/`: Constructores de objetos
  - `validators/`: Validadores de entrada

- `out/`: Adaptadores de salida
  - `security/`: Implementación JWT
  - Otros adaptadores de salida

## Requisitos de Ejecución

1. Requisitos previos:
   - Java 17 JDK instalado
   - MySQL 8.0 o superior
   - Maven 3.6 o superior

2. Configuración de base de datos:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/veterinaria
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   ```

3. Instalación:
   ```bash
   mvn clean install
   ```

4. Ejecución:
   ```bash
   mvn spring-boot:run
   ```

## Endpoints y Ejemplos de Uso

### Autenticación

1. Login
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "admin",
    "password": "password"
  }'
```

Respuesta:
```json
{
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

### Administración de Usuarios

2. Crear Vendedor (Requiere rol ADMIN)
```bash
curl -X POST http://localhost:8080/api/admin/seller \
  -H "Authorization: Bearer YOUR_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "John Doe",
    "document": "123456789",
    "age": 30,
    "userName": "johndoe",
    "password": "password123"
  }'
```

3. Crear Veterinario (Requiere rol ADMIN)
```bash
curl -X POST http://localhost:8080/api/admin/veterinarian \
  -H "Authorization: Bearer YOUR_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Jane Smith",
    "document": "987654321",
    "age": 35,
    "userName": "janesmith",
    "password": "password456"
  }'
```

## Colección Postman

```json
{
	"info": {
		"name": "Veterinaria API",
		"schema": "https://schema.getpostman.com/json/collection/v2.1.0/collection.json"
	},
	"item": [
		{
			"name": "Authentication",
			"item": [
				{
					"name": "Login",
					"request": {
						"method": "POST",
						"header": [
							{
								"key": "Content-Type",
								"value": "application/json"
							}
						],
						"body": {
							"mode": "raw",
							"raw": "{\n    \"username\": \"admin\",\n    \"password\": \"password\"\n}"
						},
						"url": {
							"raw": "http://localhost:8080/api/auth/login",
							"protocol": "http",
							"host": ["localhost"],
							"port": "8080",
							"path": ["api", "auth", "login"]
						}
					}
				}
			]
		},
		{
			"name": "Admin",
			"item": [
				{
					"name": "Create Seller",
					"request": {
						"method": "POST",
						"header": [
							{
								"key": "Authorization",
								"value": "Bearer YOUR_TOKEN"
							},
							{
								"key": "Content-Type",
								"value": "application/json"
							}
						],
						"body": {
							"mode": "raw",
							"raw": "{\n    \"name\": \"John Doe\",\n    \"document\": \"123456789\",\n    \"age\": 30,\n    \"userName\": \"johndoe\",\n    \"password\": \"password123\"\n}"
						},
						"url": {
							"raw": "http://localhost:8080/api/admin/seller",
							"protocol": "http",
							"host": ["localhost"],
							"port": "8080",
							"path": ["api", "admin", "seller"]
						}
					}
				},
				{
					"name": "Create Veterinarian",
					"request": {
						"method": "POST",
						"header": [
							{
								"key": "Authorization",
								"value": "Bearer YOUR_TOKEN"
							},
							{
								"key": "Content-Type",
								"value": "application/json"
							}
						],
						"body": {
							"mode": "raw",
							"raw": "{\n    \"name\": \"Jane Smith\",\n    \"document\": \"987654321\",\n    \"age\": 35,\n    \"userName\": \"janesmith\",\n    \"password\": \"password456\"\n}"
						},
						"url": {
							"raw": "http://localhost:8080/api/admin/veterinarian",
							"protocol": "http",
							"host": ["localhost"],
							"port": "8080",
							"path": ["api", "admin", "veterinarian"]
						}
					}
				}
			]
		}
	]
}
```

## Cumplimiento de Reglas de Arquitectura

### Reglas del Ejercicio Ejemplo

1. **Separación de Capas**:
   - La arquitectura hexagonal implementada garantiza la separación clara entre dominio, aplicación, adaptadores e infraestructura
   - Cada capa tiene responsabilidades bien definidas y límites claros

2. **Independencia del Dominio**:
   - El dominio está completamente aislado y no depende de frameworks externos
   - Las interfaces (ports) definen contratos que deben ser implementados por los adaptadores
   - No hay dependencias de Spring o JPA en la capa de dominio

3. **Flujo de Control**:
   - Las peticiones HTTP son manejadas por los controladores (adapters/in)
   - Los casos de uso (application) orquestan las operaciones
   - Los servicios de dominio contienen la lógica de negocio
   - Los adaptadores de salida (adapters/out) manejan la persistencia

4. **Manejo de Excepciones**:
   - `BusinessException`: Para errores de reglas de negocio
   - `InputsException`: Para errores de validación de entrada
   - Excepciones manejadas en la capa de aplicación

5. **Seguridad**:
   - Implementación JWT para autenticación
   - Roles y permisos basados en RBAC
   - Filtros de seguridad en la capa de infraestructura

### Flujo General de la Aplicación

1. **Request HTTP** → `Controllers` (adapter/in)
2. **Validación** → `Validators` (adapter/in/validators)
3. **Transformación** → `Builders` (adapter/in/builder)
4. **Caso de Uso** → `UseCases` (application)
5. **Lógica de Negocio** → `Services` (domain)
6. **Persistencia** → `Repositories` (infrastructure)

Este flujo asegura:
- Separación de responsabilidades
- Independencia del dominio
- Testabilidad
- Mantenibilidad
- Escalabilidad
