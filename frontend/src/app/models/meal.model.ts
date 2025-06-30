export interface Ingredient {
  name: string;
  measure: string;
}

export interface SimpleMeal {
  id: string;
  name: string;
  thumbnail: string;
  instructions?: string;
  ingredients?: Ingredient[];
}

// Full meal details matching backend MealDto
export interface MealDto {
  id: string;
  name: string;
  thumbnail: string;
  instructions?: string;
  ingredients?: Ingredient[];
  category?: string;
  area?: string;
  youtubeUrl?: string;
  sourceUrl?: string;
}

export interface MealSearchResult {
  meals: SimpleMeal[];
}

// API Response interfaces to match backend structure
export interface ApiResponse<T> {
  data: T;
  message?: string;
  status?: number;
} 