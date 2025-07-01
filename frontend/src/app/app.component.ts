import { Component, OnInit } from "@angular/core";
import { MealService } from './services/meal.service';
import { SimpleMeal, MealDto } from './models/meal.model';
import { AuthService, UserProfile, FavoriteMeal } from './services/auth.service';

@Component({
  selector: "app-root",
  templateUrl: "./app.component.html",
  styleUrls: ["./app.component.css"],
})
export class AppComponent implements OnInit {
  title = "NomNom Frontend";
  searchQuery = "";
  cartCount = 3;
  
  // New properties for backend integration
  isLoading = false;
  errorMessage = "";
  searchResults: SimpleMeal[] = [];
  randomMeal: SimpleMeal | null = null;
  lastSearchTerm = "";
  selectedMeal: MealDto | null = null;
  showMealDetails = false;

  // Authentication state
  showAuthModal = false;
  authMode: 'signin' | 'signup' = 'signin';
  authLoading = false;
  authError = '';
  showUserDropdown = false;
  
  // Auth form data
  authForm = {
    email: '',
    password: '',
    displayName: '',
    confirmPassword: ''
  };

  // User state
  currentUser$ = this.authService.currentUser$;
  userProfile$ = this.authService.userProfile$;
  favorites$ = this.authService.favorites$;

  constructor(
    private mealService: MealService,
    private authService: AuthService
  ) {}

  ngOnInit() {
    // Close dropdown when clicking outside
    document.addEventListener('click', (event) => {
      if (this.showUserDropdown) {
        const target = event.target as HTMLElement;
        if (!target.closest('.user-dropdown-container')) {
          this.showUserDropdown = false;
        }
      }
    });
  }

  quickIngredients = ["Chicken", "Beef", "Pasta", "Rice", "Fish", "Pork"];

  recentSearches = [
    "chicken",
    "pasta",
    "vegetarian",
    "quick meals",
    "desserts",
  ];

  featuredRecipes = [
    {
      id: 1,
      title: "Honey Garlic Chicken",
      difficulty: "Easy",
      time: 25,
      servings: 4,
      rating: 4.8,
      reviews: 234,
      image: "assets/recipe1.jpg",
      ingredients: ["chicken", "honey", "garlic", "+1 more"],
    },
    {
      id: 2,
      title: "Creamy Tomato Basil Pasta",
      difficulty: "Easy",
      time: 20,
      servings: 3,
      rating: 4.9,
      reviews: 189,
      image: "assets/recipe2.jpg",
      ingredients: ["pasta", "tomato", "basil", "+1 more"],
    },
    {
      id: 3,
      title: "Teriyaki Beef Bowl",
      difficulty: "Medium",
      time: 30,
      servings: 2,
      rating: 4.7,
      reviews: 156,
      image: "assets/recipe3.jpg",
      ingredients: ["beef", "rice", "vegetables", "+1 more"],
    },
  ];

  stats = [
    { value: "15,000+", label: "Curated Recipes" },
    { value: "85+", label: "Countries Represented" },
    { value: "3.2M", label: "Meals Created" },
  ];

  onSearch() {
    if (!this.searchQuery.trim()) {
      this.errorMessage = "Please enter an ingredient to search for";
      return;
    }

    this.isLoading = true;
    this.errorMessage = "";
    this.searchResults = [];
    this.randomMeal = null; // Clear random meal when doing new search
    this.lastSearchTerm = this.searchQuery; // Track what was searched

    console.log("Searching for:", this.searchQuery);

    this.mealService.getMealsByIngredient(this.searchQuery).subscribe({
      next: (meals) => {
        this.searchResults = meals;
        this.isLoading = false;
        console.log("Search results:", meals);
        
        // Add to recent searches if not already there
        if (!this.recentSearches.includes(this.searchQuery.toLowerCase())) {
          this.recentSearches.unshift(this.searchQuery.toLowerCase());
          // Keep only last 5 searches
          this.recentSearches = this.recentSearches.slice(0, 5);
        }
      },
      error: (error) => {
        this.errorMessage = error.message;
        this.isLoading = false;
        console.error("Search error:", error);
      }
    });
  }

