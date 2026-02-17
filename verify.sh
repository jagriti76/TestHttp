#!/bin/bash

echo "======================================"
echo "Fullstack Application - File Checker"
echo "======================================"
echo ""

# Color codes
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

check_file() {
    if [ -f "$1" ]; then
        echo -e "${GREEN}✓${NC} $1"
        return 0
    else
        echo -e "${RED}✗${NC} $1 ${RED}(MISSING)${NC}"
        return 1
    fi
}

check_dir() {
    if [ -d "$1" ]; then
        echo -e "${GREEN}✓${NC} $1/"
        return 0
    else
        echo -e "${RED}✗${NC} $1/ ${RED}(MISSING)${NC}"
        return 1
    fi
}

echo "Checking project structure..."
echo ""

# Backend files
echo "Backend Files:"
check_file "backend/build.gradle"
check_file "backend/settings.gradle"
check_file "backend/gradlew"
check_file "backend/gradlew.bat"
check_file "backend/src/main/java/com/example/fullstack/FullstackApplication.java"
check_file "backend/src/main/java/com/example/fullstack/controller/GreetingController.java"
check_file "backend/src/main/resources/application.properties"
check_file "backend/start-backend.sh"
echo ""

# Frontend files
echo "Frontend Files:"
check_file "frontend/package.json"
check_file "frontend/angular.json"
check_file "frontend/src/app/app.component.ts"
check_file "frontend/src/app/app.component.html"
check_file "frontend/src/app/app.component.css"
check_file "frontend/src/app/app.config.ts"
check_file "frontend/src/app/services/greeting.service.ts"
check_file "frontend/start-frontend.sh"
echo ""

# Documentation files
echo "Documentation Files:"
check_file "README.md"
check_file "QUICKSTART.md"
check_file "PROJECT_SUMMARY.md"
check_file ".gitignore"
check_file "start.sh"
echo ""

# Check prerequisites
echo "======================================"
echo "Checking Prerequisites..."
echo "======================================"
echo ""

if command -v java &> /dev/null; then
    JAVA_VERSION=$(java -version 2>&1 | head -n 1)
    echo -e "${GREEN}✓${NC} Java: $JAVA_VERSION"
else
    echo -e "${RED}✗${NC} Java not found - Install from https://adoptium.net/"
fi

echo -e "${GREEN}✓${NC} Gradle: Included via wrapper (gradlew)"

if command -v node &> /dev/null; then
    NODE_VERSION=$(node -v)
    echo -e "${GREEN}✓${NC} Node.js: $NODE_VERSION"
else
    echo -e "${RED}✗${NC} Node.js not found - Install from https://nodejs.org/"
fi

if command -v npm &> /dev/null; then
    NPM_VERSION=$(npm -v)
    echo -e "${GREEN}✓${NC} npm: v$NPM_VERSION"
else
    echo -e "${RED}✗${NC} npm not found"
fi

echo ""
echo "======================================"
echo "Verification Complete!"
echo "======================================"
echo ""
echo "To start the application:"
echo "  1. Open terminal, run: cd backend && ./start-backend.sh"
echo "  2. Open another terminal, run: cd frontend && ./start-frontend.sh"
echo "  3. Open browser: http://localhost:4200"
echo ""
echo "For detailed instructions, see QUICKSTART.md"

