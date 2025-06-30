// Template file - Copy this to environment.prod.ts and fill in your actual Firebase values
// DO NOT commit environment.prod.ts to git!

export const environment = {
  production: true,
  apiUrl: "/api", // Relative URL for production (served from same domain)
  firebase: {
    apiKey: "your-production-firebase-api-key",
    authDomain: "your-production-project-id.firebaseapp.com",
    databaseURL: "https://your-production-project-id-default-rtdb.firebaseio.com",
    projectId: "your-production-project-id",
    storageBucket: "your-production-project-id.appspot.com",
    messagingSenderId: "your-production-messaging-sender-id",
    appId: "your-production-app-id"
  }
};

// Instructions for Production:
// 1. Use a separate Firebase project for production
// 2. Configure proper Firebase Security Rules
// 3. Set up proper CORS settings
// 4. Use environment variables in your CI/CD pipeline
// 5. Never commit production credentials to git 