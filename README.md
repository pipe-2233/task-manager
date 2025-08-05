# 🎯 Task Manager - Sistema de Gestión de Tareas

[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://openjdk.org/projects/jdk/17/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.2-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Angular](https://img.shields.io/badge/Angular-17-red.svg)](https://angular.io/)
[![TypeScript](https://img.shields.io/badge/TypeScript-5.0-blue.svg)](https://www.typescriptlang.org/)

## 📖 Descripción

Task Manager es una aplicación web full-stack moderna para la gestión eficiente de tareas. Desarrollada con Spring Boot en el backend y Angular en el frontend, ofrece una experiencia de usuario fluida y funcionalidades completas para organizar y administrar tareas de manera efectiva.

## ✨ Características Principales

- 🔐 **Gestión de Usuarios**: Sistema completo de registro y administración de usuarios
- 📝 **CRUD de Tareas**: Crear, leer, actualizar y eliminar tareas con facilidad
- 💾 **Persistencia de Datos**: Almacenamiento seguro con base de datos H2
- 🎨 **Interfaz Moderna**: Diseño responsive y atractivo con Angular 17
- ⚡ **API REST**: Backend robusto con Spring Boot y Spring Security
- 🔄 **Tiempo Real**: Actualizaciones dinámicas sin recargar la página

## �️ Stack Tecnológico

### Backend
- **Java 17** - Plataforma de desarrollo robusta
- **Spring Boot 3.2.2** - Framework de aplicación empresarial
- **Spring Data JPA** - Capa de persistencia simplificada
- **Spring Security** - Seguridad y autenticación
- **H2 Database** - Base de datos embebida para desarrollo
- **Maven** - Gestión de dependencias y construcción

### Frontend
- **Angular 17** - Framework moderno de SPA
- **TypeScript** - Desarrollo tipado y escalable
- **Bootstrap** - Framework CSS responsive
- **RxJS** - Programación reactiva

## 🚀 Instalación y Configuración

### Prerrequisitos
- Java 17 o superior
- Node.js 18+ y npm
- Git

### Clonar el Repositorio
```bash
git clone https://github.com/pipe-2233/task-manager.git
cd task-manager
```

### Backend (Spring Boot)
```bash
cd backend
mvn clean install
mvn spring-boot:run
```
El servidor estará disponible en: `http://localhost:8081`

### Frontend (Angular)
```bash
cd frontend
npm install
ng serve
```
La aplicación estará disponible en: `http://localhost:4200`

## � Uso de la Aplicación

1. **Inicio**: Accede a `http://localhost:4200`
2. **Usuarios**: Crea y gestiona usuarios desde la interfaz
3. **Tareas**: Añade, edita y elimina tareas asociadas a usuarios
4. **Persistencia**: Los datos se mantienen automáticamente

## 🏗️ Estructura del Proyecto

```
task-manager/
├── backend/                 # Aplicación Spring Boot
│   ├── src/main/java/      # Código fuente Java
│   │   └── com/taskmanager/
│   │       ├── controller/ # Controladores REST
│   │       ├── service/    # Lógica de negocio
│   │       ├── repository/ # Capa de datos
│   │       ├── entity/     # Entidades JPA
│   │       └── config/     # Configuraciones
│   └── pom.xml            # Dependencias Maven
├── frontend/              # Aplicación Angular
│   ├── src/app/          # Código fuente TypeScript
│   │   ├── components/   # Componentes Angular
│   │   ├── services/     # Servicios
│   │   └── models/       # Modelos de datos
│   └── package.json      # Dependencias npm
└── README.md
```

## 🌐 API Endpoints

### Usuarios
- `GET /api/users` - Obtener todos los usuarios
- `POST /api/users` - Crear nuevo usuario
- `PUT /api/users/{id}` - Actualizar usuario
- `DELETE /api/users/{id}` - Eliminar usuario

### Tareas
- `GET /api/tasks` - Obtener todas las tareas
- `POST /api/tasks` - Crear nueva tarea
- `PUT /api/tasks/{id}` - Actualizar tarea
- `DELETE /api/tasks/{id}` - Eliminar tarea
- `GET /api/tasks/user/{userId}` - Obtener tareas por usuario

## 🔧 Configuración de Base de Datos

Por defecto, la aplicación usa H2 Database en modo archivo para persistencia:

```properties
spring.datasource.url=jdbc:h2:file:./data/taskmanager
spring.datasource.username=sa
spring.datasource.password=password
```

Consola H2 disponible en: `http://localhost:8081/h2-console`

## 📋 Próximas Funcionalidades

- [ ] Autenticación JWT
- [ ] Filtros avanzados de tareas
- [ ] Notificaciones por fecha de vencimiento
- [ ] Dashboard con estadísticas
- [ ] Exportación de datos
- [ ] Temas oscuro/claro

## 🤝 Contribución

Las contribuciones son bienvenidas. Para cambios importantes:

1. Fork del proyecto
2. Crea tu feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push al branch (`git push origin feature/AmazingFeature`)
5. Abre un Pull Request

## 📄 Licencia

Este proyecto está bajo la Licencia MIT. Ver el archivo `LICENSE` para más detalles.

## 👨‍💻 Autor

**Andre** - [@pipe-2233](https://github.com/pipe-2233)

## 🙏 Agradecimientos

- Spring Boot Team por el excelente framework
- Angular Team por las herramientas modernas de desarrollo
- Comunidad de desarrolladores por la inspiración y recursos

---

⭐ ¡Si te gusta este proyecto, dale una estrella en GitHub!
- [ ] Interfaz responsive
- [ ] Filtros y búsquedas
- [ ] Estados de tareas

### 🏗️ Arquitectura
```
task-manager-v2/
├── backend/          # API REST con Spring Boot
├── frontend/         # Aplicación Angular
└── docs/            # Documentación
```

## 🚦 Estados del Proyecto
- 🔧 **En configuración** - Preparando entorno de desarrollo
- ⏳ **Pendiente** - Backend base
- ⏳ **Pendiente** - Frontend base  
- ⏳ **Pendiente** - Integración
- ⏳ **Pendiente** - Pruebas y despliegue

## 🛠️ Instalación y Configuración

### Prerrequisitos
- Java 17+
- Node.js 16+
- Maven 3.8+
- Angular CLI 14+

### Ejecución

#### Backend
```bash
cd backend
mvn spring-boot:run
```

#### Frontend
```bash
cd frontend
npm install
ng serve
```

## 📖 Documentación
- La documentación de la API estará disponible en: `http://localhost:8080/swagger-ui.html`
- El frontend se ejecutará en: `http://localhost:4200`

## 👨‍💻 Desarrollo
Este proyecto se desarrolla siguiendo un enfoque paso a paso, revisando cada archivo y probando cada funcionalidad antes de continuar.

## 📝 Notas
Proyecto en desarrollo activo. Se actualiza conforme se implementan nuevas características.
