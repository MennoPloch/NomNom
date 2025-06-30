# NomNom Frontend

This is the Angular frontend for the NomNom application, built with Angular 17 and Tailwind CSS.

## Prerequisites

- Node.js (v20.10.0 or higher)
- npm (v10.2.3 or higher)

## Getting Started

1. Install dependencies:

   ```bash
   npm install
   ```

2. Start the development server:

   ```bash
   npm start
   ```

   or

   ```bash
   npm run serve
   ```

   The application will be available at `http://localhost:4200`

3. Build for production:
   ```bash
   npm run build
   ```

## Project Structure

```
src/
├── app/
│   ├── app.component.ts       # Main app component
│   ├── app.component.html     # Main app template
│   ├── app.component.css      # Main app styles
│   ├── app.module.ts          # App module
│   └── app-routing.module.ts  # Routing configuration
├── assets/                    # Static assets
├── environments/              # Environment configurations
│   ├── environment.ts         # Development environment
│   └── environment.prod.ts    # Production environment
├── index.html                 # Main HTML file
├── main.ts                    # Application bootstrap
├── polyfills.ts              # Browser polyfills
└── styles.css                # Global styles with Tailwind CSS
```

## Features

- Angular 17 with standalone components support
- Tailwind CSS for styling
- Routing configured
- Environment-specific configurations
- Production-ready build configuration

## Development

The backend API is expected to run on `http://localhost:8080/api` in development mode.

To modify the API URL, update the `apiUrl` in the environment files:

- `src/environments/environment.ts` (development)
- `src/environments/environment.prod.ts` (production)
