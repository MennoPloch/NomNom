# 🔥 Firebase Setup Guide for NomNom

## Quick Setup (5 minutes)

### 1. Create Firebase Project
1. Go to [Firebase Console](https://console.firebase.google.com/)
2. Click "Create a project"
3. Name it "nomnom-app" (or your preferred name)
4. Enable Google Analytics (optional)

### 2. Setup Authentication
1. In Firebase Console, go to **Authentication** > **Sign-in method**
2. Enable **Email/Password** authentication
3. Optionally enable **Google** sign-in for social login

### 3. Setup Firestore Database
1. Go to **Firestore Database** > **Create database**
2. Choose **Start in test mode** (for development)
3. Select your preferred location

### 4. Get Firebase Config
1. Go to **Project Settings** (gear icon)
2. Scroll down to "Your apps" section
3. Click "Add app" > Web app icon (`</>`)
4. Register your app with name "nomnom-frontend"
5. Copy the Firebase configuration object

### 5. Update Environment Files

**frontend/src/environments/environment.ts:**
```typescript
export const environment = {
  production: false,
  apiUrl: "http://localhost:8080/api",
  firebase: {
    apiKey: "your-api-key-here",
    authDomain: "your-project.firebaseapp.com",
    projectId: "your-project-id",
    storageBucket: "your-project.appspot.com",
    messagingSenderId: "123456789",
    appId: "your-app-id"
  }
};
```

**frontend/src/environments/environment.prod.ts:**
```typescript
export const environment = {
  production: true,
  apiUrl: "/api",
  firebase: {
    // Same config as above for production
  }
};
```

### 6. Update Backend (Optional)
If you want to use Firebase Admin SDK in the backend:

1. Go to **Project Settings** > **Service accounts**
2. Click "Generate new private key"
3. Save the JSON file securely
4. Set environment variables in your backend

## 🚀 Features Included

✅ **User Authentication**
- Email/Password sign up and sign in
- User profile management
- Secure session handling

✅ **Favorites System**
- Save recipes to favorites
- Real-time favorites sync
- Favorite count tracking

✅ **Firestore Integration**
- User profiles stored in Firestore
- Favorites collection
- Real-time data updates

## 🧪 Demo Mode

The app includes demo Firebase configuration for testing. To use with real Firebase:

1. Replace the demo config in environment files
2. Restart the development server
3. Create an account and test the features!

## 🔒 Security Rules

For production, update your Firestore security rules:

```javascript
rules_version = '2';
service cloud.firestore {
  match /databases/{database}/documents {
    // Users can read/write their own profile
    match /users/{userId} {
      allow read, write: if request.auth != null && request.auth.uid == userId;
    }
    
    // Users can read/write their own favorites
    match /favorites/{document} {
      allow read, write: if request.auth != null && request.auth.uid == resource.data.userId;
      allow create: if request.auth != null && request.auth.uid == request.resource.data.userId;
    }
  }
}
```

## 📱 What You Get

- **Beautiful Authentication UI** with sign in/up modals
- **User Profile Management** with avatar and stats
- **Real-time Favorites** with instant updates  
- **Responsive Design** works on all devices
- **Production Ready** with proper error handling

Happy coding! 🍽️✨ 