#!/bin/bash

echo "==================================="
echo "Fullstack Application Setup"
echo "==================================="
echo ""

# Check prerequisites
echo "Checking prerequisites..."

# Check Java
if command -v java &> /dev/null; then
    JAVA_VERSION=$(java -version 2>&1 | head -n 1)
    echo "✓ Java found: $JAVA_VERSION"
else
    echo "✗ Java not found. Please install Java 17 or higher."
    echo "  Download from: https://adoptium.net/"
    exit 1
fi

# Check Node.js
if command -v node &> /dev/null; then
    NODE_VERSION=$(node -v)
    echo "✓ Node.js found: $NODE_VERSION"
else
    echo "✗ Node.js not found. Please install Node.js 18 or higher."
    echo "  Download from: https://nodejs.org/"
    exit 1
fi

# Check npm
if command -v npm &> /dev/null; then
    NPM_VERSION=$(npm -v)
    echo "✓ npm found: v$NPM_VERSION"
else
    echo "✗ npm not found. It should come with Node.js."
    exit 1
fi

echo ""
echo "All prerequisites satisfied!"
echo ""
echo "==================================="
echo "Starting Backend (Spring Boot)..."
echo "==================================="
echo ""

cd backend
./gradlew bootRun &
BACKEND_PID=$!
cd ..

echo "Backend started with PID: $BACKEND_PID"
echo "Waiting for backend to initialize..."
sleep 10

echo ""
echo "==================================="
echo "Starting Frontend (Angular)..."
echo "==================================="
echo ""

cd frontend
npm start &
FRONTEND_PID=$!
cd ..

echo "Frontend started with PID: $FRONTEND_PID"
echo ""
echo "==================================="
echo "Application Started Successfully!"
echo "==================================="
echo ""
echo "Backend running at:  http://localhost:8080"
echo "Frontend running at: http://localhost:4200"
echo ""
echo "API Endpoint: http://localhost:8080/api/greeting"
echo ""
echo "Press Ctrl+C to stop both servers..."
echo ""

# Wait for both processes
wait $BACKEND_PID $FRONTEND_PID

