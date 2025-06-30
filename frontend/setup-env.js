const fs = require('fs');
const path = require('path');
const readline = require('readline');

const rl = readline.createInterface({
  input: process.stdin,
  output: process.stdout
});

const environmentsDir = path.join(__dirname, 'src', 'environments');

async function askQuestion(question) {
  return new Promise((resolve) => {
    rl.question(question, (answer) => {
      resolve(answer);
    });
  });
}

async function setupEnvironment() {
  console.log('🔥 Firebase Environment Setup');
  console.log('===============================\n');
  
  console.log('First, make sure you have:');
  console.log('1. Created a Firebase project at https://console.firebase.google.com/');
  console.log('2. Enabled Authentication (Email/Password)');
  console.log('3. Created a Firestore database');
  console.log('4. Added a web app to your project\n');
  
  const proceed = await askQuestion('Ready to continue? (y/n): ');
  if (proceed.toLowerCase() !== 'y') {
    console.log('Setup cancelled.');
    rl.close();
    return;
  }
  
  console.log('\nEnter your Firebase configuration:');
  const apiKey = await askQuestion('API Key: ');
  const authDomain = await askQuestion('Auth Domain: ');
  const databaseURL = await askQuestion('Database URL (optional): ');
  const projectId = await askQuestion('Project ID: ');
  const storageBucket = await askQuestion('Storage Bucket: ');
  const messagingSenderId = await askQuestion('Messaging Sender ID: ');
  const appId = await askQuestion('App ID: ');
  
  // Create development environment
  const devConfig = `export const environment = {
  production: false,
  apiUrl: "http://localhost:8080/api",
  firebase: {
    apiKey: "${apiKey}",
    authDomain: "${authDomain}",
    ${databaseURL ? `databaseURL: "${databaseURL}",` : ''}
    projectId: "${projectId}",
    storageBucket: "${storageBucket}",
    messagingSenderId: "${messagingSenderId}",
    appId: "${appId}"
  }
};
`;

  // Create production environment (same for now, but can be different)
  const prodConfig = `export const environment = {
  production: true,
  apiUrl: "/api",
  firebase: {
    apiKey: "${apiKey}",
    authDomain: "${authDomain}",
    ${databaseURL ? `databaseURL: "${databaseURL}",` : ''}
    projectId: "${projectId}",
    storageBucket: "${storageBucket}",
    messagingSenderId: "${messagingSenderId}",
    appId: "${appId}"
  }
};
`;

  // Write files
  fs.writeFileSync(path.join(environmentsDir, 'environment.ts'), devConfig);
  fs.writeFileSync(path.join(environmentsDir, 'environment.prod.ts'), prodConfig);
  
  console.log('\n✅ Environment files created successfully!');
  console.log('📁 Files created:');
  console.log('   - src/environments/environment.ts');
  console.log('   - src/environments/environment.prod.ts');
  console.log('\n🔒 These files are now excluded from git.');
  console.log('🚀 You can now run: npm start');
  
  rl.close();
}

setupEnvironment().catch(console.error); 