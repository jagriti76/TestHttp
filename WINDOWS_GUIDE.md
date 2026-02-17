# 🪟 Windows Setup Guide

## ✅ Yes! This Application Works on Windows!

Your fullstack application is **fully compatible** with Windows. All necessary Windows scripts have been included.

---

## 📦 What's Included for Windows

### Windows Batch Scripts (.bat)
- ✅ `backend/start-backend.bat` - Start Spring Boot backend
- ✅ `frontend/start-frontend.bat` - Start Angular frontend
- ✅ `start.bat` - Start both servers at once
- ✅ `backend/gradlew.bat` - Gradle wrapper for Windows

---

## 🚀 Quick Start on Windows

### Option 1: Start Both Servers at Once (Easiest)

1. **Open File Explorer**
2. **Navigate to:** `C:\...\fullstack-app\`
3. **Double-click:** `start.bat`

This will:
- Check prerequisites
- Start backend in one window
- Start frontend in another window
- Both servers will run automatically

### Option 2: Manual Start (Two Command Prompts)

#### Command Prompt 1 - Backend
```batch
cd C:\path\to\fullstack-app\backend
start-backend.bat
```

Or directly:
```batch
gradlew.bat bootRun
```

#### Command Prompt 2 - Frontend
```batch
cd C:\path\to\fullstack-app\frontend
start-frontend.bat
```

Or directly:
```batch
npm start
```

---

## 📋 Prerequisites for Windows

### Required Software

1. **Java 17 or Higher**
   - Check: `java -version` in Command Prompt
   - Download: https://adoptium.net/
   - Choose: Windows x64 installer

2. **Node.js 18 or Higher**
   - Check: `node -v` in Command Prompt
   - Download: https://nodejs.org/
   - Choose: Windows Installer (.msi)

3. **npm** (comes with Node.js)
   - Check: `npm -v` in Command Prompt

**Note:** You do NOT need to install Gradle! The Gradle wrapper (`gradlew.bat`) is included.

---

## 🔧 Installation Steps for Windows

### Step 1: Install Java

1. Go to https://adoptium.net/
2. Download: **Eclipse Temurin 17 (LTS)** for Windows x64
3. Run the installer
4. Check "Set JAVA_HOME environment variable"
5. Click "Install"
6. Verify in Command Prompt:
   ```batch
   java -version
   ```

### Step 2: Install Node.js

1. Go to https://nodejs.org/
2. Download: **LTS version** (Windows Installer .msi)
3. Run the installer
4. Keep all default options
5. Click "Install"
6. Verify in Command Prompt:
   ```batch
   node -v
   npm -v
   ```

### Step 3: Extract the Application

1. Copy/extract `fullstack-app` folder to your desired location
   - Example: `C:\Users\YourName\Projects\fullstack-app\`

---

## 🎯 Running the Application on Windows

### Method 1: Using Batch Scripts (Recommended)

#### Start Everything at Once
```batch
# Navigate to project folder
cd C:\path\to\fullstack-app

# Double-click or run
start.bat
```

This opens two separate Command Prompt windows:
- **Window 1:** Backend (Spring Boot)
- **Window 2:** Frontend (Angular)

#### Start Backend Only
```batch
cd C:\path\to\fullstack-app\backend
start-backend.bat
```

#### Start Frontend Only
```batch
cd C:\path\to\fullstack-app\frontend
start-frontend.bat
```

### Method 2: Using Command Prompt

**Backend:**
```batch
cd C:\path\to\fullstack-app\backend
gradlew.bat bootRun
```

**Frontend:**
```batch
cd C:\path\to\fullstack-app\frontend
npm install
npm start
```

### Method 3: Using PowerShell

**Backend:**
```powershell
cd C:\path\to\fullstack-app\backend
.\gradlew.bat bootRun
```

**Frontend:**
```powershell
cd C:\path\to\fullstack-app\frontend
npm install
npm start
```

---

## 🌐 Accessing the Application

Once both servers are running:

1. **Frontend:** http://localhost:4200
   - Open in your browser (Chrome, Edge, Firefox)
   - You should see the greeting message

2. **Backend API:** http://localhost:8080/api/greeting
   - Test in browser or with:
   ```batch
   curl http://localhost:8080/api/greeting
   ```

---

## 🧪 Testing on Windows

### Test Backend

**Command Prompt:**
```batch
curl http://localhost:8080/api/greeting
```

**PowerShell:**
```powershell
Invoke-WebRequest -Uri http://localhost:8080/api/greeting
```

**Browser:**
- Open: http://localhost:8080/api/greeting
- Should display: `hi jagriti`

### Test Frontend

**Browser:**
- Open: http://localhost:4200
- Should see: Beautiful page with "hi jagriti" message

---

## 🔍 Windows-Specific Commands

### Check Ports in Use
```batch
# Check port 8080 (backend)
netstat -ano | findstr :8080

