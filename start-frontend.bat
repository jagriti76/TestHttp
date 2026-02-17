@echo off
REM =====================================================
REM Angular Frontend - Windows Startup Script
REM =====================================================

echo.
echo Starting Angular Frontend...
echo ============================
echo.

REM Check if Node.js is installed
where node >nul 2>&1
if %ERRORLEVEL% neq 0 (
    echo ERROR: Node.js is not installed.
    echo Please install Node.js 18 or higher:
    echo   - Download from: https://nodejs.org/
    echo.
    pause
    exit /b 1
)

REM Check if npm is installed
where npm >nul 2>&1
if %ERRORLEVEL% neq 0 (
    echo ERROR: npm is not installed.
    echo It should come with Node.js installation.
    echo.
    pause
    exit /b 1
)

REM Install dependencies if node_modules doesn't exist
if not exist "node_modules\" (
    echo Installing dependencies...
    call npm install
    echo.
)

echo Starting Angular development server...
echo Frontend will be available at: http://localhost:4200
echo.
echo Make sure the backend is running on http://localhost:8080
echo.
echo Press Ctrl+C to stop the server
echo.

call npm start

