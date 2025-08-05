import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { 
  Task, 
  CreateTaskRequest, 
  UpdateTaskRequest, 
  TaskSummary 
} from '../models/task.model';

@Injectable({
  providedIn: 'root'
})
export class TaskService {
  private apiUrl = 'http://localhost:8081/api/tasks';

  constructor(private http: HttpClient) {}

  // Obtener todas las tareas
  getAllTasks(): Observable<Task[]> {
    return this.http.get<Task[]>(this.apiUrl).pipe(
      catchError(this.handleError)
    );
  }

  // Obtener tarea por ID
  getTaskById(id: number): Observable<Task> {
    return this.http.get<Task>(`${this.apiUrl}/${id}`).pipe(
      catchError(this.handleError)
    );
  }

  // Obtener tareas por usuario
  getTasksByUserId(userId: number): Observable<Task[]> {
    return this.http.get<Task[]>(`${this.apiUrl}/user/${userId}`).pipe(
      catchError(this.handleError)
    );
  }

  // Obtener tareas por estado
  getTasksByStatus(status: 'PENDING' | 'IN_PROGRESS' | 'COMPLETED' | 'CANCELLED'): Observable<Task[]> {
    return this.http.get<Task[]>(`${this.apiUrl}/status/${status}`).pipe(
      catchError(this.handleError)
    );
  }

  // Obtener tareas por prioridad
  getTasksByPriority(priority: 'LOW' | 'MEDIUM' | 'HIGH' | 'URGENT'): Observable<Task[]> {
    return this.http.get<Task[]>(`${this.apiUrl}/priority/${priority}`).pipe(
      catchError(this.handleError)
    );
  }

  // Obtener tareas completadas
  getCompletedTasks(): Observable<Task[]> {
    return this.http.get<Task[]>(`${this.apiUrl}/completed`).pipe(
      catchError(this.handleError)
    );
  }

  // Obtener tareas pendientes
  getPendingTasks(): Observable<Task[]> {
    return this.http.get<Task[]>(`${this.apiUrl}/pending`).pipe(
      catchError(this.handleError)
    );
  }

  // Obtener tareas vencidas
  getOverdueTasks(): Observable<Task[]> {
    return this.http.get<Task[]>(`${this.apiUrl}/overdue`).pipe(
      catchError(this.handleError)
    );
  }

  // Buscar tareas
  searchTasks(searchTerm: string): Observable<Task[]> {
    return this.http.get<Task[]>(`${this.apiUrl}/search?q=${searchTerm}`).pipe(
      catchError(this.handleError)
    );
  }

  // Crear nueva tarea
  createTask(task: CreateTaskRequest): Observable<Task> {
    return this.http.post<Task>(this.apiUrl, task).pipe(
      catchError(this.handleError)
    );
  }

  // Actualizar tarea
  updateTask(id: number, task: UpdateTaskRequest): Observable<Task> {
    return this.http.put<Task>(`${this.apiUrl}/${id}`, task).pipe(
      catchError(this.handleError)
    );
  }

  // Cambiar estado de tarea
  changeTaskStatus(id: number, status: 'PENDING' | 'IN_PROGRESS' | 'COMPLETED' | 'CANCELLED'): Observable<Task> {
    return this.http.put<Task>(`${this.apiUrl}/${id}/status`, { status }).pipe(
      catchError(this.handleError)
    );
  }

  // Marcar tarea como completada
  completeTask(id: number): Observable<Task> {
    return this.http.put<Task>(`${this.apiUrl}/${id}/complete`, {}).pipe(
      catchError(this.handleError)
    );
  }

  // Eliminar tarea
  deleteTask(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`).pipe(
      catchError(this.handleError)
    );
  }

  // Obtener resumen de tareas
  getTaskSummary(): Observable<TaskSummary> {
    return this.http.get<TaskSummary>(`${this.apiUrl}/summary`).pipe(
      catchError(this.handleError)
    );
  }

  // Obtener resumen de tareas por usuario
  getTaskSummaryByUser(userId: number): Observable<TaskSummary> {
    return this.http.get<TaskSummary>(`${this.apiUrl}/summary/user/${userId}`).pipe(
      catchError(this.handleError)
    );
  }

  // Obtener estadísticas de tareas
  getTaskStatistics(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/statistics`).pipe(
      catchError(this.handleError)
    );
  }

  // Contar tareas por estado
  countByStatus(status: 'PENDING' | 'IN_PROGRESS' | 'COMPLETED' | 'CANCELLED'): Observable<number> {
    return this.http.get<number>(`${this.apiUrl}/count/status/${status}`).pipe(
      catchError(this.handleError)
    );
  }

  // Contar tareas por prioridad
  countByPriority(priority: 'LOW' | 'MEDIUM' | 'HIGH' | 'URGENT'): Observable<number> {
    return this.http.get<number>(`${this.apiUrl}/count/priority/${priority}`).pipe(
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
    
    console.error('Error en TaskService:', errorMessage);
    return throwError(() => new Error(errorMessage));
  }
}