  onSurpriseMe() {
    this.isLoading = true;
    this.errorMessage = "";
    this.randomMeal = null;
    this.searchResults = []; // Clear search results when getting random meal

    console.log("Getting random meal...");

    this.mealService.getRandomMeal().subscribe({
      next: (meal) => {
        this.randomMeal = meal;
        this.isLoading = false;
        console.log("Random meal:", meal);
      },
      error: (error) => {
        this.errorMessage = error.message;
        this.isLoading = false;
        console.error("Random meal error:", error);
      }
    });
  }

  selectQuickIngredient(ingredient: string) {
    this.searchQuery = ingredient.toLowerCase();
  }

  selectRecentSearch(search: string) {
    this.searchQuery = search;
  }

  // Enhanced Recipe Card Methods
  selectMeal(meal: SimpleMeal) {
    this.selectedMeal = meal;
    console.log('Selected meal:', meal);
  }

  viewRecipeDetails(meal: SimpleMeal) {
    // Get full meal details by ID
    this.mealService.getMealById(meal.id).subscribe({
      next: (fullMeal) => {
        this.selectedMeal = fullMeal;
        this.showMealDetails = true;
        console.log('Full meal details:', fullMeal);
      },
      error: (error) => {
        console.error('Error fetching meal details:', error);
        this.errorMessage = 'Failed to load recipe details';
      }
    });
  }

  async addToFavorites(meal: SimpleMeal | MealDto) {
    if (!this.authService.isLoggedIn) {
      this.openAuthModal('signin');
      return;
    }

    try {
      await this.authService.addToFavorites(meal);
      this.errorMessage = '';
      console.log('Added to favorites:', meal.name);
      // Could show a success toast here
    } catch (error: any) {
      this.errorMessage = error.message;
      console.error('Error adding to favorites:', error);
    }
  }

  closeMealDetails() {
    this.showMealDetails = false;
    this.selectedMeal = null;
  }

  // Utility methods for enhanced display
  getEstimatedTime(): number {
    // Return random cooking time between 15-60 minutes
    return Math.floor(Math.random() * 45) + 15;
  }

  getRandomDifficulty(): string {
    const difficulties = ['Easy', 'Medium', 'Hard'];
    return difficulties[Math.floor(Math.random() * difficulties.length)];
  }

  getRandomRating(): string {
    // Return random rating between 4.0 and 5.0
    const rating = (Math.random() + 4).toFixed(1);
    return rating;
  }

  // Enhanced Random Meal Methods
  viewRandomMealDetails() {
    if (this.randomMeal) {
      // Convert SimpleMeal to MealDto for the modal
      this.selectedMeal = {
        id: this.randomMeal.id,
        name: this.randomMeal.name,
        thumbnail: this.randomMeal.thumbnail,
        instructions: this.randomMeal.instructions,
        ingredients: this.randomMeal.ingredients
      };
      this.showMealDetails = true;
      console.log('Viewing random meal details:', this.selectedMeal);
    }
  }

  getAnotherSurprise() {
    this.onSurpriseMe(); // Reuse the existing surprise me functionality
  }

  // Additional utility methods for enhanced UI
  randomServings(): number {
    // Return random serving count between 2-6
    return Math.floor(Math.random() * 5) + 2;
  }

  formatInstructions(instructions: string): string {
    if (!instructions) return '';
    
    // Split instructions into steps and format them nicely
    const steps = instructions.split(/\d+\.|\n/).filter(step => step.trim().length > 0);
    
    let formattedHtml = '';
    steps.forEach((step, index) => {
      if (step.trim().length > 10) { // Only include substantial steps
        formattedHtml += `
          <div class="flex items-start space-x-4 mb-6 p-4 bg-gray-50 rounded-lg hover:bg-gray-100 transition-colors duration-200">
            <div class="flex-shrink-0 w-8 h-8 bg-gradient-to-br from-blue-500 to-purple-500 rounded-full flex items-center justify-center text-white font-bold text-sm">
              ${index + 1}
            </div>
            <div class="flex-1">
              <p class="text-gray-800 leading-relaxed">${step.trim()}</p>
            </div>
          </div>
        `;
      }
    });
    
    return formattedHtml || `<p class="text-gray-700 leading-relaxed whitespace-pre-line">${instructions}</p>`;
  }

