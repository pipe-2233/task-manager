import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { environment } from '../../environments/environment';
import { 
  User, 
  CreateUserRequest, 
  UpdateUserRequest, 
  PasswordChangeRequest, 
  UserStatusRequest 
} from '../models/user.model';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private apiUrl = `${environment.apiUrl}/users`;

  constructor(private http: HttpClient) {}

  // Obtener todos los usuarios
  getAllUsers(): Observable<User[]> {
    return this.http.get<User[]>(this.apiUrl).pipe(
      catchError(this.handleError)
    );
  }

  // Obtener usuario por ID
  getUserById(id: number): Observable<User> {
    return this.http.get<User>(`${this.apiUrl}/${id}`).pipe(
      catchError(this.handleError)
    );
  }

  // Obtener usuario por username
  getUserByUsername(username: string): Observable<User> {
    return this.http.get<User>(`${this.apiUrl}/username/${username}`).pipe(
      catchError(this.handleError)
    );
  }

  // Obtener usuario por email
  getUserByEmail(email: string): Observable<User> {
    return this.http.get<User>(`${this.apiUrl}/email/${email}`).pipe(
      catchError(this.handleError)
    );
  }

  // Obtener usuarios por rol
  getUsersByRole(role: 'USER' | 'ADMIN'): Observable<User[]> {
    return this.http.get<User[]>(`${this.apiUrl}/role/${role}`).pipe(
      catchError(this.handleError)
    );
  }

  // Obtener usuarios activos
  getActiveUsers(): Observable<User[]> {
    return this.http.get<User[]>(`${this.apiUrl}/active`).pipe(
      catchError(this.handleError)
    );
  }

  // Obtener usuarios con tareas
  getUsersWithTasks(): Observable<User[]> {
    return this.http.get<User[]>(`${this.apiUrl}/with-tasks`).pipe(
      catchError(this.handleError)
    );
  }

  // Obtener usuarios sin tareas
  getUsersWithoutTasks(): Observable<User[]> {
    return this.http.get<User[]>(`${this.apiUrl}/without-tasks`).pipe(
      catchError(this.handleError)
    );
  }

  // Buscar usuarios
  searchUsers(searchTerm: string): Observable<User[]> {
    return this.http.get<User[]>(`${this.apiUrl}/search?q=${searchTerm}`).pipe(
      catchError(this.handleError)
    );
  }

  // Crear nuevo usuario
  createUser(user: CreateUserRequest): Observable<User> {
    return this.http.post<User>(this.apiUrl, user).pipe(
      catchError(this.handleError)
    );
  }

  // Actualizar usuario
  updateUser(id: number, user: UpdateUserRequest): Observable<User> {
    return this.http.put<User>(`${this.apiUrl}/${id}`, user).pipe(
      catchError(this.handleError)
    );
  }

  // Cambiar contraseña
  changePassword(id: number, passwordRequest: PasswordChangeRequest): Observable<User> {
    return this.http.put<User>(`${this.apiUrl}/${id}/password`, passwordRequest).pipe(
      catchError(this.handleError)
    );
  }

  // Cambiar estado del usuario
  toggleUserStatus(id: number, statusRequest: UserStatusRequest): Observable<User> {
    return this.http.put<User>(`${this.apiUrl}/${id}/status`, statusRequest).pipe(
      catchError(this.handleError)
    );
  }

  // Eliminar usuario
  deleteUser(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`).pipe(
      catchError(this.handleError)
    );
  }

  // Verificar si username existe
  existsByUsername(username: string): Observable<boolean> {
    return this.http.get<boolean>(`${this.apiUrl}/exists/username/${username}`).pipe(
      catchError(this.handleError)
    );
  }

  // Verificar si email existe
  existsByEmail(email: string): Observable<boolean> {
    return this.http.get<boolean>(`${this.apiUrl}/exists/email/${email}`).pipe(
      catchError(this.handleError)
    );
  }

  // Obtener estadísticas de usuarios
  getUserStatistics(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/statistics`).pipe(
      catchError(this.handleError)
    );
  }

  // Contar usuarios por rol
  countByRole(role: 'USER' | 'ADMIN'): Observable<number> {
    return this.http.get<number>(`${this.apiUrl}/count/role/${role}`).pipe(
      catchError(this.handleError)
    );
  }

  // Manejo de errores
  private handleError(error: HttpErrorResponse) {
    let errorMessage = 'Error desconocido';
    
    if (error.error instanceof ErrorEvent) {
      // Error del lado del cliente
      errorMessage = `Error: ${error.error.message}`;
    } else {
      // Error del lado del servidor
      if (error.error && error.error.message) {
        errorMessage = error.error.message;
      } else {
        errorMessage = `Código de error: ${error.status}, mensaje: ${error.message}`;
      }
    }
    
    console.error('Error en UserService:', errorMessage);
    return throwError(() => new Error(errorMessage));
  }
}
