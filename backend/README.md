# NomNom Backend - TheMealDB API Integration + Folder Management

Een gestructureerde Spring Boot backend die connecteert met TheMealDB API en gebruikers toestaat om aangepaste folders te maken voor het groeperen van meals.

## Project Structuur

```
src/main/java/com/nomnom/backend/
├── controller/
│   ├── MealController.java           # TheMealDB API endpoints
│   └── FolderController.java         # Folder management endpoints
├── service/
│   ├── MealService.java              # TheMealDB business logic
│   └── FolderService.java            # Folder management business logic
├── dto/
│   ├── MealDto.java                  # TheMealDB API response mapping
│   ├── MealApiResponse.java          # API response wrapper
│   ├── SimpleMealDto.java            # Cleaned response DTO
│   ├── IngredientDto.java            # Ingredient representation
│   ├── FolderDto.java                # Folder response DTO
│   ├── CreateFolderRequest.java      # Create folder request DTO
│   └── AddMealToFolderRequest.java   # Add meal to folder request DTO
├── entity/
│   └── Folder.java                   # Firestore folder entity
├── exception/
│   ├── MealNotFoundException.java     # Custom exception voor niet gevonden meals
│   ├── MealApiException.java         # Custom exception voor API fouten
│   ├── FolderNotFoundException.java  # Custom exception voor niet gevonden folders
│   └── FirestoreException.java       # Custom exception voor database fouten
└── config/
    ├── RestTemplateConfig.java       # HTTP client configuratie
    └── FirebaseConfig.java           # Firebase & Firestore configuratie
```

## API Endpoints

### TheMealDB API Endpoints

#### 1. Zoek recepten op ingrediënt
```
GET /api/meals/ingredient/{ingredient}
```
**Voorbeeld:** `GET /api/meals/ingredient/chicken`

#### 2. Haal recept op basis van ID
```
GET /api/meals/{id}
```
**Voorbeeld:** `GET /api/meals/52770`

#### 3. Krijg willekeurig recept
```
GET /api/meals/random
```

### 🆕 Folder Management Endpoints

#### 1. Maak nieuwe folder
```
POST /api/folders
Headers: X-User-ID: {userId}
```
**Request Body:**
```json
{
  "name": "Winter Gerechten",
  "description": "Warme maaltijden voor de winter"
}
```

#### 2. Haal alle folders van gebruiker
```
GET /api/folders
Headers: X-User-ID: {userId}
```

#### 3. Haal specifieke folder
```
GET /api/folders/{folderId}
Headers: X-User-ID: {userId}
```

#### 4. Update folder
```
PUT /api/folders/{folderId}
Headers: X-User-ID: {userId}
```
**Request Body:**
```json
{
  "name": "Zomer Gerechten",
  "description": "Lichte maaltijden voor warme dagen"
}
```

#### 5. Verwijder folder
```
DELETE /api/folders/{folderId}
Headers: X-User-ID: {userId}
```

#### 6. Voeg meal toe aan folder
```
POST /api/folders/{folderId}/meals
Headers: X-User-ID: {userId}
```
**Request Body:**
```json
{
  "mealId": "52770"
}
```

#### 7. Verwijder meal uit folder
```
DELETE /api/folders/{folderId}/meals/{mealId}
Headers: X-User-ID: {userId}
```

## Response Formats

### Meal Response
```json
{
  "id": "52770",
  "name": "Spaghetti Bolognese",
  "category": "Beef",
  "area": "Italian",
  "instructions": "Put the onion and oil in a large pan...",
  "thumbnail": "https://www.themealdb.com/images/media/meals/sutysw1468247559.jpg",
  "youtubeUrl": "https://www.youtube.com/watch?v=1IszT_guI08",
  "sourceUrl": "https://www.bbcgoodfood.com/recipes/best-spaghetti-bolognese-recipe",
  "ingredients": [
    {
      "name": "onion",
      "measure": "1 large"
    }
  ]
}
```

### Folder Response
```json
{
  "id": "abc123",
  "name": "Winter Gerechten",
  "description": "Warme maaltijden voor de winter",
  "userId": "user123",
  "mealIds": ["52770", "52771"],
  "createdAt": "2025-06-30T14:30:00",
  "updatedAt": "2025-06-30T15:00:00"
}
```

