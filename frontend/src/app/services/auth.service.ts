import { Injectable } from '@angular/core';
import { Auth, signInWithEmailAndPassword, createUserWithEmailAndPassword, signOut, User, onAuthStateChanged, updateProfile, GoogleAuthProvider, signInWithPopup } from '@angular/fire/auth';
import { Firestore, doc, setDoc, getDoc, collection, addDoc, deleteDoc, query, where, getDocs } from '@angular/fire/firestore';
import { BehaviorSubject, Observable } from 'rxjs';
import { SimpleMeal, MealDto } from '../models/meal.model';

export interface UserProfile {
  uid: string;
  email: string;
  displayName: string;
  photoURL?: string;
  createdAt: Date;
  favoriteCount: number;
}

export interface FavoriteMeal {
  id: string;
  mealId: string;
  userId: string;
  mealName: string;
  mealThumbnail: string;
  dateAdded: Date;
  category?: string;
  area?: string;
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private currentUserSubject = new BehaviorSubject<User | null>(null);
  private userProfileSubject = new BehaviorSubject<UserProfile | null>(null);
  private favoritesSubject = new BehaviorSubject<FavoriteMeal[]>([]);
  
  currentUser$ = this.currentUserSubject.asObservable();
  userProfile$ = this.userProfileSubject.asObservable();
  favorites$ = this.favoritesSubject.asObservable();
  
  constructor(
    private auth: Auth,
    private firestore: Firestore
  ) {
    // Listen for authentication state changes
    onAuthStateChanged(this.auth, (user) => {
      this.currentUserSubject.next(user);
      if (user) {
        this.loadUserProfile(user.uid);
        this.loadUserFavorites(user.uid);
      } else {
        this.userProfileSubject.next(null);
        this.favoritesSubject.next([]);
      }
    });
  }

  // Authentication Methods
  async signUp(email: string, password: string, displayName: string): Promise<User> {
    try {
      const credential = await createUserWithEmailAndPassword(this.auth, email, password);
      const user = credential.user;

      // Update user profile with display name
      await updateProfile(user, { displayName });

      // Create user profile in Firestore
      await this.createUserProfile(user, displayName);

      return user;
    } catch (error: any) {
      throw new Error(this.getErrorMessage(error.code));
    }
  }

  async signIn(email: string, password: string): Promise<User> {
    try {
      const credential = await signInWithEmailAndPassword(this.auth, email, password);
      return credential.user;
    } catch (error: any) {
      throw new Error(this.getErrorMessage(error.code));
    }
  }

  async signInWithGoogle(): Promise<User> {
    try {
      const provider = new GoogleAuthProvider();
      provider.addScope('profile');
      provider.addScope('email');
      
      const credential = await signInWithPopup(this.auth, provider);
      const user = credential.user;

      // Check if user profile exists, create if not
      const userDoc = await getDoc(doc(this.firestore, 'users', user.uid));
      if (!userDoc.exists()) {
        await this.createUserProfile(user, user.displayName || 'User');
      }

      return user;
    } catch (error: any) {
      throw new Error(this.getErrorMessage(error.code));
    }
  }

  async signOut(): Promise<void> {
    try {
      await signOut(this.auth);
    } catch (error: any) {
      throw new Error('Failed to sign out');
    }
  }

  // User Profile Methods
  private async createUserProfile(user: User, displayName: string): Promise<void> {
    const userProfile: UserProfile = {
      uid: user.uid,
      email: user.email || '',
      displayName: displayName,
      photoURL: user.photoURL || this.getDefaultProfilePicture(displayName),
      createdAt: new Date(),
      favoriteCount: 0
    };

    await setDoc(doc(this.firestore, 'users', user.uid), userProfile);
    this.userProfileSubject.next(userProfile);
  }

  private getDefaultProfilePicture(displayName: string): string {
    // Generate a default profile picture using UI Avatars service
    const name = encodeURIComponent(displayName || 'User');
    const colors = ['FF6B6B', '4ECDC4', '45B7D1', '96CEB4', 'FFEAA7', 'DDA0DD', 'F0A07C', '98D8C8'];
    const randomColor = colors[Math.floor(Math.random() * colors.length)];
    return `https://ui-avatars.com/api/?name=${name}&background=${randomColor}&color=ffffff&size=200&font-size=0.6&bold=true&format=png`;
  }

  private async loadUserProfile(uid: string): Promise<void> {
    try {
      const userDoc = await getDoc(doc(this.firestore, 'users', uid));
      if (userDoc.exists()) {
        this.userProfileSubject.next(userDoc.data() as UserProfile);
      }
    } catch (error) {
      console.error('Error loading user profile:', error);
    }
  }

  async updateUserProfile(updates: Partial<UserProfile>): Promise<void> {
    const currentUser = this.currentUserSubject.value;
    if (!currentUser) throw new Error('No user logged in');

    try {
      await setDoc(doc(this.firestore, 'users', currentUser.uid), updates, { merge: true });
      
      // Update local state
      const currentProfile = this.userProfileSubject.value;
      if (currentProfile) {
        this.userProfileSubject.next({ ...currentProfile, ...updates });
      }
    } catch (error) {
      throw new Error('Failed to update profile');
    }
  }

