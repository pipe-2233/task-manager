import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterOutlet } from '@angular/router';
import { UserService } from './services/user.service';
import { TaskService } from './services/task.service';
import { User, CreateUserRequest, UpdateUserRequest } from './models/user.model';
import { Task, CreateTaskRequest, UpdateTaskRequest } from './models/task.model';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterOutlet],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent implements OnInit {
  title = 'Task Manager';

  // Estado de la aplicación
  users: User[] = [];
  tasks: Task[] = [];
  backendConnected = false;
  message = '';
  messageType: 'success' | 'error' | 'info' = 'info';

  // Formularios
  showUserForm = false;
  showTaskForm = false;
  editingUser: User | null = null;
  editingTask: Task | null = null;

  // Modelos para nuevos registros
  newUser: CreateUserRequest = {
    username: '',
    email: '',
    password: '',
    firstName: '',
    lastName: '',
    role: 'USER'
  };

  newTask: CreateTaskRequest = {
    title: '',
    description: '',
    priority: 'MEDIUM',
    status: 'PENDING',
    userId: 0,
    dueDate: undefined
  };

  constructor(
    private userService: UserService,
    private taskService: TaskService
  ) {}

  ngOnInit() {
    this.checkConnection();
    this.loadUsersQuietly();
    this.loadTasksQuietly();
  }

  // Verificar conexión con el backend
  checkConnection() {
    this.userService.getAllUsers().subscribe({
      next: () => {
        this.backendConnected = true;
        this.showMessage('Conectado al backend correctamente', 'success');
      },
      error: (error) => {
        this.backendConnected = false;
        this.showMessage('Error de conexión: ' + error.message, 'error');
      }
    });
  }

  // Cargar usuarios silenciosamente (sin mensajes)
  loadUsersQuietly() {
    this.userService.getAllUsers().subscribe({
      next: (users) => {
        this.users = users;
      },
      error: (error) => {
        console.error('Error al cargar usuarios:', error);
      }
    });
  }

  // Cargar tareas silenciosamente (sin mensajes)
  loadTasksQuietly() {
    this.taskService.getAllTasks().subscribe({
      next: (tasks) => {
        this.tasks = tasks;
      },
      error: (error) => {
        console.error('Error al cargar tareas:', error);
      }
    });
  }

  // Cargar usuarios (con mensaje)
  loadUsers() {
    this.userService.getAllUsers().subscribe({
      next: (users) => {
        this.users = users;
        this.showMessage(`Se cargaron ${users.length} usuarios`, 'success');
      },
      error: (error) => {
        this.showMessage('Error al cargar usuarios: ' + error.message, 'error');
      }
    });
  }

  // Cargar tareas (con mensaje)
  loadTasks() {
    this.taskService.getAllTasks().subscribe({
      next: (tasks) => {
        this.tasks = tasks;
        this.showMessage(`Se cargaron ${tasks.length} tareas`, 'success');
      },
      error: (error) => {
        this.showMessage('Error al cargar tareas: ' + error.message, 'error');
      }
    });
  }

  // Crear nuevo usuario
  createUser() {
    if (!this.newUser.username || !this.newUser.email || !this.newUser.password || 
        !this.newUser.firstName || !this.newUser.lastName) {
      this.showMessage('Por favor completa todos los campos obligatorios', 'error');
      return;
    }

    this.userService.createUser(this.newUser).subscribe({
      next: (user) => {
        this.users.push(user);
        this.resetUserForm();
        this.showUserForm = false;
        this.showMessage('Usuario creado exitosamente', 'success');
      },
      error: (error) => {
        this.showMessage('Error al crear usuario: ' + error.message, 'error');
      }
    });
  }

  // Eliminar usuario
  deleteUser(id: number) {
    if (confirm('¿Estás seguro de que quieres eliminar este usuario?')) {
      this.userService.deleteUser(id).subscribe({
        next: () => {
          this.users = this.users.filter(u => u.id !== id);
          this.showMessage('Usuario eliminado exitosamente', 'success');
        },
        error: (error) => {
          this.showMessage('Error al eliminar usuario: ' + error.message, 'error');
        }
      });
    }
  }

  // Crear nueva tarea
  createTask() {
    if (!this.newTask.title || !this.newTask.priority || !this.newTask.userId) {
      this.showMessage('Por favor completa todos los campos obligatorios', 'error');
      return;
    }

    this.taskService.createTask(this.newTask).subscribe({
      next: (task) => {
        this.tasks.push(task);
        this.resetTaskForm();
        this.showTaskForm = false;
        this.showMessage('Tarea creada exitosamente', 'success');
      },
      error: (error) => {
        this.showMessage('Error al crear tarea: ' + error.message, 'error');
      }
    });
  }

  // Eliminar tarea
  deleteTask(id: number) {
    if (confirm('¿Estás seguro de que quieres eliminar esta tarea?')) {
      this.taskService.deleteTask(id).subscribe({
        next: () => {
          this.tasks = this.tasks.filter(t => t.id !== id);
          this.showMessage('Tarea eliminada exitosamente', 'success');
        },
        error: (error) => {
          this.showMessage('Error al eliminar tarea: ' + error.message, 'error');
        }
      });
    }
  }

  // Resetear formulario de usuario
  resetUserForm() {
    this.newUser = {
      username: '',
      email: '',
      password: '',
      firstName: '',
      lastName: '',
      role: 'USER'
    };
  }

  // Resetear formulario de tarea
  resetTaskForm() {
    this.newTask = {
      title: '',
      description: '',
      priority: 'MEDIUM',
      status: 'PENDING',
      userId: 0,
      dueDate: undefined
    };
    this.editingTask = null;
  }

  // Editar usuario
  editUser(user: User) {
    this.editingUser = user;
    this.newUser = {
      username: user.username,
      email: user.email,
      password: '', // No mostramos la contraseña por seguridad
      firstName: user.firstName,
      lastName: user.lastName,
      role: user.role
    };
    this.showUserForm = true;
  }

  // Actualizar usuario
  updateUser() {
    if (!this.editingUser) return;
    
    const updateData: UpdateUserRequest = {
      username: this.newUser.username,
      email: this.newUser.email,
      firstName: this.newUser.firstName,
      lastName: this.newUser.lastName,
      role: this.newUser.role || 'USER',
      enabled: this.editingUser.enabled
    };

    this.userService.updateUser(this.editingUser.id!, updateData).subscribe({
      next: (updatedUser) => {
        const index = this.users.findIndex(u => u.id === this.editingUser!.id);
        if (index !== -1) {
          this.users[index] = updatedUser;
        }
        this.cancelUserForm();
        this.showMessage('Usuario actualizado exitosamente', 'success');
      },
      error: (error) => {
        this.showMessage('Error al actualizar usuario: ' + error.message, 'error');
      }
    });
  }

  // Cancelar formulario de usuario
  cancelUserForm() {
    this.showUserForm = false;
    this.editingUser = null;
    this.resetUserForm();
  }

  // Editar tarea
  editTask(task: Task) {
    this.editingTask = task;
    this.newTask = {
      title: task.title,
      description: task.description || '',
      priority: task.priority,
      status: task.status,
      userId: task.userId,
      dueDate: task.dueDate
    };
    this.showTaskForm = true;
  }

  // Actualizar tarea
  updateTask() {
    if (!this.editingTask) return;
    
    const updateData: UpdateTaskRequest = {
      title: this.newTask.title,
      description: this.newTask.description,
      status: this.newTask.status || 'PENDING',
      priority: this.newTask.priority,
      dueDate: this.newTask.dueDate,
      userId: this.newTask.userId
    };
    
    this.taskService.updateTask(this.editingTask.id!, updateData).subscribe({
      next: (updatedTask) => {
        const index = this.tasks.findIndex(t => t.id === this.editingTask!.id);
        if (index !== -1) {
          this.tasks[index] = updatedTask;
        }
        this.cancelTaskForm();
        this.showMessage('Tarea actualizada exitosamente', 'success');
      },
      error: (error) => {
        this.showMessage('Error al actualizar tarea: ' + error.message, 'error');
      }
    });
  }

  // Cancelar formulario de tarea
  cancelTaskForm() {
    this.showTaskForm = false;
    this.editingTask = null;
    this.resetTaskForm();
  }

  // Obtener nombre de usuario por ID
  getUserName(userId: number): string {
    const user = this.users.find(u => u.id === userId);
    return user ? `${user.firstName} ${user.lastName}` : 'Usuario desconocido';
  }

  // Obtener etiqueta de estado
  getStatusLabel(status: string): string {
    const labels: { [key: string]: string } = {
      'PENDING': 'Pendiente',
      'IN_PROGRESS': 'En Progreso',
      'COMPLETED': 'Completada',
      'CANCELLED': 'Cancelada'
    };
    return labels[status] || status;
  }

  // Mostrar mensaje
  showMessage(text: string, type: 'success' | 'error' | 'info') {
    this.message = text;
    this.messageType = type;
    setTimeout(() => {
      this.message = '';
    }, 5000);
  }
}
