# Sistema de Testing y Cobertura

> **📚 GUÍA COMPLETA PARA ESTUDIANTES**: Este documento es un resumen técnico. Para una guía detallada con comandos copy-paste y ejemplos en JavaScript, Python y C#, consulta **[GUIA_TESTING.md](GUIA_TESTING.md)**

Este documento describe el enfoque de pruebas unitarias del proyecto, cómo medir la cobertura, y el paso a paso para ejecutar los tests y generar reportes. Al final, se genera automáticamente un archivo con el detalle de líneas cubiertas y no cubiertas por clase.

## Enfoque de pruebas

El proyecto sigue arquitectura por capas (Dominio, Aplicación, Adaptadores). Las pruebas están organizadas por paquete, una por cada clase pública y elementos clave:

- Validators (adapter/in/validators): pruebas de validación, inputs válidos/ inválidos y casos nulos.
- Builders (adapter/in/builder): construcción de modelos de dominio desde inputs string; se mockean validators.
- Domain Services (domain/services): reglas de negocio con puertos mockeados.
- Use Cases (application/usecases): orquestación y asignación de roles.
- REST Mappers (adapter/rest/mapper): mapeo Request → Dominio → Response.
- Controllers (adapter/in/rest/controllers): endpoints, códigos HTTP y propagación de excepciones.
- Exception Handler (adapter/in/rest/controllers): mapeo 400/409/500.
- Infrastructure Mappers (infrastructure/persistence/mapper): mapeo Dominio ↔ Entidad JPA (incluye campos renombrados como dose/doce, procedure/proceddure).
- Infrastructure Adapters (adapter/out/persistence, adapter/out/security): implementación de puertos con repositorios/seguridad mockeados.

Cada prueba cubre:
- Happy paths (flujos exitosos)
- Errores esperables (InputsException/BusinessException)
- Nulos y límites (campos opcionales, enums, mapeos especiales)

## Cobertura de código (JaCoCo)

Se integró el plugin de JaCoCo en Maven para instrumentar las pruebas y generar reportes en HTML y XML:
- HTML: `target/site/jacoco/index.html`
- XML: `target/site/jacoco/jacoco.xml` (base para reportes automatizados)

Además, se agregó un generador automático que produce un archivo con las líneas ejecutadas/no ejecutadas por clase:
- Archivo: `target/coverage-lines.txt`

Este archivo se genera automáticamente en la fase `verify`.

## Requisitos previos

- Java 21 (LTS) instalado y JAVA_HOME configurado a un JDK válido. El proyecto compila con `--release 21` (JDK ≥ 21 también es válido).
- Maven Wrapper del proyecto (mvnw.cmd) o Maven instalado.

## Cómo ejecutar los tests y generar cobertura

En PowerShell (Windows):

```powershell
# 1) Validar Java
$env:JAVA_HOME
& "$env:JAVA_HOME\bin\java.exe" -version

# 2) Ejecutar pruebas y generar cobertura + archivo de líneas
cd "c:\Users\Andres\OneDrive - Tecnologico de Antioquia Institucion Universitaria\Documentos\ConstruccionDeSoftware2\veterinaria"
.\mvnw.cmd -q verify
```

Salidas principales:
- Reporte HTML: `target/site/jacoco/index.html`
- Detalle por línea: `target/coverage-lines.txt`

Si prefieres ejecutar en dos pasos:

```powershell
.\mvnw.cmd -q test
.\mvnw.cmd -q jacoco:report
.\mvnw.cmd -q exec:java -Dexec.mainClass=coverage.CoverageReportGenerator -Dexec.args="target/site/jacoco/jacoco.xml target/coverage-lines.txt"
```

### Ejecución focalizada y exclusiones temporales

- Una clase de test específica:
```powershell
.\mvnw.cmd -Dtest=app.adapter.rest.mapper.ClinicalRecordRestMapperTest test
```

- Por patrón (comodín):
```powershell
.\mvnw.cmd -Dtest="*MapperTest" test
```

- Excluir temporalmente pruebas de contexto completo de Spring (por ejemplo `VeterinariaApplicationTests`) mientras se estabiliza el entorno:
```powershell
.\mvnw.cmd -Dtest="*" -DexcludeTests=VeterinariaApplicationTests test
```

