# Errores Identificados en el Proyecto SalesMaster

## 🔴 ERRORES CRÍTICOS (Impiden la ejecución)

### 1. **Falta archivo .env con JWT_SECRET**
   - **Ubicación**: `JwtService.java` línea 35-52
   - **Problema**: El servicio JWT requiere la variable `JWT_SECRET` en un archivo `.env` o en variables de entorno del sistema. Si no existe, lanza `IllegalStateException` al iniciar.
   - **Solución**: Crear archivo `.env` en la raíz del proyecto con:
     ```
     JWT_SECRET=<clave_base64_de_al_menos_32_bytes>
     ```
   - **Generar clave**: Usar `openssl rand -base64 64` o similar

### 2. **Base de datos PostgreSQL no configurada**
   - **Ubicación**: `application.properties` líneas 2-4
   - **Problema**: El proyecto requiere PostgreSQL corriendo en `localhost:5432` con:
     - Base de datos: `salesmasterdb`
     - Usuario: `postgres`
     - Contraseña: `catetopositive`
   - **Solución**: 
     - Instalar y configurar PostgreSQL
     - Crear la base de datos `salesmasterdb`
     - O modificar `application.properties` con tus credenciales

## ⚠️ ERRORES DE CÓDIGO (No críticos pero deben corregirse)

### 3. **Inconsistencia en inyección de dependencias**
   - **Ubicación**: Varios controladores
   - **Problema**: 
     - `AuthController` usa `@RequiredArgsConstructor` (correcto)
     - `ProductoController`, `ClienteController`, `PedidoController`, `FacturaController` usan `@Autowired` (menos recomendado)
   - **Solución**: Estandarizar usando `@RequiredArgsConstructor` en todos los controladores

### 4. **Redundancia en ProductoServiceImpl**
   - **Ubicación**: `ProductoServiceImpl.java` líneas 17-20
   - **Problema**: Usa `@AllArgsConstructor` y `@Autowired` al mismo tiempo, lo cual es redundante
   - **Solución**: Eliminar `@Autowired` y usar solo `@RequiredArgsConstructor` o `@AllArgsConstructor`

### 5. **MapStruct no se está utilizando**
   - **Ubicación**: Todos los mappers (`ClienteMapper`, `ProductoMapper`, etc.)
   - **Problema**: MapStruct está en las dependencias pero los mappers son clases estáticas normales
   - **Solución**: 
     - Opción A: Eliminar MapStruct del `pom.xml` si no se va a usar
     - Opción B: Refactorizar mappers para usar MapStruct con `@Mapper`

### 6. **Falta validación de null en JwtAuthenticationFilter**
   - **Ubicación**: `JwtAuthenticationFilter.java` línea 55
   - **Problema**: Si `extractUsername` lanza excepción, no se maneja correctamente
   - **Solución**: Agregar try-catch para manejar tokens inválidos sin romper la aplicación

## 📝 OBSERVACIONES Y MEJORAS SUGERIDAS

### 7. **Credenciales hardcodeadas en application.properties**
   - **Ubicación**: `application.properties` línea 4
   - **Problema**: La contraseña de la base de datos está expuesta
   - **Solución**: Mover a variables de entorno o usar Spring Cloud Config

### 8. **Falta configuración de puerto del servidor**
   - **Problema**: No se especifica el puerto en `application.properties`
   - **Solución**: Agregar `server.port=8080` (o el puerto deseado)

### 9. **CORS configurado pero puede mejorarse**
   - **Ubicación**: `CorsConfig.java`
   - **Observación**: CORS está bien configurado, pero podría usar variables de entorno para los orígenes permitidos

### 10. **Falta manejo de excepciones JWT**
   - **Ubicación**: `JwtAuthenticationFilter.java`
   - **Problema**: Si el token es inválido o expirado, no se devuelve una respuesta HTTP apropiada
   - **Solución**: Agregar manejo de excepciones JWT en el filtro

## 🧪 PRUEBAS RECOMENDADAS

1. Verificar que PostgreSQL esté corriendo antes de iniciar la aplicación
2. Crear el archivo `.env` con `JWT_SECRET` antes de ejecutar
3. Probar endpoints de autenticación (`/api/auth/register`, `/api/auth/login`)
4. Verificar que Swagger UI funcione en `http://localhost:8080/swagger-ui/index.html`
5. Probar CRUD de todas las entidades (Clientes, Productos, Pedidos, Facturas)

## 📋 CHECKLIST ANTES DE EJECUTAR

- [ ] PostgreSQL instalado y corriendo
- [ ] Base de datos `salesmasterdb` creada
- [ ] Archivo `.env` creado con `JWT_SECRET`
- [ ] Java 17 instalado y configurado
- [ ] Maven instalado o usar `mvnw.cmd`
- [ ] Variables de entorno JAVA_HOME configuradas (si se usa Maven directamente)