  printRecipe() {
    if (this.selectedMeal) {
      // Basic print functionality - opens print dialog
      window.print();
      console.log('Printing recipe:', this.selectedMeal.name);
    }
  }

  // Authentication Methods
  openAuthModal(mode: 'signin' | 'signup') {
    this.authMode = mode;
    this.showAuthModal = true;
    this.authError = '';
    this.resetAuthForm();
  }

  closeAuthModal() {
    this.showAuthModal = false;
    this.authError = '';
    this.authLoading = false;
    this.resetAuthForm();
  }

  switchAuthMode() {
    this.authMode = this.authMode === 'signin' ? 'signup' : 'signin';
    this.authError = '';
    this.resetAuthForm();
  }

  async onAuthSubmit() {
    if (this.authLoading) return;

    // Basic validation
    if (!this.authForm.email || !this.authForm.password) {
      this.authError = 'Please fill in all required fields';
      return;
    }

    if (this.authMode === 'signup') {
      if (!this.authForm.displayName) {
        this.authError = 'Please enter your display name';
        return;
      }
      if (this.authForm.password !== this.authForm.confirmPassword) {
        this.authError = 'Passwords do not match';
        return;
      }
    }

    this.authLoading = true;
    this.authError = '';

    try {
      if (this.authMode === 'signin') {
        await this.authService.signIn(this.authForm.email, this.authForm.password);
      } else {
        await this.authService.signUp(this.authForm.email, this.authForm.password, this.authForm.displayName);
      }
      this.closeAuthModal();
    } catch (error: any) {
      this.authError = error.message;
    } finally {
      this.authLoading = false;
    }
  }

  async signInWithGoogle() {
    if (this.authLoading) return;

    this.authLoading = true;
    this.authError = '';

    try {
      await this.authService.signInWithGoogle();
      this.closeAuthModal();
    } catch (error: any) {
      this.authError = error.message;
    } finally {
      this.authLoading = false;
    }
  }

  async signOut() {
    try {
      await this.authService.signOut();
      this.errorMessage = '';
    } catch (error: any) {
      this.errorMessage = error.message;
    }
  }

  async removeFromFavorites(mealId: string) {
    try {
      await this.authService.removeFromFavorites(mealId);
      this.errorMessage = '';
    } catch (error: any) {
      this.errorMessage = error.message;
    }
  }

  async isFavorite(mealId: string): Promise<boolean> {
    return await this.authService.checkIfFavorite(mealId);
  }

  private resetAuthForm() {
    this.authForm = {
      email: '',
      password: '',
      displayName: '',
      confirmPassword: ''
    };
  }

  // User Menu Methods
  toggleUserDropdown() {
    this.showUserDropdown = !this.showUserDropdown;
  }

  closeUserDropdown() {
    this.showUserDropdown = false;
  }

  getUserProfilePicture(): string {
    return this.authService.getUserProfilePicture();
  }

  getUserDisplayName(): string {
    return this.authService.getUserDisplayName();
  }

  getDefaultProfileUrl(): string {
    const name = encodeURIComponent(this.getUserDisplayName());
    return `https://ui-avatars.com/api/?name=${name}&background=4F46E5&color=ffffff&size=200`;
  }

  onImageError(event: Event): void {
    const target = event.target as HTMLImageElement;
    if (target) {
      target.src = this.getDefaultProfileUrl();
    }
  }

  // UI Helper Methods
  showFavorites() {
    this.closeUserDropdown();
    // TODO: Implement favorites view
    console.log('Showing favorites');
  }

  showProfile() {
    this.closeUserDropdown();
    // TODO: Implement profile view
    console.log('Showing profile');
  }

  async signOutUser() {
    try {
      await this.authService.signOut();
      this.closeUserDropdown();
      this.errorMessage = '';
    } catch (error: any) {
      this.errorMessage = error.message;
    }
  }
}