  // Favorites Methods
  async addToFavorites(meal: SimpleMeal | MealDto): Promise<void> {
    const currentUser = this.currentUserSubject.value;
    if (!currentUser) throw new Error('Please sign in to add favorites');

    try {
      // Check if already in favorites
      const existingFavorite = await this.checkIfFavorite(meal.id);
      if (existingFavorite) {
        throw new Error('Recipe is already in your favorites');
      }

      const favoriteMeal: Omit<FavoriteMeal, 'id'> = {
        mealId: meal.id,
        userId: currentUser.uid,
        mealName: meal.name,
        mealThumbnail: meal.thumbnail,
        dateAdded: new Date(),
        category: (meal as MealDto).category,
        area: (meal as MealDto).area
      };

      const docRef = await addDoc(collection(this.firestore, 'favorites'), favoriteMeal);
      
      // Add to local favorites list
      const newFavorite: FavoriteMeal = { id: docRef.id, ...favoriteMeal };
      const currentFavorites = this.favoritesSubject.value;
      this.favoritesSubject.next([...currentFavorites, newFavorite]);

      // Update favorite count
      await this.updateFavoriteCount(currentUser.uid, currentFavorites.length + 1);

    } catch (error) {
      throw error;
    }
  }

  async removeFromFavorites(mealId: string): Promise<void> {
    const currentUser = this.currentUserSubject.value;
    if (!currentUser) throw new Error('Please sign in to manage favorites');

    try {
      const favorite = await this.getFavoriteDocument(mealId, currentUser.uid);
      if (favorite) {
        await deleteDoc(doc(this.firestore, 'favorites', favorite.id));
        
        // Remove from local favorites list
        const currentFavorites = this.favoritesSubject.value;
        const updatedFavorites = currentFavorites.filter(f => f.mealId !== mealId);
        this.favoritesSubject.next(updatedFavorites);

        // Update favorite count
        await this.updateFavoriteCount(currentUser.uid, updatedFavorites.length);
      }
    } catch (error) {
      throw new Error('Failed to remove from favorites');
    }
  }

  async checkIfFavorite(mealId: string): Promise<boolean> {
    const currentUser = this.currentUserSubject.value;
    if (!currentUser) return false;

    const currentFavorites = this.favoritesSubject.value;
    return currentFavorites.some(f => f.mealId === mealId);
  }

  private async getFavoriteDocument(mealId: string, userId: string): Promise<FavoriteMeal | null> {
    try {
      const q = query(
        collection(this.firestore, 'favorites'),
        where('mealId', '==', mealId),
        where('userId', '==', userId)
      );
      
      const querySnapshot = await getDocs(q);
      if (!querySnapshot.empty) {
        const doc = querySnapshot.docs[0];
        return { id: doc.id, ...doc.data() } as FavoriteMeal;
      }
      return null;
    } catch (error) {
      console.error('Error getting favorite document:', error);
      return null;
    }
  }

  private async loadUserFavorites(userId: string): Promise<void> {
    try {
      const q = query(
        collection(this.firestore, 'favorites'),
        where('userId', '==', userId)
      );
      
      const querySnapshot = await getDocs(q);
      const favorites: FavoriteMeal[] = [];
      
      querySnapshot.forEach((doc) => {
        favorites.push({ id: doc.id, ...doc.data() } as FavoriteMeal);
      });

      this.favoritesSubject.next(favorites);
    } catch (error) {
      console.error('Error loading favorites:', error);
    }
  }

  private async updateFavoriteCount(userId: string, count: number): Promise<void> {
    try {
      await setDoc(doc(this.firestore, 'users', userId), { favoriteCount: count }, { merge: true });
      
      // Update local user profile
      const currentProfile = this.userProfileSubject.value;
      if (currentProfile) {
        this.userProfileSubject.next({ ...currentProfile, favoriteCount: count });
      }
    } catch (error) {
      console.error('Error updating favorite count:', error);
    }
  }

  // Utility Methods
  get isLoggedIn(): boolean {
    return !!this.currentUserSubject.value;
  }

  get currentUser(): User | null {
    return this.currentUserSubject.value;
  }

  get userProfile(): UserProfile | null {
    return this.userProfileSubject.value;
  }

  get favorites(): FavoriteMeal[] {
    return this.favoritesSubject.value;
  }

  getUserProfilePicture(): string {
    const user = this.currentUserSubject.value;
    const profile = this.userProfileSubject.value;
    
    // Priority: Google photo > stored profile photo > default
    if (user?.photoURL) {
      return user.photoURL;
    } else if (profile?.photoURL) {
      return profile.photoURL;
    } else {
      return this.getDefaultProfilePicture(profile?.displayName || 'User');
    }
  }

  getUserDisplayName(): string {
    const user = this.currentUserSubject.value;
    const profile = this.userProfileSubject.value;
    
    return profile?.displayName || user?.displayName || user?.email || 'User';
  }

  private getErrorMessage(errorCode: string): string {
    switch (errorCode) {
      case 'auth/user-not-found':
        return 'No user found with this email address.';
      case 'auth/wrong-password':
        return 'Incorrect password.';
      case 'auth/email-already-in-use':
        return 'An account with this email already exists.';
      case 'auth/weak-password':
        return 'Password should be at least 6 characters.';
      case 'auth/invalid-email':
        return 'Invalid email address.';
      case 'auth/too-many-requests':
        return 'Too many failed attempts. Please try again later.';
      case 'auth/popup-closed-by-user':
        return 'Sign-in was cancelled.';
      case 'auth/popup-blocked':
        return 'Pop-up was blocked by your browser. Please allow pop-ups and try again.';
      case 'auth/cancelled-popup-request':
        return 'Sign-in was cancelled.';
      case 'auth/account-exists-with-different-credential':
        return 'An account already exists with the same email but different sign-in credentials.';
      default:
        return 'An error occurred. Please try again.';
    }
  }
} 