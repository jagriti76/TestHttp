#!/bin/bash

echo "Starting Angular Frontend..."
echo "============================"
echo ""

# Check if Node.js is installed
if ! command -v node &> /dev/null; then
    echo "Error: Node.js is not installed."
    echo "Please install Node.js 18 or higher:"
    echo "  - Download from: https://nodejs.org/"
    exit 1
fi

# Check if npm is installed
if ! command -v npm &> /dev/null; then
    echo "Error: npm is not installed."
    echo "It should come with Node.js installation."
    exit 1
fi

# Install dependencies if node_modules doesn't exist
if [ ! -d "node_modules" ]; then
    echo "Installing dependencies..."
    npm install
    echo ""
fi

echo "Starting Angular development server..."
echo "Frontend will be available at: http://localhost:4200"
echo ""
echo "Make sure the backend is running on http://localhost:8080"
echo ""

npm start

