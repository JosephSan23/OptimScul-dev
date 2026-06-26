# OptimScul — Plataforma Institucional

Plataforma SaaS multiinstitución para la gestión académica, administrativa y financiera de instituciones educativas.

## Tecnologías

| Capa | Tecnología |
|---|---|
| Backend | Java 21 + Spring Boot 3.3 |
| Frontend | Angular 17 |
| Base de datos | PostgreSQL 16 |
| Contenedores | Docker + Docker Compose |
| Migraciones | Flyway |
| Autenticación | JWT |

## Estructura del proyecto

```
optimscul/
├── backend/      # API REST — Spring Boot con arquitectura hexagonal
├── frontend/     # SPA — Angular
├── infra/        # Docker Compose y configuración de infraestructura
│   └── init/     # SQL inicial de la base de datos (solo día cero)
├── .gitignore
└── README.md
```

## Requisitos previos

Antes de levantar el proyecto asegúrate de tener instalado:

- [Java 21 LTS](https://adoptium.net) — verificar con `java -version`
- [Node 20 LTS](https://nodejs.org) — verificar con `node -v`
- [Angular CLI 17](https://angular.io/cli) — verificar con `ng version`
- [Docker Desktop](https://www.docker.com/products/docker-desktop) — verificar con `docker -v`
- [Git](https://git-scm.com) — verificar con `git --version`

## Levantar el proyecto por primera vez

### 1. Clonar el repositorio

```bash
git clone https://github.com/tu-usuario/optimscul.git
cd optimscul
```

### 2. Levantar la base de datos

```bash
cd infra
docker compose up -d
```

Esto levanta PostgreSQL en el puerto `5432` y pgAdmin en `http://localhost:5050`.

El script `init/optimscul.sql` se ejecuta automáticamente la primera vez y crea el schema completo con las 52 tablas.

> **Nota:** si ya levantaste el contenedor antes y necesitas reiniciar desde cero, ejecuta `docker compose down -v` antes de volver a hacer `up`.

### 3. Levantar el backend

```bash
cd backend
./mvnw spring-boot:run
```

En Windows usar:

```bash
mvnw.cmd spring-boot:run
```

El backend queda disponible en `http://localhost:8080`.

Flyway ejecuta automáticamente cualquier migración pendiente al arrancar.

### 4. Levantar el frontend

```bash
cd frontend
npm install
ng serve
```

El frontend queda disponible en `http://localhost:4200`.

## Acceso a pgAdmin

| Campo | Valor |
|---|---|
| URL | http://localhost:5050 |
| Email | admin@optimscul.com |
| Password | admin1234 |

Conectar el servidor con:

| Campo | Valor |
|---|---|
| Host | postgres |
| Puerto | 5432 |
| Base de datos | optimscul |
| Usuario | optimscul |
| Contraseña | optimscul1234 |

## Flujo de cambios en la base de datos

**Nunca modificar el archivo `init/optimscul.sql` después del día cero.**

Cualquier cambio de estructura (nueva tabla, nueva columna, índice) se hace mediante una migración Flyway dentro de `backend/src/main/resources/db/migration/`:

```
V2__descripcion_del_cambio.sql
V3__otra_modificacion.sql
```

Flyway las aplica automáticamente al arrancar el backend. Así todos los ambientes (local, staging, producción) se mantienen sincronizados.

## Ramas de trabajo

| Rama | Propósito |
|---|---|
| `main` | Código en producción — solo merge desde `develop` |
| `develop` | Integración — rama principal de desarrollo |
| `feature/F1-01-nombre` | Cada tarea del backlog tiene su propia rama |

## Convención de commits

Seguimos [Conventional Commits](https://www.conventionalcommits.org):

```
feat: agregar endpoint de login
fix: corregir validación de nota máxima
chore: actualizar dependencias
docs: actualizar README
refactor: separar mapper de estudiante
```

## Variables de entorno

El backend usa perfiles de Spring. Para desarrollo local el archivo `application-dev.yml` ya tiene la configuración apuntando al Docker local. **Nunca subir credenciales de producción al repositorio.**

## Equipo

| Rol | Responsabilidad |
|---|---|
| Backend | Java, Spring Boot, migraciones Flyway |
| Frontend | Angular, servicios, componentes |
| DevOps | Docker, CI/CD, despliegues |