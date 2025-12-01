# 🔧 Solución para el Error en Render.com

## ❌ Problemas Detectados

1. **Render detectó Node.js en lugar de Java**
2. **Error "Permission denied" en `./mvnw`**

## ✅ Soluciones Aplicadas

1. ✅ Agregados permisos de ejecución a `mvnw` en Git
2. ✅ Creado archivo `render.yaml` para configuración
3. ✅ Actualizado `application.properties` con variables de entorno

## 📋 Pasos para Corregir en Render.com

### Opción 1: Actualizar Build Command (RECOMENDADO)

1. Ve a tu servicio en Render.com
2. Ve a **Settings** → **Build & Deploy**
3. Cambia el **Build Command** a:
   ```
   chmod +x ./mvnw && ./mvnw clean package -DskipTests
   ```
4. Asegúrate que el **Start Command** sea:
   ```
   java -jar target/*.jar
   ```

### Opción 2: Cambiar el Runtime a Java

1. Ve a **Settings** → **Environment**
2. Busca la opción **Runtime** o **Environment**
3. Cambia de "Node" a "Java" o "Java 17"
4. Si no aparece, usa la Opción 1

### Opción 3: Usar Maven directamente (si mvnw sigue fallando)

Si el problema persiste, cambia el Build Command a:
```
mvn clean package -DskipTests
```

(Esto requiere que Maven esté instalado en Render, pero normalmente lo está)

## 🔄 Después de Cambiar la Configuración

1. Haz clic en **Manual Deploy** → **Deploy latest commit**
2. Render reconstruirá con la nueva configuración
3. Revisa los logs para verificar que ahora usa Java

## ✅ Verificación

En los logs deberías ver:
- ✅ "Using Java version 17" (en lugar de Node.js)
- ✅ "Running build command..." sin errores de permisos
- ✅ "BUILD SUCCESS"

## 📝 Variables de Entorno Necesarias

Asegúrate de tener configuradas:
- `JWT_SECRET` (obligatorio)
- `SPRING_DATASOURCE_URL`
- `SPRING_DATASOURCE_USERNAME`
- `SPRING_DATASOURCE_PASSWORD`