# Check port 4200 (frontend)
netstat -ano | findstr :4200
```

### Kill Process on Port
```batch
# Find process ID
netstat -ano | findstr :8080

# Kill process (replace PID with actual process ID)
taskkill /PID <PID> /F
```

### Stop the Servers
- Press `Ctrl+C` in each Command Prompt window
- Or close the Command Prompt windows

---

## 🛠️ Windows Troubleshooting

### Issue: "Java is not recognized"

**Solution:**
1. Check if Java is installed: `where java`
2. If not found, install from https://adoptium.net/
3. Verify installation: `java -version`

### Issue: "npm is not recognized"

**Solution:**
1. Check if Node.js is installed: `where node`
2. If not found, install from https://nodejs.org/
3. Restart Command Prompt after installation
4. Verify: `node -v` and `npm -v`

### Issue: Port 8080 Already in Use

**Solution:**
```batch
# Find what's using port 8080
netstat -ano | findstr :8080

# Kill the process (replace PID)
taskkill /PID <PID> /F

# Or use different port in application.properties
```

### Issue: Port 4200 Already in Use

**Solution:**
```batch
# Find what's using port 4200
netstat -ano | findstr :4200

# Kill the process
taskkill /PID <PID> /F
```

### Issue: gradlew.bat Permission Denied

**Solution:**
- Right-click `gradlew.bat`
- Properties → Security
- Make sure you have "Read & Execute" permissions

### Issue: npm install Fails

**Solution:**
```batch
# Clear npm cache
npm cache clean --force

# Delete node_modules and package-lock.json
rmdir /s /q node_modules
del package-lock.json

# Reinstall
npm install
```

---

## 📂 Windows File Paths

Windows uses backslashes `\` for paths:

```batch
# Correct Windows paths
C:\Users\YourName\fullstack-app\backend
C:\Users\YourName\fullstack-app\frontend

# In Command Prompt
cd C:\Users\YourName\fullstack-app\backend
gradlew.bat bootRun

# In PowerShell (can use forward or back slashes)
cd C:/Users/YourName/fullstack-app/backend
.\gradlew.bat bootRun
```

---

## 🎨 Windows Terminal (Optional)

For a better experience, use **Windows Terminal**:

1. Install from Microsoft Store: "Windows Terminal"
2. Open Windows Terminal
3. Run commands in separate tabs:

**Tab 1 (Backend):**
```powershell
cd C:\path\to\fullstack-app\backend
.\gradlew.bat bootRun
```

**Tab 2 (Frontend):**
```powershell
cd C:\path\to\fullstack-app\frontend
npm start
```

---

## ✅ Windows Compatibility Checklist

- ✅ Gradle wrapper for Windows (`gradlew.bat`)
- ✅ Backend startup script (`start-backend.bat`)
- ✅ Frontend startup script (`start-frontend.bat`)
- ✅ Combined startup script (`start.bat`)
- ✅ Works on Windows 10 & 11
- ✅ Compatible with Command Prompt
- ✅ Compatible with PowerShell
- ✅ Compatible with Windows Terminal

---

## 🎉 Summary

### Yes, it works perfectly on Windows!

**To run on Windows:**

1. **Install Prerequisites:**
   - Java 17+ from https://adoptium.net/
   - Node.js 18+ from https://nodejs.org/

2. **Navigate to project:**
   ```batch
   cd C:\path\to\fullstack-app
   ```

3. **Double-click `start.bat`** OR run manually:
   ```batch
   # Terminal 1
   cd backend
   gradlew.bat bootRun

   # Terminal 2
   cd frontend
   npm start
   ```

4. **Open browser:**
   - http://localhost:4200

That's it! Your fullstack application runs on Windows just as well as on macOS or Linux! 🚀

---

## 📚 Additional Resources

- **Java Download:** https://adoptium.net/
- **Node.js Download:** https://nodejs.org/
- **Gradle Documentation:** https://docs.gradle.org/
- **Angular Documentation:** https://angular.dev/
- **Spring Boot Documentation:** https://spring.io/projects/spring-boot

---

**Platform Tested:** Windows 10 & 11  
**Status:** ✅ Fully Compatible  
**Last Updated:** February 17, 2026