## Interpretación de los reportes

- `index.html`: cobertura por paquete, clase y métricas (líneas, instrucciones, ramas). Abre en el navegador.
- `coverage-lines.txt`: lista, por clase, de:
  - "Líneas cubiertas": líneas que tuvieron ejecución durante los tests.
  - "Líneas NO cubiertas": líneas instrumentadas que no fueron ejecutadas.

Ejemplo (fragmento):

```
Clase: app.domain.services.CreateUser
  Líneas cubiertas (12): [23, 24, 25, 31, 33, 35, 41, 42, 43, 44, 50, 51]
  Líneas NO cubiertas (2): [60, 61]
```

## Objetivo de cobertura

El objetivo de cobertura es ≥ 90% de líneas. El conjunto de pruebas cubre todas las capas mencionadas; si el reporte queda por debajo del objetivo:
- Revisa `coverage-lines.txt` para identificar clases/líneas faltantes.
- Agrega tests que ejerciten esos flujos (p.ej., paths de error, enums adicionales, nulos).

## Solución de problemas

- Error JAVA_HOME no definido: configura JAVA_HOME a un JDK 21 y agrega `%JAVA_HOME%\bin` al PATH. Vuelve a abrir la terminal.
- Fallos de compilación: revisa mensajes en la consola y corrige imports/typos.
- Reporte `jacoco.xml` no encontrado: asegúrate de ejecutar `verify` (o `jacoco:report`) antes del generador.

### Contexto Spring y base de datos en pruebas

Para pruebas unitarias no se requiere base de datos real. Se añadió `src/test/resources/application.properties` para evitar que el contexto intente inicializar JDBC/JPA:

```
spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration,org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration
```

Si necesitas ejecutar pruebas de contexto (`@SpringBootTest`) que sí dependan de repositorios, considera:
- Añadir H2 en scope `test` y configurar `spring.datasource.url=jdbc:h2:mem:testdb` y `spring.jpa.hibernate.ddl-auto=create-drop` en `src/test/resources/application.properties`.
- O mockear explícitamente beans de repositorio con `@MockBean` en esa clase de prueba.

### Mockito: stubs innecesarios

Si aparece `UnnecessaryStubbingException`, elimina stubs no usados o haz el mock `lenient()` en casos puntuales. Mantén los tests con el mínimo stubbing necesario.

### JWT: tokens generados en el mismo instante

Si se comprueba la unicidad de dos tokens generados seguidos y falla, puede ser por reutilizar la misma marca de tiempo. Opciones:
- Inyectar un `Clock` y mockearlo.
- Incluir un `jti` aleatorio.
- Esperar unos milisegundos entre generaciones sólo para la prueba.

## Estructura relevante

- Plugin JaCoCo: configurado en `pom.xml` (prepare-agent en pruebas, reporte en verify).
- Generador de líneas: clase `coverage.CoverageReportGenerator` (ubicada en `src/test/java/coverage`).

Con esto, tendrás un flujo reproducible para ejecutar tests, validar cobertura y obtener un detalle claro de líneas cubiertas y no cubiertas por clase.

---

## Bases y decisiones aplicadas en las pruebas

- Patrón AAA (Arrange-Act-Assert) y nombres descriptivos.
- Aislamiento por capas: se mockean puertos/repositorios; mappers y builders no tocan DB.
- Alineación con el dominio y DTOs actuales:
  - `ClinicalRecordRestMapperTest`: usa `ClinicalRecordBuilder.create(veterinarianDocument, petId, orderId)` (3 argumentos) y estado booleano; se eliminaron aserciones sobre campos no expuestos en la `Response`.
  - `Invoice` en dominio usa `productName` y `productAmount` (no `description`/`value`); se ajustó la prueba del adapter.
  - `Spices` incluye `RABBIT` para cubrir los casos esperados por pruebas de mapeo de mascotas.
- Configuración de pruebas: `src/test/resources/application.properties` desactiva auto-config de JDBC/JPA para acelerar y hacer determinísticas las unitarias.
