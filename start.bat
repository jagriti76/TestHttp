@echo off
REM =====================================================
REM Fullstack Application - Windows Startup Script
REM =====================================================

echo ===================================
echo Fullstack Application Setup
echo ===================================
echo.

REM Check prerequisites
echo Checking prerequisites...
echo.

REM Check Java
where java >nul 2>&1
if %ERRORLEVEL% equ 0 (
    echo [OK] Java found
    java -version 2>&1 | findstr /C:"version"
) else (
    echo [X] Java not found. Please install Java 17 or higher.
    echo     Download from: https://adoptium.net/
    pause
    exit /b 1
)

REM Check Node.js
where node >nul 2>&1
if %ERRORLEVEL% equ 0 (
    echo [OK] Node.js found
    node -v
) else (
    echo [X] Node.js not found. Please install Node.js 18 or higher.
    echo     Download from: https://nodejs.org/
    pause
    exit /b 1
)

REM Check npm
where npm >nul 2>&1
if %ERRORLEVEL% equ 0 (
    echo [OK] npm found
    npm -v
) else (
    echo [X] npm not found. It should come with Node.js.
    pause
    exit /b 1
)

echo.
echo All prerequisites satisfied!
echo.
echo ===================================
echo Starting Backend (Spring Boot)...
echo ===================================
echo.

REM Start backend in new window
start "Fullstack Backend" cmd /k "cd backend && gradlew.bat bootRun"

echo Backend started in new window
echo Waiting for backend to initialize...
timeout /t 10 /nobreak >nul

echo.
echo ===================================
echo Starting Frontend (Angular)...
echo ===================================
echo.

REM Start frontend in new window
start "Fullstack Frontend" cmd /k "cd frontend && npm start"

echo Frontend started in new window
echo.
echo ===================================
echo Application Started Successfully!
echo ===================================
echo.
echo Backend running at:  http://localhost:8080
echo Frontend running at: http://localhost:4200
echo.
echo API Endpoint: http://localhost:8080/api/greeting
echo.
echo Both servers are running in separate windows.
echo Close the windows or press Ctrl+C in them to stop the servers.
echo.
pause

