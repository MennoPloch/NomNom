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

2. **Setup Environment Configuration:**

   Run the interactive setup script to configure Firebase:

   ```bash
   npm run setup-env
   ```

   This will:
   - Guide you through Firebase configuration
   - Create your local environment files
   - Keep your credentials secure (not committed to git)

   **Alternative manual setup:**
   
   Copy the template files and fill in your Firebase credentials:
   ```bash
   cp src/environments/environment.template.ts src/environments/environment.ts
   cp src/environments/environment.prod.template.ts src/environments/environment.prod.ts
   ```
   
   Then edit the files with your actual Firebase configuration.

3. Start the development server:

   ```bash
   npm start
   ```

   or

   ```bash
   npm run serve
   ```

   The application will be available at `http://localhost:4200`

4. Build for production:
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
│   ├── environment.template.ts    # Development template (committed)
│   ├── environment.prod.template.ts # Production template (committed)
│   ├── environment.ts         # Development environment (NOT committed)
│   └── environment.prod.ts    # Production environment (NOT committed)
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

### Environment Configuration

This project uses a **secure environment configuration** approach:

- **Template files** (`*.template.ts`) are committed to git and show the required structure
- **Actual environment files** (`environment.ts`, `environment.prod.ts`) are **NOT committed** and contain your real credentials
- Use `npm run setup-env` for guided setup

### Security Best Practices

✅ **Do:**
- Use the setup script or copy from templates
- Keep your Firebase credentials secure
- Use different Firebase projects for dev/prod
- Review `.gitignore` to ensure credentials aren't committed

❌ **Don't:**
- Commit real Firebase credentials to git
- Share your `environment.ts` files
- Use production credentials in development

### Firebase Setup

1. Go to [Firebase Console](https://console.firebase.google.com/)
2. Create a new project (or use existing)
3. Enable Authentication → Email/Password
4. Create Firestore database
5. Add a web app to get your config
6. Run `npm run setup-env` and paste your config
