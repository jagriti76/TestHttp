# Quick Start Guide

## Prerequisites Check

Before starting, ensure you have these installed:

1. **Java 17+**: 
   ```bash
   java -version
   ```
   If not installed: https://adoptium.net/

2. **Node.js 18+**: 
   ```bash
   node -v
   ```
   If not installed: https://nodejs.org/

3. **npm**: 
   ```bash
   npm -v
   ```
   (Comes with Node.js)

**Note:** Gradle is included via the Gradle Wrapper (gradlew), so no separate installation is needed!

## Starting the Application

### Option 1: Manual Start (Recommended for First Time)

#### Terminal 1 - Start Backend:
```bash
cd /Users/p901kxo/ws/fullstack-app/backend
./gradlew bootRun
```

Wait until you see: `Started FullstackApplication in X seconds`

#### Terminal 2 - Start Frontend:
```bash
cd /Users/p901kxo/ws/fullstack-app/frontend
npm install  # Only needed first time
npm start
```

Wait until you see: `Angular Live Development Server is listening on localhost:4200`

### Option 2: Using Start Scripts

#### Terminal 1:
```bash
cd /Users/p901kxo/ws/fullstack-app/backend
./start-backend.sh
```

#### Terminal 2:
```bash
cd /Users/p901kxo/ws/fullstack-app/frontend
./start-frontend.sh
```

## Access the Application

1. **Frontend**: Open browser at http://localhost:4200
2. **Backend API**: http://localhost:8080/api/greeting

You should see a beautiful page with the message "hi jagriti" displayed in a styled card!

## Verify It's Working

### Test Backend Directly:
```bash
curl http://localhost:8080/api/greeting
```
Expected output: `hi jagriti`

### Test Frontend:
Open http://localhost:4200 in your browser. You should see:
- A gradient purple background
- A white card with "Greeting from Backend"
- The message "hi jagriti" displayed prominently

## Troubleshooting

### Backend Issues:

**Port 8080 already in use:**
```bash
# Find and kill process using port 8080
lsof -ti:8080 | xargs kill -9
```

**Java not found:**
Download and install from https://adoptium.net/

**Gradle build fails:**
The Gradle wrapper (gradlew) is included, so you don't need to install Gradle separately.
Just ensure Java 17+ is installed.

### Frontend Issues:

**Port 4200 already in use:**
```bash
# Find and kill process using port 4200
lsof -ti:4200 | xargs kill -9
```

**npm install fails:**
```bash
# Clear npm cache and retry
npm cache clean --force
rm -rf node_modules package-lock.json
npm install
```

**CORS errors in browser console:**
- Make sure backend is running on port 8080
- Check browser console for specific error messages
- Backend is configured to allow requests from http://localhost:4200

## What You'll See

When everything is running correctly:

1. **Backend Console**: Shows Spring Boot startup logs
2. **Frontend Console**: Shows Angular compilation and dev server logs
3. **Browser** (http://localhost:4200):
   - Header: "Fullstack Application"
   - Subheader: "Angular 21 Frontend + Spring Boot 3 Backend"
   - Card with "Greeting from Backend"
   - Message: "hi jagriti" (in large purple text on light blue background)

## Architecture Flow

1. Browser loads Angular app from http://localhost:4200
2. `AppComponent` initializes and calls `GreetingService`
3. Service makes HTTP GET to http://localhost:8080/api/greeting
4. Spring Boot `GreetingController` returns "hi jagriti"
5. Angular displays the message with styling

## Stopping the Application

Press `Ctrl+C` in each terminal window to stop the servers.

## Next Steps

- Modify `/backend/src/main/java/com/example/fullstack/controller/GreetingController.java` to change the message
- Update `/frontend/src/app/app.component.html` to modify the UI
- Add new endpoints in the backend
- Create new components in the frontend

Enjoy coding! 🚀

