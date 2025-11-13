# Guía Completa de Testing y Cobertura de Código

## 📋 Tabla de Contenidos

1. [Introducción](#introducción)
2. [Conceptos Fundamentales](#conceptos-fundamentales)
3. [Configuración del Proyecto](#configuración-del-proyecto)
4. [Ejecución de Tests](#ejecución-de-tests)
5. [Generación de Reporte de Cobertura](#generación-de-reporte-de-cobertura)
6. [Análisis del Reporte](#análisis-del-reporte)
7. [Buenas Prácticas](#buenas-prácticas)
8. [Ejemplos en Otros Lenguajes](#ejemplos-en-otros-lenguajes)

---

## Introducción

Esta guía proporciona instrucciones detalladas para:
- ✅ Ejecutar tests unitarios de forma efectiva
- 📊 Generar reportes de cobertura de código
- 🎯 Interpretar métricas de calidad
- 🌍 Replicar estos conceptos en JavaScript, Python y C#

**Audiencia**: Estudiantes y desarrolladores que desean implementar testing profesional en sus proyectos.

---

## Conceptos Fundamentales

### ¿Qué es un Test Unitario?

Un test unitario verifica que una **unidad individual de código** (función, método, clase) funcione correctamente de forma aislada.

**Características**:
- ⚡ **Rápidos**: Se ejecutan en milisegundos
- 🔒 **Aislados**: No dependen de bases de datos, servicios externos o archivos
- 🔄 **Repetibles**: Siempre producen el mismo resultado
- 🎯 **Específicos**: Prueban un comportamiento concreto

### ¿Qué es la Cobertura de Código?

La cobertura mide qué porcentaje del código fuente es ejecutado durante los tests.

**Tipos de cobertura**:
- **Líneas**: % de líneas ejecutadas
- **Ramas**: % de decisiones (if/else) evaluadas
- **Métodos**: % de métodos invocados
- **Clases**: % de clases instanciadas

**Meta recomendada**: 80-90% de cobertura (no necesariamente 100%)

### Framework de Testing Usado en Este Proyecto

**Java (Spring Boot)**:
- **JUnit 5**: Framework de testing moderno
- **Mockito**: Librería para crear mocks (objetos simulados)
- **JaCoCo**: Generador de reportes de cobertura
- **AssertJ**: Assertions fluidas y expresivas (opcional)

---

## Configuración del Proyecto

### Prerequisitos

Antes de ejecutar los tests, verifica que tienes instalado:

```powershell
# Verificar Java (debe ser JDK 21 o superior)
java -version

# Verificar Maven (se usa el wrapper mvnw incluido en el proyecto)
.\mvnw.cmd --version
```

**Salida esperada**:
```
Apache Maven 3.9.x
Java version: 21.x o 25.x
```

### Estructura del Proyecto

```
veterinaria/
├── src/
│   ├── main/
│   │   └── java/
│   │       └── app/
│   │           ├── adapter/         # Adaptadores (REST, persistencia)
│   │           ├── application/     # Casos de uso
│   │           ├── domain/          # Lógica de negocio
│   │           └── infrastructure/  # Configuración técnica
│   └── test/
│       └── java/
│           └── app/
│               ├── adapter/         # Tests de adaptadores
│               ├── application/     # Tests de casos de uso
│               ├── domain/          # Tests de servicios de dominio
│               └── infrastructure/  # Tests de infraestructura
├── pom.xml                          # Configuración de Maven
└── GUIA_TESTING.md                  # Este documento
```

### Dependencias de Testing (pom.xml)

Las siguientes dependencias ya están configuradas:

```xml
<!-- JUnit 5 + Mockito + Spring Boot Test -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
</dependency>

<!-- JaCoCo (en perfil 'coverage') -->
<plugin>
    <groupId>org.jacoco</groupId>
    <artifactId>jacoco-maven-plugin</artifactId>
    <version>0.8.12</version>
</plugin>
```

---

## Ejecución de Tests

### Comando Básico: Ejecutar Todos los Tests

```powershell
# Navega al directorio del proyecto
cd "c:\Users\Andres\OneDrive - Tecnologico de Antioquia Institucion Universitaria\Documentos\ConstruccionDeSoftware2\veterinaria"

# Ejecuta todos los tests
.\mvnw.cmd test
```

**Salida esperada**:
```
[INFO] Tests run: 280, Failures: 0, Errors: 0, Skipped: 0
[INFO] BUILD SUCCESS
```

### Ejecutar Tests de una Clase Específica

```powershell
# Sintaxis: -Dtest=NombreDeLaClase
.\mvnw.cmd test -Dtest=PetBuilderTest
```

### Ejecutar Tests de un Paquete

```powershell
# Todos los tests del paquete 'builder'
.\mvnw.cmd test -Dtest=app.adapter.in.builder.*
```

### Ejecutar un Test Individual

```powershell
# Sintaxis: -Dtest=Clase#metodo
.\mvnw.cmd test -Dtest=PetBuilderTest#builder_withValidData_shouldReturnPet
```

### Modo Verbose (Ver Detalles)

```powershell
# Muestra información detallada de cada test
.\mvnw.cmd test -X
```

### Ejecutar Tests sin Recompilar

```powershell
# Útil cuando solo modificaste tests
.\mvnw.cmd surefire:test
```

### Saltar Tests (No Recomendado)

```powershell
# Solo compila, no ejecuta tests
.\mvnw.cmd package -DskipTests
```

---

## Generación de Reporte de Cobertura

### ⚠️ Importante: Configuración de JaCoCo

En este proyecto, JaCoCo está **deshabilitado por defecto** para evitar conflictos con JDK 25. Para generar cobertura, usa el **perfil `coverage`**.

### Paso 1: Ejecutar Tests con Cobertura

```powershell
# Ejecuta tests + genera reporte de cobertura
.\mvnw.cmd verify -Pcoverage
```

**¿Qué hace este comando?**
1. Compila el código
2. Ejecuta todos los tests con el agente JaCoCo
3. Genera reportes en formato HTML y XML
4. Crea un archivo resumen `coverage-lines.txt`

### Paso 2: Abrir el Reporte HTML

```powershell
# Abre el reporte en tu navegador predeterminado
start .\target\site\jacoco\index.html
```

**Ubicación de los reportes**:
```
target/
├── site/
│   └── jacoco/
│       ├── index.html              # Reporte principal (visual)
│       ├── jacoco.xml              # Datos XML (para CI/CD)
│       └── jacoco.csv              # Datos CSV (para análisis)
└── coverage-lines.txt              # Resumen de líneas cubiertas
```

### Paso 3: Leer el Archivo Resumen

```powershell
# Ver resumen rápido de cobertura
type .\target\coverage-lines.txt
```

**Ejemplo de salida**:
```
app/domain/services/CreatePet.java: 15,17,18,20,21,22
app/adapter/in/builder/PetBuilder.java: 30,31,32,33,34,35,36,37,38
```
*(Líneas cubiertas por tests)*

### 🛠️ Solución a Problemas Comunes

#### Error: "Unsupported class file major version 69"

**Causa**: JaCoCo 0.8.12 no soporta completamente JDK 25.

**Solución 1**: Ejecutar con JDK 21 (recomendado)
```powershell
# Configura JAVA_HOME a JDK 21 temporalmente
$env:JAVA_HOME = "C:\Program Files\Java\jdk-21"
$env:PATH = "$env:JAVA_HOME\bin;$env:PATH"

# Verifica
java -version

# Ejecuta cobertura
.\mvnw.cmd verify -Pcoverage
```

**Solución 2**: Excluir clases generadas por Mockito

Si necesitas usar JDK 25, edita `pom.xml` y agrega en el perfil `coverage`:

```xml
<configuration>
    <includes>
        <include>app.*</include>
    </includes>
    <excludes>
        <exclude>**/*$MockitoMock*</exclude>
        <exclude>**/*$auxiliary*</exclude>
    </excludes>
</configuration>
```

---

## Análisis del Reporte

### Navegación del Reporte HTML

1. **Página Principal (`index.html`)**:
   - Resumen global del proyecto
   - Cobertura por paquete

2. **Vista de Paquete**:
   - Clases dentro del paquete
   - Cobertura de cada clase

3. **Vista de Clase**:
   - Código fuente coloreado:
     - 🟢 **Verde**: Línea cubierta
     - 🔴 **Rojo**: Línea NO cubierta
     - 🟡 **Amarillo**: Rama parcialmente cubierta

### Métricas Clave

| Métrica | Descripción | Meta |
|---------|-------------|------|
| **Instructions** | Bytecode ejecutado | > 80% |
| **Branches** | Decisiones if/else/switch | > 75% |
| **Lines** | Líneas de código | > 80% |
| **Methods** | Métodos invocados | > 85% |
| **Classes** | Clases usadas | > 90% |

### Ejemplo de Interpretación

```
Package: app.domain.services
Instructions: 250/300 (83%)
Branches: 45/60 (75%)
Lines: 90/100 (90%)
```

**Interpretación**:
- ✅ 90% de líneas cubiertas → Excelente
- ⚠️ 75% de ramas cubiertas → Faltan tests para algunos casos edge (if/else)
- 🎯 Acción: Agregar tests para las ramas no cubiertas

---

## Buenas Prácticas

### 1. Anatomía de un Test Efectivo

**Patrón AAA (Arrange-Act-Assert)**:

```java
@Test
void builder_withValidData_shouldReturnPet() throws Exception {
    // ARRANGE: Preparar datos de entrada
    String document = "123456";
    String name = "Rex";
    String age = "5";
    
    // ACT: Ejecutar el método bajo prueba
    Pet result = petBuilder.builder(document, name, age, ...);
    
    // ASSERT: Verificar el resultado esperado
    assertNotNull(result);
    assertEquals("Rex", result.getName());
    assertEquals(5, result.getAge());
}
```

### 2. Nombrado de Tests

**Convención**: `metodo_condicion_resultadoEsperado`

Ejemplos:
- ✅ `builder_withValidData_shouldReturnPet`
- ✅ `builder_withInvalidAge_shouldThrowInputsException`
- ❌ `test1` (no descriptivo)
- ❌ `testPetBuilder` (muy genérico)

### 3. Uso de Mocks

**Cuándo usar Mocks**:
- ✅ Para simular dependencias externas (DB, APIs, archivos)
- ✅ Para aislar la unidad bajo prueba
- ❌ No hagas mock de clases que estás probando

**Ejemplo**:
```java
@Mock
private PetValidator petValidator;  // Mock de dependencia

@InjectMocks
private PetBuilder petBuilder;      // Clase bajo prueba (inyecta mocks)

@BeforeEach
void setUp() {
    // Configurar comportamiento del mock
    when(petValidator.nameValidator(anyString()))
        .thenAnswer(i -> i.getArgument(0));
}
```

### 4. Tests para Casos Límite

Siempre prueba:
- ✅ **Casos válidos** (happy path)
- ✅ **Valores nulos**
- ✅ **Valores vacíos** ("")
- ✅ **Valores fuera de rango**
- ✅ **Excepciones esperadas**

```java
@Test
void builder_withNullName_shouldThrowInputsException() {
    assertThrows(InputsException.class, () -> 
        petBuilder.builder("123", null, "5", ...)
    );
}
```

### 5. Configuración Consistente

```java
@ExtendWith(MockitoExtension.class)  // Habilita Mockito
@MockitoSettings(strictness = Strictness.LENIENT)  // Permite stubs no usados
class PetBuilderTest {
    // Tests...
}
```

---

## Ejemplos en Otros Lenguajes

### JavaScript (Jest + Node.js)

#### Setup Inicial

```bash
# Inicializar proyecto
npm init -y

# Instalar dependencias
npm install --save-dev jest @jest/globals

# Configurar script en package.json
```

**package.json**:
```json
{
  "scripts": {
    "test": "jest",
    "test:coverage": "jest --coverage"
  },
  "jest": {
    "coverageDirectory": "coverage",
    "collectCoverageFrom": [
      "src/**/*.js",
      "!src/**/*.test.js"
    ]
  }
}
```

#### Ejemplo de Test

**src/PetBuilder.js**:
```javascript
class PetBuilder {
  build(document, name, age) {
    if (!name) throw new Error('Name is required');
    return { document, name, age: parseInt(age) };
  }
}

module.exports = PetBuilder;
```

**src/PetBuilder.test.js**:
```javascript
const PetBuilder = require('./PetBuilder');

describe('PetBuilder', () => {
  let builder;

  beforeEach(() => {
    builder = new PetBuilder();
  });

  test('build with valid data should return pet', () => {
    // Arrange
    const document = '123';
    const name = 'Rex';
    const age = '5';

    // Act
    const result = builder.build(document, name, age);

    // Assert
    expect(result).toBeDefined();
    expect(result.name).toBe('Rex');
    expect(result.age).toBe(5);
  });

  test('build with null name should throw error', () => {
    // Assert
    expect(() => builder.build('123', null, '5'))
      .toThrow('Name is required');
  });
});
```

#### Comandos

```bash
# Ejecutar tests
npm test

# Generar cobertura
npm run test:coverage

# Abrir reporte HTML
start coverage/lcov-report/index.html  # Windows
open coverage/lcov-report/index.html   # macOS
```

---

### Python (pytest + coverage)

#### Setup Inicial

```bash
# Crear entorno virtual
python -m venv venv
venv\Scripts\activate  # Windows
source venv/bin/activate  # macOS/Linux

# Instalar dependencias
pip install pytest pytest-cov

# Crear archivo de configuración
```

**pytest.ini**:
```ini
[pytest]
testpaths = tests
python_files = test_*.py
python_classes = Test*
python_functions = test_*
```

#### Ejemplo de Test

**src/pet_builder.py**:
```python
class PetBuilder:
    def build(self, document, name, age):
        if not name:
            raise ValueError("Name is required")
        return {
            'document': document,
            'name': name,
            'age': int(age)
        }
```

**tests/test_pet_builder.py**:
```python
import pytest
from src.pet_builder import PetBuilder

class TestPetBuilder:
    
    @pytest.fixture
    def builder(self):
        return PetBuilder()
    
    def test_build_with_valid_data_should_return_pet(self, builder):
        # Arrange
        document = "123"
        name = "Rex"
        age = "5"
        
        # Act
        result = builder.build(document, name, age)
        
        # Assert
        assert result is not None
        assert result['name'] == 'Rex'
        assert result['age'] == 5
    
    def test_build_with_null_name_should_raise_error(self, builder):
        # Assert
        with pytest.raises(ValueError, match="Name is required"):
            builder.build("123", None, "5")
```

#### Comandos

```bash
# Ejecutar tests
pytest

# Ejecutar con verbose
pytest -v

# Generar cobertura
pytest --cov=src --cov-report=html

# Abrir reporte HTML
start htmlcov/index.html  # Windows
open htmlcov/index.html   # macOS
```

---

### C# (.NET + xUnit)

#### Setup Inicial

```powershell
# Crear solución
dotnet new sln -n VeterinariaApp

# Crear proyecto principal
dotnet new classlib -n Veterinaria

# Crear proyecto de tests
dotnet new xunit -n Veterinaria.Tests

# Agregar proyectos a la solución
dotnet sln add Veterinaria/Veterinaria.csproj
dotnet sln add Veterinaria.Tests/Veterinaria.Tests.csproj

# Agregar referencia del proyecto principal en tests
cd Veterinaria.Tests
dotnet add reference ../Veterinaria/Veterinaria.csproj

# Instalar paquete de cobertura
dotnet add package coverlet.collector
```

#### Ejemplo de Test

**Veterinaria/PetBuilder.cs**:
```csharp
using System;

namespace Veterinaria
{
    public class PetBuilder
    {
        public Pet Build(string document, string name, string age)
        {
            if (string.IsNullOrEmpty(name))
                throw new ArgumentException("Name is required");
            
            return new Pet
            {
                Document = document,
                Name = name,
                Age = int.Parse(age)
            };
        }
    }
    
    public class Pet
    {
        public string Document { get; set; }
        public string Name { get; set; }
        public int Age { get; set; }
    }
}
```

**Veterinaria.Tests/PetBuilderTests.cs**:
```csharp
using System;
using Xunit;

namespace Veterinaria.Tests
{
    public class PetBuilderTests
    {
        private readonly PetBuilder _builder;

        public PetBuilderTests()
        {
            _builder = new PetBuilder();
        }

        [Fact]
        public void Build_WithValidData_ShouldReturnPet()
        {
            // Arrange
            var document = "123";
            var name = "Rex";
            var age = "5";

            // Act
            var result = _builder.Build(document, name, age);

            // Assert
            Assert.NotNull(result);
            Assert.Equal("Rex", result.Name);
            Assert.Equal(5, result.Age);
        }

        [Fact]
        public void Build_WithNullName_ShouldThrowArgumentException()
        {
            // Assert
            Assert.Throws<ArgumentException>(() => 
                _builder.Build("123", null, "5")
            );
        }
    }
}
```

#### Comandos

```powershell
# Navegar a la raíz de la solución
cd VeterinariaApp

# Ejecutar tests
dotnet test

# Ejecutar tests con verbose
dotnet test --verbosity normal

# Generar cobertura (formato HTML)
dotnet test --collect:"XPlat Code Coverage" --results-directory ./TestResults

# Instalar herramienta de reportes (una vez)
dotnet tool install -g dotnet-reportgenerator-globaltool

# Generar reporte HTML
reportgenerator `
  -reports:"TestResults/**/coverage.cobertura.xml" `
  -targetdir:"coveragereport" `
  -reporttypes:Html

# Abrir reporte
start coveragereport/index.html
```

---

## Comparación de Herramientas

| Aspecto | Java (JUnit/JaCoCo) | JavaScript (Jest) | Python (pytest) | C# (xUnit) |
|---------|---------------------|-------------------|-----------------|------------|
| **Framework** | JUnit 5 | Jest | pytest | xUnit |
| **Mocking** | Mockito | jest.fn() | unittest.mock | Moq |
| **Cobertura** | JaCoCo | jest --coverage | pytest-cov | coverlet |
| **Assertions** | assertEquals() | expect().toBe() | assert x == y | Assert.Equal() |
| **Setup** | @BeforeEach | beforeEach() | @pytest.fixture | Constructor |

---

## Checklist de Calidad

Antes de considerar un test completo, verifica:

- [ ] ✅ **Nomenclatura descriptiva** (`metodo_condicion_resultado`)
- [ ] ✅ **Patrón AAA** aplicado (Arrange-Act-Assert)
- [ ] ✅ **Casos válidos** cubiertos
- [ ] ✅ **Casos inválidos** cubiertos (nulos, vacíos, excepciones)
- [ ] ✅ **Mocks configurados** correctamente
- [ ] ✅ **Aserciones específicas** (no solo `assertNotNull`)
- [ ] ✅ **Tests aislados** (no dependen de otros)
- [ ] ✅ **Cobertura > 80%** en clases críticas
- [ ] ✅ **Tests rápidos** (< 100ms por test unitario)
- [ ] ✅ **Sin código duplicado** en tests

---

## Recursos Adicionales

### Documentación Oficial
- **JUnit 5**: https://junit.org/junit5/docs/current/user-guide/
- **Mockito**: https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html
- **JaCoCo**: https://www.jacoco.org/jacoco/trunk/doc/
- **Jest**: https://jestjs.io/docs/getting-started
- **pytest**: https://docs.pytest.org/
- **xUnit**: https://xunit.net/docs/getting-started

### Libros Recomendados
- *Test Driven Development: By Example* - Kent Beck
- *Growing Object-Oriented Software, Guided by Tests* - Freeman & Pryce
- *Unit Testing Principles, Practices, and Patterns* - Vladimir Khorikov

### Artículos
- Martin Fowler: https://martinfowler.com/testing/
- Google Testing Blog: https://testing.googleblog.com/

---

## Comandos Rápidos de Referencia

### Java (Este Proyecto)

```powershell
# Setup
cd "c:\Users\Andres\OneDrive - Tecnologico de Antioquia Institucion Universitaria\Documentos\ConstruccionDeSoftware2\veterinaria"

# Ejecutar todos los tests
.\mvnw.cmd test

# Ejecutar tests + cobertura
.\mvnw.cmd verify -Pcoverage

# Ver reporte de cobertura
start .\target\site\jacoco\index.html

# Ver resumen rápido
type .\target\coverage-lines.txt

# Test específico
.\mvnw.cmd test -Dtest=PetBuilderTest

# Limpiar y re-ejecutar
.\mvnw.cmd clean test
```

### JavaScript

```bash
npm test                      # Ejecutar tests
npm run test:coverage         # Tests + cobertura
start coverage/lcov-report/index.html  # Ver reporte
```

### Python

```bash
pytest                        # Ejecutar tests
pytest --cov=src --cov-report=html  # Tests + cobertura
start htmlcov/index.html      # Ver reporte
```

### C#

```powershell
dotnet test                   # Ejecutar tests
dotnet test --collect:"XPlat Code Coverage"  # Tests + cobertura
reportgenerator -reports:"TestResults/**/coverage.cobertura.xml" -targetdir:"coveragereport" -reporttypes:Html
start coveragereport/index.html  # Ver reporte
```

---

## Solución de Problemas Frecuentes

### "Tests no se ejecutan"

```powershell
# Limpiar compilación previa
.\mvnw.cmd clean

# Recompilar y ejecutar
.\mvnw.cmd test
```

### "Error de dependencias"

```powershell
# Forzar actualización de dependencias
.\mvnw.cmd clean install -U
```

### "OutOfMemoryError en tests"

Edita `.mvn/jvm.config` y agrega:
```
-Xmx2048m
```

### "Tests pasan localmente pero fallan en CI/CD"

- Verifica que no dependan de rutas absolutas
- Usa `@TempDir` para archivos temporales
- Evita depender de zona horaria o locale específico

---

## Licencia y Contribuciones

Este documento es material educativo del curso **Construcción de Software 2** - Tecnológico de Antioquia.

**Autor**: Equipo Docente CS2  
**Fecha**: Octubre 2025  
**Versión**: 1.0

---

## Glosario

- **AAA**: Arrange-Act-Assert (patrón de estructura de tests)
- **Mock**: Objeto simulado que imita el comportamiento de una dependencia
- **Stub**: Versión simplificada de una dependencia que devuelve valores predefinidos
- **Cobertura**: Porcentaje de código ejecutado durante los tests
- **Assertion**: Verificación de que un resultado es el esperado
- **Test Suite**: Conjunto de tests relacionados
- **Fixture**: Datos o estado inicial requerido para ejecutar tests
- **CI/CD**: Continuous Integration/Continuous Deployment
- **TDD**: Test-Driven Development (desarrollo guiado por tests)

---

**¿Preguntas?** Consulta con tu docente o revisa los ejemplos en `src/test/java/app/`.
