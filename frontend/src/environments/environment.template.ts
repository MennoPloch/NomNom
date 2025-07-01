// Template file - Copy this to environment.ts and fill in your actual Firebase values
// DO NOT commit environment.ts to git!

export const environment = {
  production: false,
  apiUrl: "http://localhost:8080/api",
  firebase: {
    apiKey: "your-firebase-api-key",
    authDomain: "your-project-id.firebaseapp.com",
    databaseURL: "https://your-project-id-default-rtdb.firebaseio.com",
    projectId: "your-project-id",
    storageBucket: "your-project-id.appspot.com",
    messagingSenderId: "your-messaging-sender-id",
    appId: "your-app-id"
  }
};

// Instructions:
// 1. Go to https://console.firebase.google.com/
// 2. Create or select your project
// 3. Go to Project Settings > General > Your apps
// 4. Click on the web app or create one
// 5. Copy the config values from the Firebase SDK snippet
// 6. Replace the values above with your actual Firebase config 