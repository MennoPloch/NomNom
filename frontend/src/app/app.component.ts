import { Component } from "@angular/core";

@Component({
  selector: "app-root",
  templateUrl: "./app.component.html",
  styleUrls: ["./app.component.css"],
})
export class AppComponent {
  title = "NomNom Frontend";
  searchQuery = "";
  cartCount = 3;

  quickIngredients = ["Chicken", "Pasta", "Vegetables", "Rice", "Fish", "Eggs"];

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
    console.log("Searching for:", this.searchQuery);
  }

  onSurpriseMe() {
    console.log("Surprise me clicked!");
  }

  selectQuickIngredient(ingredient: string) {
    this.searchQuery = ingredient.toLowerCase();
  }

  selectRecentSearch(search: string) {
    this.searchQuery = search;
  }
}
