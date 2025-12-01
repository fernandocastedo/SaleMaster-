@echo off
echo ========================================
echo Ejecutando SalesMaster JAR
echo ========================================
echo.
echo Verificando archivo .env...
if not exist .env (
    echo [ADVERTENCIA] No se encontro el archivo .env
    echo La aplicacion requiere JWT_SECRET
    echo.
    pause
)
echo.
echo Iniciando aplicacion...
echo Presiona Ctrl+C para detener
echo.
java -jar target\SalesMaster-0.0.1-SNAPSHOT.jar
pause

