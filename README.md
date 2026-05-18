# Microservicio de Ofertas y Pujas (`ofertas-proyecto`)

## Integrantes
* **Gonzalo Hormazábal**
* **Geraldinne González**


## Descripción
Módulo de alta concurrencia encargado de registrar y validar las ofertas de los postores. Implementa comunicación inter-microservicios reactiva/bloqueante para verificar datos en tiempo real.
* **Puerto:** `8084`
* **Base de Datos:** `ofertas_db` (MySQL)


## Funcionalidades Clave
* Registro y auditoría instantánea de pujas económicas.
* Comunicación síncrona vía **WebClient** con `usuario-proyecto` (Puerto 8081) y `subasta-proyecto` (Puerto 8083) para la validación previa de existencias.
* Rechazo automatizado de ofertas inferiores a la puja más alta actual.


## Configuración (`application.properties`)
* server.port=8084
* spring.datasource.url=jdbc:mysql://localhost:3306/ofertas_db
* spring.datasource.username=root
* spring.datasource.password=
* spring.jpa.hibernate.ddl-auto=update
* logging.level.cl.sda1085.ofertas=DEBUG

# URLs de servicios externos
subastas-service.url=http://localhost:8083/api/subastas
usuarios-service.url=http://localhost:8081/api/usuarios


## Pasos para Ejecutar

### 1. Preparación de la Base de Datos
Antes de ejecutar el servicio, crear la conexión a la base de datos de MySQL (XAMPP) corriendo en el puerto 3306 y con el nombre 'ofertas_db'.

### 2. Verificación de Credenciales
Revisar que el archivo application.properties tenga por defecto, usuario root y contraseña vacía.

### 3. Lanzamiento del Microservicio
Ejecutar (run) la clase principal con la anotación @SpringBootApplication (OfertasApplication.java).

### 4. Reglas de Seguridad
Al consumir los endpoints en Postman, ten en cuenta el comportamiento de la cadena de filtros de seguridad:

* Historial de Pujas (GET /api/ofertas/subasta/{id}): Es público para que se vea la puja más alta actual del artículo (No Auth).
