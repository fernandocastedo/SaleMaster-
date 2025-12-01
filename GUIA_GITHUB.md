# Guía para Subir el Proyecto a GitHub

## 📋 Respuesta a tu pregunta: ¿Backend y Frontend?

**Para Render.com (según tu guía):**
- ✅ **Solo necesitas subir el BACKEND** (SalesMaster) a GitHub
- El frontend se despliega en otro servicio (Vercel, Netlify, etc.) o en Render como servicio separado

**Recomendación:**
- **Backend**: Repositorio separado o carpeta `backend/` en el mismo repo
- **Frontend**: Repositorio separado o carpeta `frontend/` en el mismo repo

## 🚀 Pasos para Subir el Backend a GitHub

### Opción 1: Si ya tienes un repositorio en GitHub (recomendado)

1. **Verificar cambios pendientes:**
   ```bash
   git status
   ```

2. **Agregar todos los archivos necesarios:**
   ```bash
   git add .
   ```

3. **Hacer commit:**
   ```bash
   git commit -m "Preparar proyecto para despliegue en Render.com"
   ```

4. **Subir a GitHub:**
   ```bash
   git push origin main
   ```

### Opción 2: Crear un nuevo repositorio en GitHub

1. **Ve a GitHub.com** y crea un nuevo repositorio
2. **NO inicialices con README** (ya tienes código)
3. **Copia la URL** del repositorio (ej: `https://github.com/tu-usuario/salesmaster-backend.git`)

4. **Si ya tienes git inicializado:**
   ```bash
   git remote set-url origin https://github.com/tu-usuario/salesmaster-backend.git
   git push -u origin main
   ```

5. **Si NO tienes git inicializado:**
   ```bash
   git init
   git add .
   git commit -m "Initial commit - SalesMaster Backend"
   git branch -M main
   git remote add origin https://github.com/tu-usuario/salesmaster-backend.git
   git push -u origin main
   ```

## ⚠️ Archivos que NO se suben (gracias a .gitignore)

- ✅ `target/` - Archivos compilados
- ✅ `.env` - Variables de entorno sensibles
- ✅ `*.jar` - Archivos JAR compilados
- ✅ `.idea/` - Configuración de IDE

## ✅ Archivos que SÍ se suben

- ✅ `pom.xml` - Configuración de Maven
- ✅ `src/` - Todo el código fuente
- ✅ `mvnw` y `mvnw.cmd` - Maven Wrapper
- ✅ `env.example.txt` - Ejemplo de variables de entorno
- ✅ `.gitignore` - Configuración de Git
- ✅ `application.properties` - Configuración (con puerto dinámico)

## 🔐 Importante: Variables de Entorno

**NO subas el archivo `.env`** con tus credenciales reales. Render.com te permitirá configurar:
- `JWT_SECRET`
- `SPRING_DATASOURCE_URL`
- `SPRING_DATASOURCE_USERNAME`
- `SPRING_DATASOURCE_PASSWORD`

Directamente en su panel, sin exponerlas en GitHub.

## 📝 Siguiente Paso

Una vez subido a GitHub, sigue la guía de Render.com:
1. Conecta tu repositorio de GitHub en Render
2. Configura el Build Command: `./mvnw clean package -DskipTests`
3. Configura el Start Command: `java -jar target/*.jar`
4. Agrega las variables de entorno en Render

