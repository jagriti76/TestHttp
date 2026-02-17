# Fullstack Application Template

A full-stack application template with Angular 21 frontend and Spring Boot 3.4 backend.

**✅ Cross-Platform:** Works on macOS, Linux, and Windows!  
**📚 Windows Users:** See [WINDOWS_GUIDE.md](WINDOWS_GUIDE.md) for detailed Windows setup instructions.

## Project Structure

```
fullstack-app/
├── backend/          # Spring Boot 3.4 Backend
│   ├── src/
│   │   └── main/
│   │       ├── java/
│   │       │   └── com/example/fullstack/
│   │       │       ├── FullstackApplication.java
│   │       │       └── controller/
│   │       │           └── GreetingController.java
│   │       └── resources/
│   │           └── application.properties
│   ├── build.gradle
│   ├── settings.gradle
│   └── gradlew
│
└── frontend/         # Angular 21 Frontend
    ├── src/
    │   └── app/
    │       ├── services/
    │       │   └── greeting.service.ts
    │       ├── app.component.ts
    │       ├── app.component.html
    │       ├── app.component.css
    │       └── app.config.ts
    └── package.json
```

## Features

- **Backend**: Spring Boot REST API with CORS enabled
- **Frontend**: Angular 21 with standalone components
- **API Call**: Frontend makes HTTP GET request to backend endpoint
- **Response**: Backend returns "hi jagriti" string displayed on homepage

## Prerequisites

- Java 17 or higher
- Java 17 or higher
- Node.js 18+ and npm
- Angular CLI 19+

## Getting Started

### Backend Setup

1. Navigate to the backend directory:
```bash
cd backend
```

2. Run the Spring Boot application:

**Unix/Mac/Linux:**
```bash
./gradlew bootRun
```

**Windows (Command Prompt):**
```batch
gradlew.bat bootRun
```

**Or use the startup scripts:**
- Unix/Mac: `./start-backend.sh`
- Windows: `start-backend.bat`

The backend will start on `http://localhost:8080`

### Frontend Setup

1. Navigate to the frontend directory:
```bash
cd frontend
```

2. Install dependencies:
```bash
npm install
```

3. Start the development server:
```bash
npm start
```

Or:
```bash
ng serve
```

The frontend will start on `http://localhost:4200`

## API Endpoints

### Backend API

- **GET** `/api/greeting` - Returns greeting message "hi jagriti"
  - Response: Plain text string

## Architecture

### Backend
- **Controller**: `GreetingController` handles HTTP requests
- **CORS**: Configured to allow requests from `http://localhost:4200`
- **Port**: Runs on port 8080

### Frontend
- **Service**: `GreetingService` makes HTTP calls to backend
- **Component**: `AppComponent` displays the greeting message
- **Styling**: Modern gradient design with animations

## How It Works

1. Angular app loads and `AppComponent` initializes
2. `ngOnInit()` calls `GreetingService.getGreeting()`
3. Service makes HTTP GET request to `http://localhost:8080/api/greeting`
4. Spring Boot backend returns "hi jagriti" string
5. Frontend displays the message on the homepage with styling

## Testing

### Test Backend
```bash
curl http://localhost:8080/api/greeting
```

Expected output: `hi jagriti`

### Test Frontend
Open browser at `http://localhost:4200` and you should see the greeting message displayed in a styled card.

## Technology Stack

- **Frontend**:
  - Angular 21
  - TypeScript
  - RxJS
  - CSS3

- **Backend**:
  - Spring Boot 3.4.2
  - Java 17
  - Gradle
  - Spring Web

## Development Notes

- Frontend runs on port 4200
- Backend runs on port 8080
- CORS is enabled for local development
- Both servers must be running for the app to work properly

## License

MIT License

