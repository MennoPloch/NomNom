import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, retry } from 'rxjs/operators';
import { environment } from '../../environments/environment';
import { SimpleMeal, MealDto } from '../models/meal.model';

@Injectable({
  providedIn: 'root'
})
export class MealService {
  private apiUrl = environment.apiUrl;

  constructor(private http: HttpClient) { }

  /**
   * Get a random meal from the API
   * Corresponds to: GET /api/meals/random
   */
  getRandomMeal(): Observable<SimpleMeal> {
    return this.http.get<SimpleMeal>(`${this.apiUrl}/meals/random`)
      .pipe(
        retry(1),
        catchError(this.handleError)
      );
  }

  /**
   * Search for meals by ingredient
   * Corresponds to: GET /api/meals/ingredient/{ingredient}
   */
  getMealsByIngredient(ingredient: string): Observable<SimpleMeal[]> {
    if (!ingredient || ingredient.trim() === '') {
      return throwError(() => new Error('Ingredient cannot be empty'));
    }
    
    const encodedIngredient = encodeURIComponent(ingredient.trim());
    return this.http.get<SimpleMeal[]>(`${this.apiUrl}/meals/ingredient/${encodedIngredient}`)
      .pipe(
        retry(1),
        catchError(this.handleError)
      );
  }

  /**
   * Get a specific meal by its ID
   * Corresponds to: GET /api/meals/{id}
   */
  getMealById(id: string): Observable<MealDto> {
    if (!id || id.trim() === '') {
      return throwError(() => new Error('Meal ID cannot be empty'));
    }

    return this.http.get<MealDto>(`${this.apiUrl}/meals/${id}`)
      .pipe(
        retry(1),
        catchError(this.handleError)
      );
  }

  /**
   * Handle HTTP errors
   */
  private handleError(error: HttpErrorResponse): Observable<never> {
    let errorMessage = 'An unknown error occurred';
    
    if (error.error instanceof ErrorEvent) {
      // Client-side error
      errorMessage = `Client Error: ${error.error.message}`;
    } else {
      // Server-side error
      switch (error.status) {
        case 0:
          errorMessage = 'Unable to connect to the server. Please check if the backend is running.';
          break;
        case 404:
          errorMessage = 'No meals found for your search. Try complete ingredient names like "chicken", "beef", "pasta", or "rice".';
          break;
        case 500:
          errorMessage = 'Server error occurred. Please try again later.';
          break;
        default:
          errorMessage = `Server Error: ${error.status} - ${error.message}`;
      }
    }
    
    console.error('Meal Service Error:', errorMessage, error);
    return throwError(() => new Error(errorMessage));
  }
} 