## Error Handling

### TheMealDB Errors
- **404 Not Found:** Meal niet gevonden
- **503 Service Unavailable:** TheMealDB API niet bereikbaar

### Folder Management Errors
- **404 Not Found:** Folder niet gevonden of geen toegang
- **400 Bad Request:** Ongeldige request data
- **500 Internal Server Error:** Database fouten

**Foutmelding format:**
```json
{
  "error": "Folder not found",
  "message": "Folder not found with id: abc123",
  "status": 404
}
```

## Configuratie

### application.properties
```properties
# TheMealDB API Configuration
mealdb.api.url=https://www.themealdb.com/api/json/v1/1

# Server Configuration
server.port=8080
```

### .env bestand (voor Firebase)
```env
FIREBASE_PROJECT_ID=nomnom-f3371
FIREBASE_API_KEY=your-api-key
FIREBASE_AUTH_DOMAIN=nomnom-f3371.firebaseapp.com
FIREBASE_STORAGE_BUCKET=nomnom-f3371.firebasestorage.app
```

## Database Structuur (Firestore)

### Folders Collection
```
folders/
├── {folderId}/
│   ├── name: "Winter Gerechten"
│   ├── description: "Warme maaltijden voor de winter"
│   ├── userId: "user123"
│   ├── mealIds: ["52770", "52771"]
│   ├── createdAt: Timestamp
│   └── updatedAt: Timestamp
```

## Gebruikersscenario's

### 1. Folder Aanmaken
```bash
curl -X POST http://localhost:8080/api/folders \
  -H "Content-Type: application/json" \
  -H "X-User-ID: user123" \
  -d '{"name": "Winter Gerechten", "description": "Warme maaltijden"}'
```

### 2. Meal Toevoegen aan Folder
```bash
curl -X POST http://localhost:8080/api/folders/{folderId}/meals \
  -H "Content-Type: application/json" \
  -H "X-User-ID: user123" \
  -d '{"mealId": "52770"}'
```

### 3. Alle Folders Ophalen
```bash
curl -X GET http://localhost:8080/api/folders \
  -H "X-User-ID: user123"
```

## Starten van de Applicatie

```bash
mvn spring-boot:run
```

De applicatie start op `http://localhost:8080`

## Testing

```bash
# Compile het project
mvn clean compile

# Run tests
mvn test

# Test TheMealDB endpoints
curl http://localhost:8080/api/meals/random
curl http://localhost:8080/api/meals/ingredient/chicken

# Test folder endpoints (vereist X-User-ID header)
curl -H "X-User-ID: test123" http://localhost:8080/api/folders
```

## ✨ Nieuwe Features

### 🆕 **Folder Management System**
- ✅ **Aangepaste folders** maken (bijv. "Winter Gerechten")
- ✅ **Meal groepering** in folders
- ✅ **Firebase Firestore** integratie voor persistence
- ✅ **User isolation** - elke gebruiker ziet alleen eigen folders
- ✅ **CRUD operaties** (Create, Read, Update, Delete)
- ✅ **Meal management** binnen folders
- ✅ **Validation** met Jakarta Bean Validation
- ✅ **Error handling** met specifieke exceptions

### 🛠️ **Technische Implementatie**
- **Entity-DTO pattern** voor clean data handling
- **Firestore integration** met Firebase Admin SDK
- **User-based access control** via headers
- **Comprehensive error handling** 
- **Validation annotations** voor request DTOs
- **Async database operations** met CompletableFuture

## Dependencies

- Spring Boot Web
- Spring Boot Validation
- Spring Boot Test
- Jackson (JSON processing)
- Firebase Admin SDK (includes Firestore)

## Firebase Firestore

De applicatie gebruikt Firebase Firestore voor:
- **Folder opslag** per gebruiker
- **Real-time sync** mogelijkheden
- **Scalable NoSQL database**
- **Automatische indexering**
- **Security rules** (server-side implementatie)

## Beveiliging

- **User ID verificatie** via headers
- **Folder ownership** controle
- **Input validation** op alle endpoints
- **Exception handling** zonder data leakage 